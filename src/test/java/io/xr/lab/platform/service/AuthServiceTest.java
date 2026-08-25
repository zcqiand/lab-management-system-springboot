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
              "test-client-id",
              "test-client-secret",
              "00000000-0000-0000-0000-000000000001",
              "http://localhost:8080/api/auth/sso/callback"));

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
    LoginResponse resp =
        service.login(new LoginRequest().username("admin@lab.local").password("dev123456"));
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
        () -> service.login(new LoginRequest().username("admin@lab.local").password("wrong")));
  }

  @Test
  @Fn({"M01.F05.I02"})
  void ssoAuthorize_returnsAuthorizeUrlAndEchoesState() {
    AuthService.SsoAuthResult result = service.ssoAuthorize("/dashboard", "frontend-csrf-state");
    assertNotNull(result.redirect().getAuthorizeUrl());
    assertTrue(result.redirect().getAuthorizeUrl().contains("code=dev-code"));
    // RFC 6749 §10.12：前端 state 原样透传 saas 回显，前端比对
    assertEquals("frontend-csrf-state", result.redirect().getState());
  }

  @Test
  @Fn({"M01.F05.I03"})
  void ssoCallback_returnsDemoSession() {
    io.xr.lab.shared.dto.SsoCallbackRequest body =
        new io.xr.lab.shared.dto.SsoCallbackRequest()
            .grantType(io.xr.lab.shared.dto.OAuthGrantType.AUTHORIZATION_CODE)
            .code("dev-code")
            .redirectUri("http://localhost:8080/api/auth/sso/callback")
            .state("frontend-csrf-state");

    LoginResponse resp = service.ssoCallback(body);

    assertEquals("USER-A", resp.getUser().getId());
    assertEquals(3, resp.getTenants().size());
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
    LoginResponse first =
        service.login(new LoginRequest().username("admin@lab.local").password("dev123456"));
    LoginResponse second =
        service.refresh(
            new io.xr.lab.shared.dto.RefreshTokenRequest().refreshToken(first.getRefreshToken()));
    assertNotNull(second.getToken());
    assertEquals("USER-A", second.getUser().getId());
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
  void menus_cacheMiss_returnsFallbackFiveRoots() {
    // 无 saas 快照（密码登录用户 / noop / 缓存过期）→ 静态兜底菜单
    java.util.List<MenuNode> menus = service.menus(Map.of("sub", "USER-A"));
    assertEquals(5, menus.size());
    assertEquals("menu-dashboard", menus.get(0).getId());
    assertEquals(7, menus.get(2).getChildren().size());
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
  void menus_nullClaims_returnsFallback() {
    // controller 层 currentClaims() 对非 JWT 上下文返回 Map.of() → menus 不抛
    assertEquals(5, service.menus(Map.of()).size());
  }

  @Test
  @Fn({"M01.F04.I02"})
  void permissions_returnsAdminFullSet() {
    assertEquals(11, service.permissions().getPermissions().size());
    assertTrue(service.permissions().getPermissions().contains("*"));
  }
}
