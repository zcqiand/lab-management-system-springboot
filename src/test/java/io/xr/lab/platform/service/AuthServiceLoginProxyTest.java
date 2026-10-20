package io.xr.lab.platform.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.xr.harness.junit.Fn;
import io.xr.lab.platform.auth.jwt.LabJwtSigner;
import io.xr.lab.platform.auth.sso.MenuSnapshotCache;
import io.xr.lab.platform.auth.sso.SaasAuthClient;
import io.xr.lab.platform.auth.sso.SaasAuthException;
import io.xr.lab.platform.auth.sso.SaasMeClient;
import io.xr.lab.platform.auth.sso.SaasMenuMapper;
import io.xr.lab.platform.config.LabConfig;
import io.xr.lab.platform.directory.ConfigUserDirectory;
import io.xr.lab.shared.dto.LoginRequest;
import io.xr.lab.shared.dto.LoginResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * 登录账密校验代理 saas IdP 单测（REQ-2026-023 T-2，M01.F05.I01/I06 同源 service.login）。
 *
 * <p>锁五仓统一语义：出站 userLogin（clientId=LAB_SAAS_CLIENT_ID，账密原值透传）； saas 200 → user/tenants 映射自 saas
 * 响应（租户名富化 best-effort 降级 name=tenantId）； lab 自家 JWT 重签（tenant_id=saas currentTenantId ??
 * 首租户）；refresh 维持 dev-placeholder； 错误映射：saas 4xx → SecurityException(401
 * INVALID_CREDENTIALS)，5xx/不可达 → {@link SaasIdpUnavailableException}(502 SAAS_UNAVAILABLE)。
 */
class AuthServiceLoginProxyTest {

  private static final String SECRET =
      "test-lab-jwt-secret-test-lab-jwt-secret-test-lab-jwt-secret"; // ≥32B

  private final LabConfig labConfig =
      new LabConfig(
          new LabConfig.Jwt("lab-test", 3600, 604800),
          new LabConfig.Sso(
              "http://localhost:3000",
              null,
              "lab-management",
              "lab-management-secret",
              "00000000-0000-0000-0000-000000000001",
              "http://localhost:5202/api/auth/sso/callback",
              "svc-user",
              "svc-password",
              "lab-management"));

  private final LabJwtSigner jwt = new LabJwtSigner(SECRET, "lab-test", 3600, 604800);

  /** 记录出站账密、可注错的 userLogin 替身。 */
  private static class RecordingSaasAuthClient extends NoopSaasClients.NoopSaasAuthClient {
    String lastUsername;
    String lastPassword;
    RuntimeException failure;
    SaasAuthClient.LoginBody response = loginBody();

    @Override
    public SaasAuthClient.LoginBody userLogin(String username, String password) {
      lastUsername = username;
      lastPassword = password;
      if (failure != null) {
        throw failure;
      }
      return response;
    }
  }

  /** saas 密码登录 200 响应替身（user + availableTenants，无 currentTenantId → 落首租户）。 */
  private static SaasAuthClient.LoginBody loginBody() {
    SaasAuthClient.SaasLoginUser user = new SaasAuthClient.SaasLoginUser();
    user.setId("saas-user-alice");
    user.setUsername("alice");
    user.setEmail("alice@saas.local");
    SaasAuthClient.SaasAvailableTenant t1 = new SaasAuthClient.SaasAvailableTenant();
    t1.setTenantId("TENANT-001");
    t1.setRoleIds(List.of("admin"));
    t1.setStatus("active");
    SaasAuthClient.SaasAvailableTenant t2 = new SaasAuthClient.SaasAvailableTenant();
    t2.setTenantId("TENANT-002");
    t2.setRoleIds(List.of("technician"));
    t2.setStatus("active");
    SaasAuthClient.LoginBody body = new SaasAuthClient.LoginBody();
    body.setUser(user);
    body.setAvailableTenants(List.of(t1, t2));
    body.setAccessToken("saas-access-token");
    body.setRefreshToken("saas-refresh-token");
    body.setUserId("saas-user-alice");
    return body;
  }

  /** 平台租户列表替身：TENANT-001 富化真名/tenantKey，TENANT-002 不在列表（降级路径）。 */
  private static class EnrichingMeClient extends NoopSaasClients.NoopSaasMeClient {
    @Override
    public List<SaasMeClient.SaasPlatformTenant> listPlatformTenants(String saasAccessToken) {
      SaasMeClient.SaasPlatformTenant p = new SaasMeClient.SaasPlatformTenant();
      p.setId("TENANT-001");
      p.setName("市住建工程质量检测中心");
      p.setTenantKey("city-lab");
      return List.of(p);
    }
  }

  private AuthService service(
      RecordingSaasAuthClient saasAuth, SaasMeClient saasMe, MenuSnapshotCache menuCache) {
    return new AuthService(
        new ConfigUserDirectory(),
        jwt,
        saasAuth,
        saasMe,
        labConfig,
        menuCache,
        new SaasMenuMapper());
  }

  @Test
  @Fn({"M01.F05.I01"})
  void login_success_mapsSaasIdentityAndTenants_resignsLabJwt() {
    RecordingSaasAuthClient saasAuth = new RecordingSaasAuthClient();
    LoginResponse resp =
        service(saasAuth, new EnrichingMeClient(), new MenuSnapshotCache())
            .login(new LoginRequest().username("alice").password("pw-123"));

    // 出站透传：账密原值进 userLogin
    assertEquals("alice", saasAuth.lastUsername);
    assertEquals("pw-123", saasAuth.lastPassword);
    // user 映射自 saas（id/username 直取；roleCode=首租户 roleIds[0]）
    assertEquals("saas-user-alice", resp.getUser().getId());
    assertEquals("alice", resp.getUser().getUsername());
    assertEquals("admin", resp.getUser().getRoleCode());
    // tenants 四字段 + 富化真名/tenantKey（TENANT-002 不在平台列表 → 降级 tenantId）
    // 2026-10-20 租户标识=tenant_key（人裁「对外交互用 key」）：平台列表命中的租户
    // tenantId=tenantKey（TENANT-001→city-lab）；miss 降级原 tenantId 不变。
    assertEquals(2, resp.getTenants().size());
    assertEquals("city-lab", resp.getTenants().get(0).getTenantId());
    assertEquals("city-lab", resp.getTenants().get(0).getCode());
    assertEquals("市住建工程质量检测中心", resp.getTenants().get(0).getName());
    assertEquals("TENANT-002", resp.getTenants().get(1).getTenantId());
    assertEquals("TENANT-002", resp.getTenants().get(1).getCode());
    assertEquals("TENANT-002", resp.getTenants().get(1).getName());
    // lab 自家 JWT 重签：可被本仓 signer 验签，tenant_id 落首租户（tenant_key）
    java.util.Map<String, Object> claims = jwt.verify(resp.getToken());
    assertEquals("saas-user-alice", claims.get("sub"));
    assertEquals("city-lab", claims.get("tenant_id"));
    // refresh 维持 dev-placeholder（不透传 saas refreshToken）
    String refreshPayload =
        new String(
            Base64.getUrlDecoder().decode(resp.getRefreshToken().split("\\.")[1]),
            StandardCharsets.UTF_8);
    assert (refreshPayload.contains("\"saas_refresh_token\":\"dev-placeholder\""));
  }

  @Test
  @Fn({"M01.F05.I01"})
  void login_tenantNameLookupFails_degradesAndStillSucceeds() {
    RecordingSaasAuthClient saasAuth = new RecordingSaasAuthClient();
    SaasMeClient failing =
        new NoopSaasClients.NoopSaasMeClient() {
          @Override
          public List<SaasMeClient.SaasPlatformTenant> listPlatformTenants(String saasAccessToken) {
            throw new SaasAuthException.UpstreamUnavailable("saas /admin/tenants 5xx");
          }
        };
    LoginResponse resp =
        service(saasAuth, failing, new MenuSnapshotCache())
            .login(new LoginRequest().username("alice").password("pw-123"));
    assertEquals(2, resp.getTenants().size());
    assertEquals("TENANT-001", resp.getTenants().get(0).getCode());
    assertEquals("TENANT-001", resp.getTenants().get(0).getName());
    assertNotNull(resp.getToken());
  }

  @Test
  @Fn({"M01.F05.I01"})
  void login_saas4xx_throwsSecurityException_invalidCredentials() {
    RecordingSaasAuthClient saasAuth = new RecordingSaasAuthClient();
    saasAuth.failure = new SaasAuthException.InvalidGrant("saas 401 wrong password");
    assertThrows(
        SecurityException.class,
        () ->
            service(saasAuth, new EnrichingMeClient(), new MenuSnapshotCache())
                .login(new LoginRequest().username("alice").password("wrong")));
  }

  @Test
  @Fn({"M01.F05.I01"})
  void login_saasUnavailable_throwsSaasIdpUnavailable_502() {
    RecordingSaasAuthClient saasAuth = new RecordingSaasAuthClient();
    saasAuth.failure = new SaasAuthException.UpstreamUnavailable("saas connect failed");
    assertThrows(
        SaasIdpUnavailableException.class,
        () ->
            service(saasAuth, new EnrichingMeClient(), new MenuSnapshotCache())
                .login(new LoginRequest().username("alice").password("pw-123")));
  }

  // === REQ-2026-023 live 回归（2026-10-11 五目标同时失败指纹） ===
  // 现场：登录响应 tenants / JWT tenant_id claim 已是 saas 租户体系，但登录链
  // 不落 directory upsert + membership 快照 → me/switchTenant 的 resolveUser
  // 报 unknown user 401；switchTenant 归属校验写死 demo 字面量 → saas 租户 404。
  // 契约：登录后 me/switch 必须与登录响应同一租户体系（2026-09-03 对齐约束：
  // tenants.find(activeTenantId) 必命中）。

  /** saas 真实租户是 UUID 形（live 实测），刻意不用 TENANT-00x 字面量——会与 demo 目录撞键掩盖失配。 */
  private static SaasAuthClient.LoginBody saasUuidLoginBody() {
    SaasAuthClient.SaasLoginUser user = new SaasAuthClient.SaasLoginUser();
    user.setId("00000000-0000-0000-0000-b00000000001");
    user.setUsername("alice");
    user.setEmail("alice@saas.local");
    SaasAuthClient.SaasAvailableTenant t1 = new SaasAuthClient.SaasAvailableTenant();
    t1.setTenantId("00000000-0000-0000-0000-000000000001");
    t1.setRoleIds(List.of("admin"));
    t1.setStatus("active");
    SaasAuthClient.SaasAvailableTenant t2 = new SaasAuthClient.SaasAvailableTenant();
    t2.setTenantId("00000000-0000-0000-0000-000000000002");
    t2.setRoleIds(List.of("technician"));
    t2.setStatus("active");
    SaasAuthClient.LoginBody body = new SaasAuthClient.LoginBody();
    body.setUser(user);
    body.setAvailableTenants(List.of(t1, t2));
    body.setAccessToken("saas-access-token");
    body.setRefreshToken("saas-refresh-token");
    body.setUserId("00000000-0000-0000-0000-b00000000001");
    return body;
  }

  @Test
  @Fn({"M00.F01.I01", "M00.F02.I01"})
  void login_sessionCoherence_meAndSwitchTenant_stayInSaasTenantSystem() {
    RecordingSaasAuthClient saasAuth = new RecordingSaasAuthClient();
    saasAuth.response = saasUuidLoginBody();
    AuthService svc = service(saasAuth, new EnrichingMeClient(), new MenuSnapshotCache());
    LoginResponse login = svc.login(new LoginRequest().username("alice").password("pw-123"));
    String sub = login.getUser().getId();
    String tid = login.getTenants().get(0).getTenantId();

    // me：resolveUser 必须能解析密码登录签发的 sub；租户与登录响应同体系
    io.xr.lab.shared.dto.CurrentUserSession me = svc.me(java.util.Map.of("sub", sub));
    assertEquals(tid, me.getTenants().get(0).getTenantId());
    assertEquals(login.getTenants().size(), me.getTenants().size());

    // switch-tenant：登录响应里的租户可切 → 200 + 3 段真 JWT（tenant_id claim=目标租户）
    io.xr.lab.shared.dto.SwitchTenantRequest body =
        new io.xr.lab.shared.dto.SwitchTenantRequest().tenantId(tid);
    LoginResponse switched = svc.switchTenant(java.util.Map.of("sub", sub), body);
    assertEquals(3, switched.getToken().split("\\.").length);
  }
}
