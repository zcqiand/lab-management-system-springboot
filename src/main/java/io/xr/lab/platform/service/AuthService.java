package io.xr.lab.platform.service;

import io.xr.lab.platform.auth.jwt.LabJwtSigner;
import io.xr.lab.platform.auth.sso.MembershipSnapshotCache;
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
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
  private final MembershipSnapshotCache membershipCache;
  private final SaasMenuMapper menuMapper;

  // 两个构造器并存时 Spring 无从择一（回退找无参构造 → NoSuchMethodException 启动崩），
  // 主构造器必须显式标 @Autowired（本仓铁律：构造器注入）。
  @Autowired
  public AuthService(
      UserDirectory directory,
      LabJwtSigner jwt,
      SaasAuthClient saasAuth,
      SaasMeClient saasMe,
      LabConfig labConfig,
      MenuSnapshotCache menuCache,
      SaasMenuMapper menuMapper) {
    this(
        directory,
        jwt,
        saasAuth,
        saasMe,
        labConfig,
        menuCache,
        new MembershipSnapshotCache(),
        menuMapper);
  }

  /** 2026-09-03 租户体系对齐：可注入 MembershipSnapshotCache 的测试构造器。 */
  public AuthService(
      UserDirectory directory,
      LabJwtSigner jwt,
      SaasAuthClient saasAuth,
      SaasMeClient saasMe,
      LabConfig labConfig,
      MenuSnapshotCache menuCache,
      MembershipSnapshotCache membershipCache,
      SaasMenuMapper menuMapper) {
    this.directory = directory;
    this.jwt = jwt;
    this.saasAuth = saasAuth;
    this.saasMe = saasMe;
    this.labConfig = labConfig;
    this.menuCache = menuCache;
    this.membershipCache = membershipCache;
    this.menuMapper = menuMapper;
  }

  // === M01.F05.I01 密码登录（REQ-2026-023：账密校验代理 saas IdP，本地 dev 密码路径退役） ===

  public LoginResponse login(LoginRequest body) {
    String username = body == null || body.getUsername() == null ? "" : body.getUsername().trim();
    String password = body == null || body.getPassword() == null ? "" : body.getPassword();
    if (username.isEmpty() || password.isEmpty()) {
      throw new IllegalArgumentException("username and password are required");
    }
    // REQ-2026-023：账密校验委托 saas IdP（clientId=LAB_SAAS_CLIENT_ID，账密原值透传）。
    // 错误映射五仓统一：saas 4xx → 401 INVALID_CREDENTIALS（不透传内部码）；
    // 5xx/不可达 → 502 SAAS_UNAVAILABLE。
    SaasAuthClient.LoginBody saasLogin;
    try {
      saasLogin = saasAuth.userLogin(username, password);
    } catch (SaasAuthException.UpstreamUnavailable e) {
      throw new SaasIdpUnavailableException("saas IdP unavailable: " + e.getMessage());
    } catch (SaasAuthException e) {
      // 4xx（InvalidGrant/UnauthorizedClient）统一 401，不透传 saas 内部码
      throw new SecurityException("Invalid username or password");
    }
    SaasAuthClient.SaasLoginUser u =
        saasLogin.getUser() != null ? saasLogin.getUser() : new SaasAuthClient.SaasLoginUser();
    String saasUserId = firstNonEmpty(u.getId(), saasLogin.getUserId());
    // 租户名富化（admin/tenants，best-effort 降级 name=tenantId，不阻塞登录）
    Map<String, SaasMeClient.SaasPlatformTenant> nameById =
        fetchTenantNames(saasUserId, saasLogin.getAccessToken());
    List<MyTenant> tenants = tenantsFromLogin(saasLogin.getAvailableTenants(), nameById);
    // user 映射自 saas 响应；回退链 firstNonEmpty（||，防 saas displayName 空串陷阱）
    CurrentUser user =
        new CurrentUser()
            .id(firstNonEmpty(u.getId(), saasLogin.getUserId(), "unknown"))
            .username(firstNonEmpty(u.getUsername(), u.getEmail(), "unknown"))
            .displayName(firstNonEmpty(u.getDisplayName(), u.getUsername(), u.getEmail(), "未知用户"))
            .roleCode(firstNonEmpty(roleCodeOf(tenants), "member"));
    // 菜单快照仍走服务账号 serviceLogin（REQ-2026-023 非目标，行为不动）
    cacheMenusWithServiceAccount(user.getId());
    // REQ-2026-023 live 回归（2026-10-11 五目标 401 指纹）：密码登录用户落
    // directory（镜像 ssoCallback upsert 链）——me/switchTenant 的 resolveUser
    // 按 sub=saas user id 解析，不落目录必 unknown user 401。email 缺失降级
    // username 作 upsert 键（与 ssoCallback 的 email 主键约定同形）。
    directory.upsert(
        user.getId(),
        firstNonEmpty(u.getEmail(), u.getUsername()),
        user.getDisplayName(),
        user.getRoleCode());
    membershipCache.put(user.getId(), tenants);
    // tenant_id claim：saas currentTenantId 优先，缺省落首租户。
    // 2026-10-20 租户标识=tenant_key（人裁「对外交互用 key」）：平台列表命中 → tenantKey。
    String tenantId =
        resolveTenantKey(
            firstNonEmpty(
                saasLogin.getCurrentTenantId(),
                tenants.isEmpty() ? null : tenants.get(0).getTenantId()),
            nameById);
    // saasRefresh=null → refresh 维持 dev-placeholder（REQ-2026-023 非目标）
    return session(user, tenantId, tenants, null);
  }

  private static String firstNonEmpty(String... values) {
    for (String v : values) {
      if (v != null && !v.isEmpty()) {
        return v;
      }
    }
    return null;
  }

  private static String roleCodeOf(List<MyTenant> tenants) {
    if (tenants.isEmpty()
        || tenants.get(0).getRoleIds() == null
        || tenants.get(0).getRoleIds().isEmpty()) {
      return null;
    }
    return tenants.get(0).getRoleIds().get(0);
  }

  /**
   * saas 登录响应 availableTenants → MyTenant。nameById 命中 → tenantId/code=tenantKey、name=name； miss →
   * 降级 原值 tenantId（2026-10-20 tenant_key 人裁；与 {@link #tenantsFrom} 同款降级语义）。
   */
  private List<MyTenant> tenantsFromLogin(
      List<SaasAuthClient.SaasAvailableTenant> available,
      Map<String, SaasMeClient.SaasPlatformTenant> nameById) {
    if (available == null) {
      return List.of();
    }
    return available.stream()
        .filter(t -> t.getTenantId() != null && !"removed".equals(t.getStatus()))
        .map(
            t -> {
              SaasMeClient.SaasPlatformTenant pt = nameById.get(t.getTenantId());
              String tid =
                  pt != null && pt.getTenantKey() != null ? pt.getTenantKey() : t.getTenantId();
              return new MyTenant()
                  .tenantId(tid)
                  .code(tid)
                  .name(pt != null && pt.getName() != null ? pt.getName() : t.getTenantId())
                  .roleIds(t.getRoleIds() == null ? List.of() : t.getRoleIds());
            })
        .toList();
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
    // 2026-09-03 修（aspnetcore 2026-08-29 同款）：新部署/重启后 upserted 目录为空，
    // findByEmail 必抛 unknown user → hydrateAuth 自愈链断。跟 ssoCallback 同款 upsert 兜底。
    CurrentUser labUser =
        directory
            .findByEmail(user.getEmail())
            .orElseGet(
                () ->
                    directory.upsert(
                        user.getId(), user.getEmail(), user.getDisplayName(), "viewer"));
    // 2026-09-03：refresh 也要存回新 saas refresh token（rotate-once）+ memberships 快照
    directory.setSaasRefreshToken(labUser.getId(), t.getRefreshToken());
    // 菜单快照刷新（同 ssoCallback：refresh 时也瞬时持有 accessToken）
    cacheMenus(labUser.getId(), t.getAccessToken());
    // 2026-09-15 租户显示名：同 ssoCallback，瞬时持 token 拉平台租户列表注入真名
    Map<String, SaasMeClient.SaasPlatformTenant> tenantNameById =
        fetchTenantNames(labUser.getId(), t.getAccessToken());
    membershipCache.put(labUser.getId(), tenantsFrom(memberships, tenantNameById));
    return session(
        labUser, tenantId, tenantsFrom(memberships, tenantNameById), t.getRefreshToken());
  }

  // === M01.F05.I05 登出（无状态 JWT，服务端无 session store） ===

  public void logout(AuthLogoutRequest body) {
    // 前端清存储；服务端无操作。
  }

  // === M00.F01.I01 当前会话 ===

  public CurrentUserSession me(Map<String, Object> claims) {
    CurrentUser user = resolveUser(claims);
    // 2026-09-03 租户体系对齐（specs/2026-09-03-me-tenant-alignment-design.md §3）：
    // 登录会话租户必须与登录响应同体系（前端 hydrateAuth 的 tenants.find(saas UUID)
    // 必命中 → 否则 awaiting_tenant 卡「检查登录态…」死锁）。
    // REQ-2026-023：密码登录与 SSO 同写 membership 快照（登录响应租户=saas 体系），
    // 快照 hit 优先返回——两类用户同链；miss 时：SSO 用户（有 per-user saas refresh
    // token）抛 401 走 /api/auth/refresh 自愈；密码用户（无 per-user token）降级
    // demo 租户（重启/TTL 后可恢复态，重登即重建快照）。
    var cachedTenants = membershipCache.get(user.getId());
    if (cachedTenants.isPresent()) {
      Object tenantClaim = claims.get("tenant_id");
      String currentTenantId =
          tenantClaim != null ? tenantClaim.toString() : cachedTenants.get().get(0).getTenantId();
      return new CurrentUserSession()
          .user(user)
          .tenants(cachedTenants.get())
          .currentTenantId(currentTenantId);
    }
    String saasRefresh = directory.getSaasRefreshToken(user.getId());
    if (saasRefresh != null && !saasRefresh.isEmpty()) {
      throw new SecurityException(
          "membership snapshot unavailable for user "
              + user.getId()
              + " (cache miss); refresh required");
    }
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
    // REQ-2026-023 live 回归：归属校验对用户当前租户集（membership 快照=登录响应
    // 同体系，写死 demo 字面量会让密码登录用户切 saas 租户 404）；快照 miss 降级
    // demo 目录（与 me() 降级链一致）。
    List<MyTenant> knownTenants =
        membershipCache.get(user.getId()).orElseGet(() -> directory.tenantsOf(user.getUsername()));
    MyTenant target =
        knownTenants.stream()
            .filter(t -> tenantId.equals(t.getTenantId()))
            .findFirst()
            .orElse(null);
    if (target == null) {
      throw new NoSuchElementException("Tenant not found");
    }
    return session(user, target.getTenantId(), knownTenants, null);
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
    // ADR-0019 禁兜底 + RFC 6749 §4.1.1：redirect_uri/state 由调用方（前端）发起，
    // 缺失 fail-fast，不落 env 兜底值。2026-09-23 跨前端事故：此前忽略调用方
    // redirect_uri、硬用 env LAB_SSO_CALLBACK_REDIRECT（值=lab-react :5202），
    // lab-nextjs(:5201) 选 springboot 后端时 saas 把 code 送去了 lab-react →
    // 本前端永远拿不到 token → 全部鉴权请求 401、接样管理等数据页空列表。
    // 对齐 aspnetcore AuthService.SsoAuthorize：回显调用方 redirect_uri 且 URL 编码。
    if (businessRedirect == null || businessRedirect.isBlank()) {
      throw new IllegalArgumentException("missing redirect_uri");
    }
    String state = frontendState == null ? "" : frontendState;
    // 2026-08-29 标准化: 不再调 saas authorize 预拿 code。
    //   - saas-springboot authorize 不要求 saas session(confidential client 模式 work),
    //     但用户体验差: 用户已在 saas 登录又被跳 saas 登录页(冗余)。
    //   - 跟 lab-aspnetcore v0.2.11 同款改造: 直接 302 跳 saas 登录页(带 redirect_uri + state)。
    //   - saas-vue/saas-react LoginPage 已支持 ?redirect_uri=&state= 参数:
    //     用户登录 → saas 写 session cookie → saas-react 自动调 saas /oauth/authorize
    //     拿 code → 302 跳回 redirect_uri?code=&state= → lab 前端 POST
    //     /api/auth/sso/callback {code, state, redirect_uri} → ssoCallback
    //     用 clientSecret 调 saas /token 拿 access_token + refresh_token。
    String authorizeUrl =
        labConfig.sso().effectiveLoginUrl()
            + "/login?redirect_uri="
            + URLEncoder.encode(businessRedirect, StandardCharsets.UTF_8)
            + "&state="
            + URLEncoder.encode(state, StandardCharsets.UTF_8)
            + "&client_id="
            + URLEncoder.encode(labConfig.sso().clientId(), StandardCharsets.UTF_8);
    return new SsoAuthResult(new SsoRedirect().authorizeUrl(authorizeUrl).state(state));
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
    // 2026-09-03 租户体系对齐：存 per-user saas refresh token（me()/menus 的 reload 用）
    // + memberships 快照（me() 返回 saas 租户体系）。
    directory.setSaasRefreshToken(labUser.getId(), t.getRefreshToken());
    // 2026-09-15 租户显示名：memberships 契约只有 tenantId 不带名字，趁同一
    // 瞬时窗口拉 saas 平台租户列表（GET /api/v1/admin/tenants，guard 只验 JWT）
    // 建 tenantId→{name, tenantKey} 映射填真名（lab-nextjs sso/callback 同款）。
    // 快照与登录响应 tenants 同源 —— me() 读出的也是真名。失败只 warn 降级
    // name=tenantId（与菜单快照同款 best-effort，不阻塞登录）。
    Map<String, SaasMeClient.SaasPlatformTenant> tenantNameById =
        fetchTenantNames(labUser.getId(), t.getAccessToken());
    membershipCache.put(labUser.getId(), tenantsFrom(memberships, tenantNameById));
    // token 带 tenant_id claim（whoami currentTenantId → tenant_key，2026-10-20 人裁）——
    // me() 不再落 demo 默认租户
    return session(
        labUser,
        resolveTenantKey(saasUser.getCurrentTenantId(), tenantNameById),
        tenantsFrom(memberships, tenantNameById),
        t.getRefreshToken());
  }

  // === token 签发 ===

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

  /**
   * 2026-09-15 租户显示名：拉 saas 平台租户列表建 tenantId→{name, tenantKey} 映射。 失败（saas 5xx/网络/4xx）只 warn 返空
   * Map——名字降级 tenantId，不阻塞登录（与 {@link #cacheMenus} 同款 best-effort）。
   */
  private Map<String, SaasMeClient.SaasPlatformTenant> fetchTenantNames(
      String userId, String saasAccessToken) {
    if (userId == null || saasAccessToken == null) {
      return Map.of();
    }
    try {
      return saasMe.listPlatformTenants(saasAccessToken).stream()
          .filter(t -> t.getId() != null && !t.getId().isEmpty())
          .collect(
              java.util.stream.Collectors.toMap(
                  SaasMeClient.SaasPlatformTenant::getId, t -> t, (a, b) -> a));
    } catch (RuntimeException e) {
      log.warn("tenant name lookup failed for user {}: {}", userId, e.getMessage());
      return Map.of();
    }
  }

  private List<MyTenant> tenantsFrom(List<SaasMeClient.SaasTenantMembership> memberships) {
    return tenantsFrom(memberships, Map.of());
  }

  /**
   * memberships → MyTenant。nameById 命中时 tenantId/code=tenantKey、name=name； miss（列表拉取失败 /
   * 租户未在平台注册）降级原 tenantId（2026-10-20 tenant_key 人裁）—— 切换器最差显示原标识，不空。
   */
  private List<MyTenant> tenantsFrom(
      List<SaasMeClient.SaasTenantMembership> memberships,
      Map<String, SaasMeClient.SaasPlatformTenant> nameById) {
    if (memberships == null) {
      return List.of();
    }
    return memberships.stream()
        .map(
            m -> {
              SaasMeClient.SaasPlatformTenant t = nameById.get(m.getTenantId());
              String tid =
                  t != null && t.getTenantKey() != null ? t.getTenantKey() : m.getTenantId();
              return new MyTenant()
                  .tenantId(tid)
                  .code(tid)
                  .name(t != null && t.getName() != null ? t.getName() : m.getTenantId())
                  .roleIds(m.getRoleIds() == null ? List.of() : m.getRoleIds());
            })
        .toList();
  }

  /**
   * 2026-10-20 tenant_key 人裁：对外交互用 key，不用 UUID。平台租户列表命中且 tenantKey 非空 → tenantKey；miss/lookup
   * 失败降级原标识（与 {@link #tenantsFromLogin}/{@link #tenantsFrom} 同款 best-effort 降级语义）。
   */
  private static String resolveTenantKey(
      String saasTenantId, Map<String, SaasMeClient.SaasPlatformTenant> nameById) {
    if (saasTenantId == null) {
      return null;
    }
    SaasMeClient.SaasPlatformTenant pt = nameById.get(saasTenantId);
    return pt != null && pt.getTenantKey() != null && !pt.getTenantKey().isEmpty()
        ? pt.getTenantKey()
        : saasTenantId;
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
