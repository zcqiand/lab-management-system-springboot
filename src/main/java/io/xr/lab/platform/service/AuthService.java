package io.xr.lab.platform.service;

import io.xr.lab.platform.auth.jwt.LabJwtSigner;
import io.xr.lab.platform.auth.sso.MenuSnapshotCache;
import io.xr.lab.platform.auth.sso.SaasAuthClient;
import io.xr.lab.platform.auth.sso.SaasAuthException;
import io.xr.lab.platform.auth.sso.SaasMeClient;
import io.xr.lab.platform.auth.sso.SaasMenuMapper;
import io.xr.lab.platform.config.LabConfig;
import io.xr.lab.platform.directory.UserDirectory;
import io.xr.lab.shared.dto.AuthLogoutRequest;
import io.xr.lab.shared.dto.CurrentUser;
import io.xr.lab.shared.dto.CurrentUserSession;
import io.xr.lab.shared.dto.LoginRequest;
import io.xr.lab.shared.dto.LoginResponse;
import io.xr.lab.shared.dto.MenuNode;
import io.xr.lab.shared.dto.MyTenant;
import io.xr.lab.shared.dto.PermissionSet;
import io.xr.lab.shared.dto.RefreshTokenRequest;
import io.xr.lab.shared.dto.SsoCallbackRequest;
import io.xr.lab.shared.dto.SsoRedirect;
import io.xr.lab.shared.dto.SwitchTenantRequest;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * M00.F01/F02 + M01.F04/F05 - 认证域（B1，真后端）。
 *
 * <p>对齐 B1 真后端 OAuth 2.0 + JWT 方案（ADR-0008）：
 *
 * <ul>
 *   <li>JWT：HMAC HS256，{@link LabJwtSigner} 出真签名
 *   <li>SSO：{@link SaasAuthClient} 真调 saas /oauth/authorize + /oauth/token
 *   <li>用户信息：{@link SaasMeClient} 拿 saas /me/whoami + /me/tenants
 *   <li>CSRF：RFC 6749 §10.12 标准 state —— 前端生成、后端原样透传给 saas、回跳由前端比对
 *   <li>refresh：lab refresh token 内嵌 saas refresh token，调 saas grantType=refresh_token 续
 * </ul>
 */
@Service
public class AuthService {

  private static final Logger log = LoggerFactory.getLogger(AuthService.class);

  /** msw 权限集（admin 全量 11 项，handlers-extra.ts:160-175）。 */
  static final List<String> DEMO_PERMISSIONS =
      List.of(
          "contract:read",
          "contract:write",
          "sample:read",
          "sample:write",
          "report:read",
          "report:write",
          "report:issue",
          "inspection:read",
          "inspection:write",
          "audit:read",
          "*");

  private final UserDirectory directory;
  private final LabJwtSigner jwt;
  private final SaasAuthClient saasAuth;
  private final SaasMeClient saasMe;
  private final LabConfig labConfig;
  private final MenuSnapshotCache menuCache;
  private final SaasMenuMapper menuMapper;

  public AuthService(
      UserDirectory directory,
      LabJwtSigner jwt,
      SaasAuthClient saasAuth,
      SaasMeClient saasMe,
      LabConfig labConfig,
      MenuSnapshotCache menuCache,
      SaasMenuMapper menuMapper) {
    this.directory = directory;
    this.jwt = jwt;
    this.saasAuth = saasAuth;
    this.saasMe = saasMe;
    this.labConfig = labConfig;
    this.menuCache = menuCache;
    this.menuMapper = menuMapper;
  }

  // === M01.F05.I01 密码登录 ===

  public LoginResponse login(LoginRequest body) {
    String username = body == null || body.getUsername() == null ? "" : body.getUsername().trim();
    String password = body == null || body.getPassword() == null ? "" : body.getPassword();
    if (username.isEmpty() || password.isEmpty()) {
      throw new IllegalArgumentException("username and password are required");
    }
    if (!directory.checkPassword(username, password)) {
      throw new SecurityException("Invalid username or password");
    }
    CurrentUser user = directory.findByUsername(username).orElseThrow(SecurityException::new);
    // 密码登录的 dev 用户无 saas 身份 → 用服务账号拉菜单快照（demo 兜底已删，
    // miss 时 /menus 会 503）。失败不阻塞登录（warn），用户重登/SSO 可补。
    cacheMenusWithServiceAccount(user.getId());
    return session(user, null, null);
  }

  // === M01.F05.I04 刷新 token ===

  public LoginResponse refresh(RefreshTokenRequest body) {
    if (body == null || body.getRefreshToken() == null) {
      throw new SecurityException("missing refresh_token");
    }
    Map<String, Object> claims;
    try {
      claims = jwt.verify(body.getRefreshToken());
    } catch (IllegalArgumentException e) {
      throw new SecurityException("invalid refresh_token: " + e.getMessage());
    }
    if (!"refresh".equals(claims.get("typ"))) {
      throw new SecurityException("invalid refresh_token: not a refresh token");
    }
    String tenantId = (String) claims.get("tenant_id");
    String saasRefresh = (String) claims.get("saas_refresh_token");
    if (saasRefresh == null || saasRefresh.isEmpty()) {
      throw new SecurityException("invalid refresh_token: missing saas_refresh_token claim");
    }

    // 走 saas /oauth/token grantType=refresh_token
    SaasAuthClient.TokenResponse t;
    try {
      t = saasAuth.token("refresh_token", null, saasRefresh, null);
    } catch (SaasAuthException e) {
      throw new SecurityException("saas refresh failed: " + e.getMessage());
    }
    SaasMeClient.SaasCurrentUser user = saasMe.whoami(t.getAccessToken());
    List<SaasMeClient.SaasTenantMembership> memberships = saasMe.listMyTenants(t.getAccessToken());
    CurrentUser labUser =
        directory
            .findByEmail(user.getEmail())
            .orElseThrow(() -> new SecurityException("unknown user"));
    // 菜单快照刷新（同 ssoCallback：refresh 时也瞬时持有 accessToken）
    cacheMenus(labUser.getId(), t.getAccessToken());
    return session(labUser, tenantId, tenantsFrom(memberships), t.getRefreshToken());
  }

  // === M01.F05.I05 登出（无状态 JWT，服务端无 session store） ===

  public void logout(AuthLogoutRequest body) {
    // 前端清存储；服务端无操作。
  }

  // === M00.F01.I01 当前会话 ===

  public CurrentUserSession me(Map<String, Object> claims) {
    CurrentUser user = resolveUser(claims);
    Object tenantClaim = claims.get("tenant_id");
    String currentTenantId =
        tenantClaim != null ? tenantClaim.toString() : directory.defaultTenant().getTenantId();
    return new CurrentUserSession()
        .user(user)
        .tenants(directory.tenantsOf(user.getUsername()))
        .currentTenantId(currentTenantId);
  }

  // === M00.F02.I01 选租户换发 ===

  public LoginResponse switchTenant(Map<String, Object> claims, SwitchTenantRequest body) {
    CurrentUser user = resolveUser(claims);
    String tenantId = body == null || body.getTenantId() == null ? "" : body.getTenantId();
    MyTenant target = directory.findByTenantId(tenantId).orElse(null);
    if (target == null) {
      throw new NoSuchElementException("Tenant not found");
    }
    return session(user, target.getTenantId(), null);
  }

  /**
   * 用 sub claim (user.id) 解析出 lab {@link CurrentUser}:sub 优先按 email 查(SSO 写入路径), 退化按 username
   * 查(密码登录写入路径),再退到 id 直接匹配（防 saas 当前未实现 email 桥接） 。
   */
  private CurrentUser resolveUser(Map<String, Object> claims) {
    String sub = (String) claims.get("sub");
    if (sub == null || sub.isEmpty()) {
      throw new SecurityException("missing sub claim");
    }
    return directory
        .findById(sub)
        .or(() -> directory.findByEmail(sub))
        .or(() -> directory.findByUsername(sub))
        .orElseThrow(() -> new SecurityException("unknown user: " + sub));
  }

  // === M01.F04.I01 动态菜单 / I02 权限集 ===

  /**
   * 动态菜单：SSO/refresh/密码登录时缓存的 saas 快照。miss（快照过期/拉取失败/重启）抛
   * MenusUnavailableException（GlobalExceptionHandler 映射 503）—— 2026-08-27 起 demo 兜底删除， 假树不再下发；前端
   * useBackendMenus 失败回退静态菜单。
   */
  public List<MenuNode> menus(Map<String, Object> claims) {
    String sub = claims == null ? null : (String) claims.get("sub");
    // 2026-08-27 起 demo 兜底删除：miss 抛 503（MenusUnavailableException），
    // 前端 useBackendMenus 失败回退静态菜单（FALLBACK_NAV / MENU_TREE）
    return menuCache
        .get(sub)
        .orElseThrow(
            () ->
                new MenusUnavailableException(
                    "menu snapshot unavailable for user " + sub + "; re-login to refresh"));
  }

  /**
   * SSO/refresh 时点拉菜单进缓存。失败（saas 5xx/网络/4xx）只 warn 不抛——菜单不可用不应 阻塞登录主流程（miss 时 /menus 503 由前端兜底），下次
   * refresh 重试。
   */
  private void cacheMenus(String userId, String saasAccessToken) {
    if (userId == null || saasAccessToken == null) {
      return;
    }
    try {
      List<SaasMeClient.SaasMenuNode> snapshot = saasMe.listMyMenus(saasAccessToken, LAB_APP_CODE);
      menuCache.put(userId, menuMapper.map(snapshot));
    } catch (RuntimeException e) {
      // SaasAuthException 也是 RuntimeException 子类；菜单失败不阻塞登录（见 javadoc）
      log.warn("menu snapshot fetch failed for user {}: {}", userId, e.getMessage());
    }
  }

  /** lab 家族在 saas 注册的 appCode（seeds apps.json）。 */
  static final String LAB_APP_CODE = "lab-management";

  /**
   * 密码登录路径的菜单快照：dev 用户无 saas 身份，用服务账号（lab.sso.service-user/password， dev 默认 alice/dev123456）登 saas
   * /auth/login 换 token 再拉 /me/menus。失败只 warn—— 登录主流程不受影响，miss 时 /menus 503 由前端兜底。
   */
  private void cacheMenusWithServiceAccount(String userId) {
    if (userId == null) {
      return;
    }
    try {
      SaasAuthClient.TokenResponse t =
          saasAuth.serviceLogin(labConfig.sso().serviceUser(), labConfig.sso().servicePassword());
      cacheMenus(userId, t.getAccessToken());
    } catch (RuntimeException e) {
      log.warn("service-account menu snapshot failed for user {}: {}", userId, e.getMessage());
    }
  }

  public PermissionSet permissions() {
    return new PermissionSet().permissions(DEMO_PERMISSIONS);
  }

  // === M01.F05.I02 SSO 跳转 / I03 SSO 回调 ===

  /**
   * 构造 saas /oauth/authorize 调用 + 返回 authorizeUrl 供前端跳转（RFC 6749 §4.1.1）。
   *
   * <p>state 按标准由前端生成、此处原样透传给 saas，回跳 {@code /login?state=...} 由前端比对（CSRF 防护在前端 sessionStorage，RFC
   * 6749 §10.12）；后端不生成、不校验 state。
   *
   * @param businessRedirect 前端要保留的 redirect 参数（lab 业务方跳转前后回来）
   * @param frontendState 前端生成的 csrfState，透传给 saas 回显
   * @return SsoRedirect{authorizeUrl, state}
   */
  public SsoAuthResult ssoAuthorize(String businessRedirect, String frontendState) {
    String state = frontendState == null ? "" : frontendState;
    // scope 必须精确 ∈ apps.scopes 种子的单个值（shared V014: "lab.read" | "lab.write"；
    // saas 侧 Contains 是单值精确匹配，不接受 space-separated）。
    // 曾发 "openid profile email" → saas Authorize 抛 INVALID_SCOPE 500，浏览器只见 502。
    SaasAuthClient.AuthorizeCodeResponse resp =
        saasAuth.authorize(labConfig.sso().callbackRedirectBase(), "lab.read", state);
    // 登录跳板在 saas 前端域名（effectiveLoginUrl），不是 API 域名（saasBase 的 /login 是 404）
    String authorizeUrl =
        labConfig.sso().effectiveLoginUrl()
            + "/login?code="
            + resp.getCode()
            + "&state="
            + resp.getState()
            + "&redirect_uri="
            + labConfig.sso().callbackRedirectBase();
    return new SsoAuthResult(new SsoRedirect().authorizeUrl(authorizeUrl).state(resp.getState()));
  }

  /**
   * 处理 SSO 回调（RFC 6749 §4.1.3）：code 换 saas token，再 /me/whoami + /me/tenants 拿 user。 state
   * 校验在前端已完成（回跳比对 csrfState），后端只消费一次性 code。
   *
   * @param body 业务请求体（grant_type=authorization_code, code, redirect_uri, state）
   */
  public LoginResponse ssoCallback(SsoCallbackRequest body) {
    if (body == null) {
      throw new IllegalArgumentException("missing body");
    }
    SaasAuthClient.TokenResponse t =
        saasAuth.token(
            "authorization_code",
            body.getCode(),
            null,
            body.getRedirectUri() == null
                ? labConfig.sso().callbackRedirectBase()
                : body.getRedirectUri());
    SaasMeClient.SaasCurrentUser saasUser = saasMe.whoami(t.getAccessToken());
    List<SaasMeClient.SaasTenantMembership> memberships = saasMe.listMyTenants(t.getAccessToken());
    // username → email 桥接（ADR-0008）
    CurrentUser labUser =
        directory
            .findByEmail(saasUser.getEmail())
            .orElseGet(
                () ->
                    directory.upsert(
                        saasUser.getId(),
                        saasUser.getEmail(),
                        saasUser.getDisplayName(),
                        "viewer"));
    // 菜单快照：瞬时持有 saas accessToken 的唯一时点，顺手拉菜单进缓存（失败不阻塞登录）
    cacheMenus(labUser.getId(), t.getAccessToken());
    return session(labUser, null, tenantsFrom(memberships), t.getRefreshToken());
  }

  // === token 签发 ===

  private LoginResponse session(CurrentUser user, String tenantId, String saasRefreshToken) {
    return session(user, tenantId, null, saasRefreshToken);
  }

  private LoginResponse session(
      CurrentUser user, String tenantId, List<MyTenant> tenants, String saasRefreshToken) {
    String accessToken = jwt.issue(user.getId(), tenantId);
    String refreshToken =
        saasRefreshToken == null
            ? jwt.issueRefresh(user.getId(), "dev-placeholder")
            : jwt.issueRefresh(user.getId(), saasRefreshToken);
    List<MyTenant> useTenants = tenants == null ? directory.tenantsOf(user.getUsername()) : tenants;
    return new LoginResponse()
        .token(accessToken)
        .refreshToken(refreshToken)
        .user(user)
        .tenants(useTenants);
  }

  private List<MyTenant> tenantsFrom(List<SaasMeClient.SaasTenantMembership> memberships) {
    if (memberships == null) {
      return List.of();
    }
    return memberships.stream()
        .map(
            m ->
                new MyTenant()
                    .tenantId(m.getTenantId())
                    .code(m.getTenantId())
                    .name(m.getTenantId())
                    .roleIds(m.getRoleIds() == null ? List.of() : m.getRoleIds()))
        .toList();
  }

  private static MenuNode menu(String id, String label, String path) {
    return new MenuNode().id(id).label(label).path(path);
  }

  /**
   * Controller 接收的复合结果：SsoRedirect。SsoRedirect 是 codegen 生成的 DTO(setter/getter,内部 HashMap
   * 不可变),暴露引用对 lab 业务场景无副作用——Controller 只读不回写。
   */
  public record SsoAuthResult(SsoRedirect redirect) {}
}
