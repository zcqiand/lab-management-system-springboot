package io.xr.lab.platform.service;

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
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * M00.F01/F02 + M01.F04/F05 - 认证域（B1）。
 *
 * <p>语义镜像 lab-msw handlers-extra.ts（DEMO_USER / 3 租户 / 固定菜单与权限集 / SSO 跳 saas 登录页）。真后端差异：token 是自签
 * JWT（dev alg=none，对齐 saas-springboot v0.4.0 惯例）， switch-tenant 换发携带 tenant_id claim 的 token，me 从
 * token claim 解 currentTenantId。
 */
@Service
public class AuthService {

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
  private final String saasBase;

  public AuthService(
      UserDirectory directory,
      @Value("${lab.sso.saas-base:http://localhost:3000}") String saasBase) {
    this.directory = directory;
    this.saasBase = saasBase;
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
    return session(directory.findByUsername(username).orElseThrow());
  }

  // === M01.F05.I04 刷新 token ===

  public LoginResponse refresh(RefreshTokenRequest body) {
    if (body == null || body.getRefreshToken() == null) {
      throw new SecurityException("missing refresh_token");
    }
    // refreshToken 形如 "refresh-<userId>-<epoch>"；userId 自身含 '-'，按前缀 + 末段剥离
    // （saas AuthService.refresh 同款 split bug 的修法）。
    String token = body.getRefreshToken();
    String prefix = "refresh-";
    if (!token.startsWith(prefix)) {
      throw new SecurityException("invalid refresh_token");
    }
    String tokenBody = token.substring(prefix.length());
    int lastDash = tokenBody.lastIndexOf('-');
    if (lastDash <= 0) {
      throw new SecurityException("invalid refresh_token");
    }
    String username = tokenBody.substring(0, lastDash);
    CurrentUser user = directory.findByUsername(username).orElse(null);
    if (user == null) {
      throw new SecurityException("invalid refresh_token");
    }
    return session(user);
  }

  // === M01.F05.I05 登出（无状态 JWT，服务端无 session store） ===

  public void logout(AuthLogoutRequest body) {
    // 前端清存储；服务端无操作。
  }

  // === M00.F01.I01 当前会话 ===

  public CurrentUserSession me(Map<String, Object> claims) {
    CurrentUser user = directory.findByUsername((String) claims.get("sub")).orElse(null);
    if (user == null) {
      throw new SecurityException("unknown user");
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
    CurrentUser user = directory.findByUsername((String) claims.get("sub")).orElse(null);
    if (user == null) {
      throw new SecurityException("unknown user");
    }
    String tenantId = body == null || body.getTenantId() == null ? "" : body.getTenantId();
    MyTenant target = directory.findByTenantId(tenantId).orElse(null);
    if (target == null) {
      throw new NoSuchElementException("Tenant not found");
    }
    return session(user, target.getTenantId());
  }

  // === M01.F04.I01 动态菜单 / I02 权限集 ===

  public List<MenuNode> menus() {
    // 镜像 lab-msw handlers-extra.ts:178-225（5 根节点）。
    return List.of(
        new MenuNode().id("menu-dashboard").label("工作台").path("/dashboard").icon("dashboard"),
        new MenuNode()
            .id("menu-m02")
            .label("资源管理")
            .icon("resource")
            .children(List.of(menu("menu-contracts", "合同管理", "/contracts"))),
        new MenuNode()
            .id("menu-m03")
            .label("试验过程")
            .icon("flow")
            .children(
                List.of(
                    menu("menu-receipts", "接样管理", "/receipts"),
                    menu("menu-task", "任务分配", "/receipts?stage=task_assignment"),
                    menu("menu-entry", "数据录入", "/receipts?stage=data_entry"),
                    menu("menu-review", "报告审核", "/receipts?stage=review"),
                    menu("menu-approve", "报告批准", "/receipts?stage=approval"),
                    menu("menu-issue", "报告发放", "/receipts?stage=issuance"),
                    menu("menu-archive", "报告归档", "/receipts?stage=archived"))),
        new MenuNode()
            .id("menu-m04")
            .label("基础数据")
            .icon("data")
            .children(
                List.of(
                    menu("menu-techreq", "技术要求", "/technical-requirements"),
                    menu("menu-models", "型号维护", "/catalog/models"),
                    menu("menu-specs", "规格维护", "/catalog/specs"),
                    menu("menu-grades", "等级维护", "/catalog/grades"),
                    menu("menu-brands", "牌号维护", "/catalog/brands"))),
        new MenuNode()
            .id("menu-m05")
            .label("数据统计")
            .icon("stats")
            .children(List.of(menu("menu-summary", "报告汇总", "/summary"))));
  }

  public PermissionSet permissions() {
    return new PermissionSet().permissions(DEMO_PERMISSIONS);
  }

  // === M01.F05.I02 SSO 跳转 / I03 SSO 回调 ===

  public SsoRedirect ssoAuthorize(String redirect) {
    // v0.1.x 语义（msw 同款）：authorizeUrl 直接指 saas /login?redirect=...，
    // 浏览器真能跳过去；state 用 dev 固定值（真对接待 saas 端点就绪后换随机 + 校验）。
    String target = redirect == null || redirect.isEmpty() ? "/" : redirect;
    return new SsoRedirect()
        .authorizeUrl(saasBase + "/login?redirect=" + target + "&state=mock-state")
        .state("mock-state");
  }

  public LoginResponse ssoCallback(SsoCallbackRequest body) {
    // dev 直发 demo 会话（msw 同款）；真 code/state 校验待 saas 端点可用。
    return session(directory.findByUsername("admin").orElseThrow());
  }

  // === token 签发（dev alg=none，镜像 saas AuthService.issueAccessToken） ===

  private LoginResponse session(CurrentUser user) {
    return session(user, null);
  }

  private LoginResponse session(CurrentUser user, String tenantId) {
    long now = Instant.now().getEpochSecond();
    return new LoginResponse()
        .token(issueAccessToken(user.getUsername(), tenantId, now))
        .refreshToken("refresh-" + user.getUsername() + "-" + now)
        .user(user)
        .tenants(directory.tenantsOf(user.getUsername()));
  }

  /** dev alg=none JWT。sub 放 username（me/switchTenant 据此查目录）；tenant_id 仅在选过租户后携带。 */
  private String issueAccessToken(String username, String tenantId, long now) {
    String header = b64url("{\"alg\":\"none\",\"typ\":\"JWT\"}");
    String tenantClaim = tenantId == null ? "" : ",\"tenant_id\":\"" + tenantId + "\"";
    String payload =
        b64url(
            "{\"sub\":\""
                + username
                + "\""
                + tenantClaim
                + ",\"iat\":"
                + now
                + ",\"exp\":"
                + (now + 3600)
                + "}");
    return header + "." + payload + ".dev-placeholder";
  }

  private String b64url(String s) {
    return Base64.getUrlEncoder()
        .withoutPadding()
        .encodeToString(s.getBytes(StandardCharsets.UTF_8));
  }

  private static MenuNode menu(String id, String label, String path) {
    return new MenuNode().id(id).label(label).path(path);
  }
}
