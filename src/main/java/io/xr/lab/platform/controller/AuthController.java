package io.xr.lab.platform.controller;

import io.xr.lab.platform.service.AuthService;
import io.xr.lab.shared.api.AuthApi;
import io.xr.lab.shared.dto.AuthLogoutRequest;
import io.xr.lab.shared.dto.CurrentUserSession;
import io.xr.lab.shared.dto.LoginRequest;
import io.xr.lab.shared.dto.LoginResponse;
import io.xr.lab.shared.dto.MenuNode;
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
 * M00.F01/F02 + M01.F04/F05 - 认证域（B1）。业务在 {@link AuthService}；本层只把当前请求的 JWT claims 交给 service（me /
 * switch-tenant 需要 sub / tenant_id claim）。 生成接口方法无参数， claims 从 SecurityContextHolder
 * 取（登录/refresh/sso 匿名可达，不读上下文）。
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
    return ResponseEntity.ok(service.menus());
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

  @Override
  public ResponseEntity<SsoRedirect> authSsoAuthorize(String redirect) {
    return ResponseEntity.ok(service.ssoAuthorize(redirect));
  }

  @Override
  public ResponseEntity<LoginResponse> authSsoCallback(SsoCallbackRequest ssoCallbackRequest) {
    return ResponseEntity.ok(service.ssoCallback(ssoCallbackRequest));
  }

  @Override
  public ResponseEntity<LoginResponse> authSwitchTenant(SwitchTenantRequest switchTenantRequest) {
    return ResponseEntity.ok(service.switchTenant(currentClaims(), switchTenantRequest));
  }

  private static Map<String, Object> currentClaims() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth instanceof JwtAuthenticationToken token && token.getToken() != null) {
      Jwt jwt = token.getToken();
      return jwt.getClaims();
    }
    return Map.of();
  }
}
