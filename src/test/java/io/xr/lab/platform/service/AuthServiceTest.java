package io.xr.lab.platform.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.xr.harness.junit.Fn;
import io.xr.lab.platform.directory.ConfigUserDirectory;
import io.xr.lab.shared.dto.CurrentUserSession;
import io.xr.lab.shared.dto.LoginRequest;
import io.xr.lab.shared.dto.LoginResponse;
import io.xr.lab.shared.dto.MenuNode;
import io.xr.lab.shared.dto.SsoRedirect;
import io.xr.lab.shared.dto.SwitchTenantRequest;
import java.util.Map;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;

/**
 * AuthService 单测（B1 认证域 9 I 级）。目录用真实 {@link ConfigUserDirectory}（纯配置数据，无需 mock）。语义基准：lab-msw
 * handlers-extra.ts authExtraHandlers。
 */
class AuthServiceTest {

  private final AuthService service =
      new AuthService(new ConfigUserDirectory("dev123456"), "http://localhost:3000");

  // === M01.F05.I01 密码登录 ===

  @Test
  @Fn({"M01.F05.I01"})
  void login_success_returnsSessionWithTenants() {
    LoginResponse resp = service.login(new LoginRequest().username("admin").password("dev123456"));
    assertNotNull(resp.getToken());
    assertTrue(resp.getRefreshToken().startsWith("refresh-admin-"));
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
        () -> service.login(new LoginRequest().username("admin").password("wrong")));
  }

  // === M01.F05.I02 SSO 跳转 ===

  @Test
  @Fn({"M01.F05.I02"})
  void ssoAuthorize_buildsSaasLoginUrl() {
    SsoRedirect redirect = service.ssoAuthorize("/dashboard");
    assertEquals(
        "http://localhost:3000/login?redirect=/dashboard&state=mock-state",
        redirect.getAuthorizeUrl());
    assertEquals("mock-state", redirect.getState());
  }

  // === M01.F05.I03 SSO 回调（dev 直发 demo 会话） ===

  @Test
  @Fn({"M01.F05.I03"})
  void ssoCallback_returnsDemoSession() {
    LoginResponse resp = service.ssoCallback(null);
    assertEquals("USER-A", resp.getUser().getId());
    assertEquals(3, resp.getTenants().size());
  }

  // === M01.F05.I04 刷新 token ===

  @Test
  @Fn({"M01.F05.I04"})
  void refresh_roundTripsNewToken() {
    LoginResponse first = service.login(new LoginRequest().username("admin").password("dev123456"));
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

  // === M01.F05.I05 登出（无状态，无服务端 session） ===

  @Test
  @Fn({"M01.F05.I05"})
  void logout_isNoOp() {
    service.logout(new io.xr.lab.shared.dto.AuthLogoutRequest().token("any"));
    // 无异常即通过：无状态 JWT，服务端无 session store。
  }

  // === M00.F01.I01 当前会话 ===

  @Test
  @Fn({"M00.F01.I01"})
  void me_withTenantClaim_respectsClaim() {
    CurrentUserSession session = service.me(Map.of("sub", "admin", "tenant_id", "TENANT-002"));
    assertEquals("TENANT-002", session.getCurrentTenantId());
    assertEquals("USER-A", session.getUser().getId());
    assertEquals(3, session.getTenants().size());
  }

  @Test
  @Fn({"M00.F01.I01"})
  void me_withoutTenantClaim_defaultsToFirstTenant() {
    CurrentUserSession session = service.me(Map.of("sub", "admin"));
    assertEquals("TENANT-001", session.getCurrentTenantId());
  }

  // === M00.F02.I01 选租户换发 ===

  @Test
  @Fn({"M00.F02.I01"})
  void switchTenant_issuesTokenWithTenantClaim() throws Exception {
    LoginResponse resp =
        service.switchTenant(
            Map.of("sub", "admin"), new SwitchTenantRequest().tenantId("TENANT-003"));
    assertNotNull(resp.getToken());
    // token payload 是 base64url，解出来验 tenant_id claim
    String payload =
        new String(
            java.util.Base64.getUrlDecoder().decode(resp.getToken().split("\\.")[1]),
            java.nio.charset.StandardCharsets.UTF_8);
    assertTrue(payload.contains("\"tenant_id\":\"TENANT-003\""));
  }

  @Test
  @Fn({"M00.F02.I01"})
  void switchTenant_unknownTenant_throws404() {
    assertThrows(
        NoSuchElementException.class,
        () ->
            service.switchTenant(
                Map.of("sub", "admin"), new SwitchTenantRequest().tenantId("TENANT-999")));
  }

  // === M01.F04.I01 动态菜单 ===

  @Test
  @Fn({"M01.F04.I01"})
  void menus_returnsFiveRootsMatchingMsw() {
    java.util.List<MenuNode> menus = service.menus();
    assertEquals(5, menus.size());
    assertEquals("menu-dashboard", menus.get(0).getId());
    assertEquals(7, menus.get(2).getChildren().size()); // M03 试验过程 7 子项
  }

  // === M01.F04.I02 权限集 ===

  @Test
  @Fn({"M01.F04.I02"})
  void permissions_returnsAdminFullSet() {
    assertEquals(11, service.permissions().getPermissions().size());
    assertTrue(service.permissions().getPermissions().contains("*"));
  }
}
