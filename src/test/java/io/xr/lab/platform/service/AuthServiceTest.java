package io.xr.lab.platform.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.xr.harness.junit.Fn;
import io.xr.lab.platform.auth.jwt.LabJwtSigner;
import io.xr.lab.platform.auth.jwt.NimbusLabJwtDecoderFactory;
import io.xr.lab.platform.auth.sso.MenuSnapshotCache;
import io.xr.lab.platform.auth.sso.SaasAuthClient;
import io.xr.lab.platform.auth.sso.SaasMeClient;
import io.xr.lab.platform.auth.sso.SaasMenuMapper;
import io.xr.lab.platform.config.LabConfig;
import io.xr.lab.platform.config.SsoBeansConfig;
import io.xr.lab.platform.directory.ConfigUserDirectory;
import io.xr.lab.shared.dto.CurrentUserSession;
import io.xr.lab.shared.dto.LoginRequest;
import io.xr.lab.shared.dto.LoginResponse;
import io.xr.lab.shared.dto.MenuNode;
import io.xr.lab.shared.dto.SwitchTenantRequest;
import java.util.Base64;
import java.util.Map;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.jwt.JwtDecoder;

/**
 * AuthService 单测（B1 认证域 9 I 级，真后端）。
 *
 * <p>用 noop saas beans（{@link SsoBeansConfig.NoopSaasAuthClient} + NoopSaasMeClient），无需 saas 联通。JWT
 * 走真 HMAC HS256 签发，SecretKey ≥32B 满足。
 */
class AuthServiceTest {

  private static final String SECRET =
      "test-lab-jwt-secret-test-lab-jwt-secret-test-lab-jwt-secret"; // ≥32B

  private final LabConfig labConfig =
      new LabConfig(
          new LabConfig.Jwt("lab-test", 3600, 604800),
          new LabConfig.Sso(
              "http://localhost:3000",
              null, // loginUrl 空 → effectiveLoginUrl 回落 saasBase（同旧行为）
              "test-client-id",
              "test-client-secret",
              "00000000-0000-0000-0000-000000000001",
              "http://localhost:5202/api/auth/sso/callback",
              "alice", // 服务账号（密码登录拉菜单快照用，noop saas 下不触网）
              "dev123456"));

  private final LabJwtSigner jwt = new LabJwtSigner(SECRET, "lab-test", 3600, 604800);
  private final SaasAuthClient saasAuth = new SsoBeansConfig.NoopSaasAuthClient();
  private final SaasMeClient saasMe = new SsoBeansConfig.NoopSaasMeClient();

  private final AuthService service =
      new AuthService(
          new ConfigUserDirectory("dev123456"),
          jwt,
          saasAuth,
          saasMe,
          labConfig,
          new MenuSnapshotCache(),
          new SaasMenuMapper());

  @Test
  @Fn({"M01.F05.I01"})
  void login_success_returnsSessionWithTenants() {
    LoginResponse resp = service.login(new LoginRequest().username("alice").password("dev123456"));
    assertNotNull(resp.getToken());
    assertNotNull(resp.getRefreshToken());
    assertEquals("USER-A", resp.getUser().getId());
    assertEquals(3, resp.getTenants().size());
  }

  @Test
  @Fn({"M01.F05.I01"})
  void login_blankFields_throwsBadRequest() {
    assertThrows(
        IllegalArgumentException.class,
        () -> service.login(new LoginRequest().username("").password("x")));
  }

  @Test
  @Fn({"M01.F05.I01"})
  void login_badPassword_throwsUnauthorized() {
    assertThrows(
        SecurityException.class,
        () -> service.login(new LoginRequest().username("alice").password("wrong")));
  }

  @Test
  @Fn({"M01.F05.I02"})
  void ssoAuthorize_returnsAuthorizeUrlAndEchoesState() {
    AuthService.SsoAuthResult result = service.ssoAuthorize("/dashboard", "frontend-csrf-state");
    assertNotNull(result.redirect().getAuthorizeUrl());
    // 2026-08-29 standardized: lab backend no longer pre-fetches code from saas authorize.
    assertTrue(
        result.redirect().getAuthorizeUrl().contains("/login?redirect_uri="),
        "expected 302 to saas login with redirect_uri, got " + result.redirect().getAuthorizeUrl());
    assertTrue(
        result.redirect().getAuthorizeUrl().contains("state=frontend-csrf-state"),
        "expected state echo in authorize url");
    // RFC 6749 §10.12：前端 state 原样透传 saas 回显，前端比对
    assertEquals("frontend-csrf-state", result.redirect().getState());
  }

  @Test
  @Fn({"M01.F05.I03"})
  void ssoCallback_returnsSaasSession() {
    io.xr.lab.shared.dto.SsoCallbackRequest body =
        new io.xr.lab.shared.dto.SsoCallbackRequest()
            .grantType(io.xr.lab.shared.dto.OAuthGrantType.AUTHORIZATION_CODE)
            .code("dev-code")
            .redirectUri("http://localhost:5202/api/auth/sso/callback")
            .state("frontend-csrf-state");

    LoginResponse resp = service.ssoCallback(body);

    // 2026-09-03 Noop saas fixture 改 UUID 体系：SSO 用户是 upsert 的 saas 身份，
    // 租户来自 saas memberships（1 条 UUID），非 demo TENANT-00x
    assertEquals("00000000-0000-0000-0000-b00000000001", resp.getUser().getId());
    assertEquals(1, resp.getTenants().size());
    assertEquals("00000000-0000-0000-0000-000000000001", resp.getTenants().get(0).getTenantId());
    assertNotNull(resp.getToken());
    // refresh token 嵌 saas refresh token
    String refreshPayload =
        new String(
            Base64.getUrlDecoder().decode(resp.getRefreshToken().split("\\.")[1]),
            java.nio.charset.StandardCharsets.UTF_8);
    assertTrue(refreshPayload.contains("\"saas_refresh_token\":\"dev-refresh-token\""));
  }

  @Test
  @Fn({"M01.F05.I04"})
  void refresh_roundTripsNewToken() {
    LoginResponse first = service.login(new LoginRequest().username("alice").password("dev123456"));
    LoginResponse second =
        service.refresh(
            new io.xr.lab.shared.dto.RefreshTokenRequest().refreshToken(first.getRefreshToken()));
    assertNotNull(second.getToken());
    // 2026-09-03 Noop fixture UUID 体系：refresh 走 saas 链 upsert，用户是 saas 身份
    assertEquals("00000000-0000-0000-0000-b00000000001", second.getUser().getId());
  }

  @Test
  @Fn({"M01.F05.I04"})
  void refresh_malformedToken_throws() {
    assertThrows(
        SecurityException.class,
        () -> service.refresh(new io.xr.lab.shared.dto.RefreshTokenRequest().refreshToken("junk")));
  }

  @Test
  @Fn({"M01.F05.I05"})
  void logout_isNoOp() {
    service.logout(new io.xr.lab.shared.dto.AuthLogoutRequest().token("any"));
    // 无异常即通过
  }

  @Test
  @Fn({"M00.F01.I01"})
  void me_withTenantClaim_respectsClaim() {
    CurrentUserSessionImpl(Map.of("sub", "USER-A", "tenant_id", "TENANT-002"));
  }

  @Test
  @Fn({"M00.F01.I01"})
  void me_withoutTenantClaim_defaultsToFirstTenant() {
    CurrentUserSessionImpl(Map.of("sub", "USER-A"));
  }

  private void CurrentUserSessionImpl(Map<String, Object> claims) {
    io.xr.lab.shared.dto.CurrentUserSession session = service.me(claims);
    assertEquals("USER-A", session.getUser().getId());
    assertEquals(3, session.getTenants().size());
    Object expectedTenant =
        claims.containsKey("tenant_id") ? claims.get("tenant_id") : "TENANT-001";
    assertEquals(expectedTenant, session.getCurrentTenantId());
  }

  @Test
  @Fn({"M00.F02.I01"})
  void switchTenant_issuesTokenWithTenantClaim() {
    LoginResponse resp =
        service.switchTenant(
            Map.of("sub", "USER-A"), new SwitchTenantRequest().tenantId("TENANT-003"));
    assertNotNull(resp.getToken());
    // 真 HMAC 签发：验签 claim 里的 tenant_id
    JwtDecoder decoder = NimbusLabJwtDecoderFactory.build(jwt);
    String tenantClaim = decoder.decode(resp.getToken()).getClaim("tenant_id");
    assertEquals("TENANT-003", tenantClaim);
  }

  @Test
  @Fn({"M00.F02.I01"})
  void switchTenant_unknownTenant_throws404() {
    assertThrows(
        NoSuchElementException.class,
        () ->
            service.switchTenant(
                Map.of("sub", "USER-A"), new SwitchTenantRequest().tenantId("TENANT-999")));
  }

  @Test
  @Fn({"M01.F04.I01"})
  void menus_cacheMiss_throwsMenusUnavailable() {
    // 2026-08-27 起 demo 兜底删除：无 saas 快照（快照过期/拉取失败/重启）→
    // MenusUnavailableException（GlobalExceptionHandler 映射 503），前端回退静态菜单
    assertThrows(MenusUnavailableException.class, () -> service.menus(Map.of("sub", "USER-A")));
  }

  @Test
  @Fn({"M01.F04.I01"})
  void menus_cacheHit_returnsSaasSnapshot() {
    // 预填快照：SSO 登录用户的菜单来自缓存（saas 快照映射结果）
    MenuSnapshotCache cache = new MenuSnapshotCache();
    cache.put(
        "USER-A",
        java.util.List.of(
            new MenuNode().id("m-lab-dash").label("总览").path("/dashboard"),
            new MenuNode().id("grp-res").label("资源管理").icon("resource")));
    AuthService ssoService =
        new AuthService(
            new ConfigUserDirectory("dev123456"),
            jwt,
            saasAuth,
            saasMe,
            labConfig,
            cache,
            new SaasMenuMapper());
    java.util.List<MenuNode> menus = ssoService.menus(Map.of("sub", "USER-A"));
    assertEquals(2, menus.size());
    assertEquals("总览", menus.get(0).getLabel());
    assertEquals("资源管理", menus.get(1).getLabel());
  }

  @Test
  @Fn({"M01.F04.I01"})
  void menus_nullClaims_throwsMenusUnavailable() {
    // controller 层 currentClaims() 对非 JWT 上下文返回 Map.of() → 同样 503
    assertThrows(MenusUnavailableException.class, () -> service.menus(Map.of()));
  }

  @Test
  @Fn({"M01.F04.I01"})
  void login_passwordFlow_cachesServiceAccountSnapshot() {
    // 2026-08-27 密码登录也拉快照：dev 用户无 saas 身份，login() 成功后用
    // saas 服务账号（LabConfig.sso 配置）登 saas → 拉 /me/menus → 存 lab userId 快照。
    // noop saas 返回空菜单树 → 快照写入空树（后续 menus() 命中，不再 503）
    MenuSnapshotCache cache = new MenuSnapshotCache();
    AuthService pwdService =
        new AuthService(
            new ConfigUserDirectory("dev123456"),
            jwt,
            saasAuth,
            saasMe,
            labConfig,
            cache,
            new SaasMenuMapper());
    pwdService.login(new LoginRequest().username("alice").password("dev123456"));
    // login 副作用：快照已写入（noop listMyMenus 返回 List.of() → 空树也算命中）
    assertEquals(1, cache.size());
    // menus() 不再抛 MenusUnavailable（快照命中，即便空树）
    pwdService.menus(Map.of("sub", "USER-A"));
  }

  @Test
  @Fn({"M01.F04.I02"})
  void permissions_returnsAdminFullSet() {
    assertEquals(11, service.permissions().getPermissions().size());
    assertTrue(service.permissions().getPermissions().contains("*"));
  }

  // === M00.F01.I01 租户体系对齐（2026-09-03 设计：aspnetcore 仓
  //     docs/superpowers/specs/2026-09-03-me-tenant-alignment-design.md）===
  // SSO 用户的 me() 必须返回 saas memberships 租户（与 ssoCallback 同体系）。

  @Test
  @Fn({"M00.F01.I01"})
  void me_ssoUser_returnsSaasTenants_sameAsSsoCallback() {
    io.xr.lab.shared.dto.SsoCallbackRequest body =
        new io.xr.lab.shared.dto.SsoCallbackRequest()
            .grantType(io.xr.lab.shared.dto.OAuthGrantType.AUTHORIZATION_CODE)
            .code("dev-code")
            .redirectUri("http://localhost:5202/api/auth/sso/callback")
            .state("st-align-1");
    LoginResponse sso = service.ssoCallback(body);

    CurrentUserSession me = service.me(Map.of("sub", sso.getUser().getId()));

    assertEquals(sso.getTenants().size(), me.getTenants().size());
    assertEquals(sso.getTenants().get(0).getTenantId(), me.getTenants().get(0).getTenantId());
    assertEquals(sso.getTenants().get(0).getTenantId(), me.getCurrentTenantId());
  }

  @Test
  @Fn({"M00.F01.I01"})
  void me_passwordUser_stillReturnsDemoTenants() {
    LoginResponse login = service.login(new LoginRequest().username("alice").password("dev123456"));
    CurrentUserSession me = service.me(Map.of("sub", login.getUser().getId()));
    assertEquals("TENANT-001", me.getTenants().get(0).getTenantId());
    assertEquals(3, me.getTenants().size());
  }

  @Test
  @Fn({"M01.F05.I03"})
  void ssoCallback_tokenCarriesTenantIdClaim() {
    io.xr.lab.shared.dto.SsoCallbackRequest body =
        new io.xr.lab.shared.dto.SsoCallbackRequest()
            .grantType(io.xr.lab.shared.dto.OAuthGrantType.AUTHORIZATION_CODE)
            .code("dev-code")
            .redirectUri("http://localhost:5202/api/auth/sso/callback")
            .state("st-align-2");
    LoginResponse res = service.ssoCallback(body);

    JwtDecoder decoder = NimbusLabJwtDecoderFactory.build(jwt);
    String tenantClaim = decoder.decode(res.getToken()).getClaim("tenant_id");
    assertNotNull(tenantClaim, "ssoCallback access token must carry tenant_id claim");
  }
}
