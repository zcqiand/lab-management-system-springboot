package io.xr.lab.platform.controller;

import io.xr.lab.platform.service.AuthService;
import io.xr.lab.shared.api.AuthApi;
import io.xr.lab.shared.dto.AuthLogoutRequest;
import io.xr.lab.shared.dto.CurrentUserSession;
import io.xr.lab.shared.dto.LoginRequest;
import io.xr.lab.shared.dto.LoginResponse;
import io.xr.lab.shared.dto.MenuNode;
import io.xr.lab.shared.dto.OAuthResponseType;
import io.xr.lab.shared.dto.PermissionSet;
import io.xr.lab.shared.dto.RefreshTokenRequest;
import io.xr.lab.shared.dto.SsoCallbackRequest;
import io.xr.lab.shared.dto.SsoRedirect;
import io.xr.lab.shared.dto.SwitchTenantRequest;
import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.RestController;

/**
 * M00.F01/F02 + M01.F04/F05 - 认证域（B1，真后端）。
 *
 * <p>薄层：从 SecurityContext 取 JWT claims。业务在 {@link AuthService}；Controller 仅转发。
 *
 * <p>Spring ctor 注入 fail-fast + currentClaims 是 private static helper 被 @Override public 方法 static
 * dispatch 调用 —— spotbugs 跟踪不到;两种 pattern 已在 spotbugs-exclude.xml 全局豁免。
 */
@RestController
public class AuthController implements AuthApi {

  private final AuthService service;

  public AuthController(AuthService service) {
    this.service = service;
  }

  @Override
  public ResponseEntity<CurrentUserSession> authGetCurrentUser() {
    return ResponseEntity.ok(service.me(currentClaims()));
  }

  @Override
  public ResponseEntity<List<MenuNode>> authGetMenus() {
    return ResponseEntity.ok(service.menus(currentClaims()));
  }

  @Override
  public ResponseEntity<PermissionSet> authGetPermissions() {
    return ResponseEntity.ok(service.permissions());
  }

  @Override
  public ResponseEntity<LoginResponse> authLogin(LoginRequest loginRequest) {
    return ResponseEntity.ok(service.login(loginRequest));
  }

  @Override
  public ResponseEntity<Void> authLogout(AuthLogoutRequest authLogoutRequest) {
    service.logout(authLogoutRequest);
    return ResponseEntity.noContent().build();
  }

  @Override
  public ResponseEntity<LoginResponse> authRefresh(RefreshTokenRequest refreshTokenRequest) {
    return ResponseEntity.ok(service.refresh(refreshTokenRequest));
  }

  /**
   * M01.F05.I02 — 委派 service 构造 saas authorize 调用，返回 authorizeUrl 给前端跳转。
   *
   * <p>接口签名有 4 个 OAuth 协议参数（responseType / clientId / redirectUri / state）。state 按 RFC 6749 §10.12
   * 由前端生成，后端原样透传给 saas，回跳由前端比对。
   */
  @Override
  public ResponseEntity<SsoRedirect> authSsoAuthorize(
      OAuthResponseType responseType, String clientId, String redirectUri, String state) {
    return ResponseEntity.ok(service.ssoAuthorize(redirectUri, state).redirect());
  }

  /** M01.F05.I03 — code 换 token；state 校验已在前端回跳时完成。 */
  @Override
  public ResponseEntity<LoginResponse> authSsoCallback(SsoCallbackRequest ssoCallbackRequest) {
    return ResponseEntity.ok(service.ssoCallback(ssoCallbackRequest));
  }

  @Override
  public ResponseEntity<LoginResponse> authSwitchTenant(SwitchTenantRequest switchTenantRequest) {
    return ResponseEntity.ok(service.switchTenant(currentClaims(), switchTenantRequest));
  }

  // === helpers（Controller 仅组装 HTTP 不写业务） ===

  private static Map<String, Object> currentClaims() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth instanceof JwtAuthenticationToken token && token.getToken() != null) {
      Jwt jwt = token.getToken();
      return jwt.getClaims();
    }
    return Map.of();
  }
}
