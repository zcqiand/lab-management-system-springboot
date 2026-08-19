package io.xr.lab.platform.controller;

import io.xr.lab.platform.auth.state.StateCookieManager;
import io.xr.lab.platform.service.AuthService;
import io.xr.lab.platform.service.AuthService.SsoAuthResult;
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
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * M00.F01/F02 + M01.F04/F05 - 认证域（B1，真后端）。
 *
 * <p>薄层：从 HttpContext 取 JWT claims + 写 Set-Cookie 头（state CSRF）。业务在 {@link AuthService}；Controller
 * 仅转发。
 *
 * <p>{@code @SuppressFBWarnings}:
 *
 * <ul>
 *   <li>CT_CONSTRUCTOR_THROW — 构造期 fail-fast 是设计意图;Spring 容器包装
 *   <li>UPM_UNCALLED_PRIVATE_METHOD / UUF_UNUSED_FIELD — false positive: appendStateCookie /
 *       extractStateCookie / currentRequest / currentResponse / currentClaims 全部被本类同名
 *       public @Override 方法通过 static dispatch 调用;SpotBugs 跟踪不到 private static 方法调用
 * </ul>
 */
@RestController
@edu.umd.cs.findbugs.annotations.SuppressFBWarnings(
    value = {"CT_CONSTRUCTOR_THROW", "UPM_UNCALLED_PRIVATE_METHOD", "UUF_UNUSED_FIELD"},
    justification =
        "CT_CONSTRUCTOR_THROW: Spring ctor 注入 fail-fast; UPM/UUF: private static helpers 被 @Override"
            + " public 方法 static dispatch 调用,SpotBugs 跟踪不到")
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

  /**
   * M01.F05.I02 — 写 HttpOnly Secure Cookie 包装 state，body 给前端跳转 URL。
   *
   * <p>接口签名有 4 个 OAuth 协议参数（responseType / clientId / redirectUri / state），lab 业务只关心
   * redirectUri；后端构造 saas 调用自带其余三个。这里记录请求参数后委派核心法。
   */
  @Override
  public ResponseEntity<SsoRedirect> authSsoAuthorize(
      OAuthResponseType responseType, String clientId, String redirectUri, String state) {
    SsoAuthResult result = service.ssoAuthorize(redirectUri);
    appendStateCookie(currentResponse(), result.cookieValue());
    return ResponseEntity.ok(result.redirect());
  }

  /**
   * M01.F05.I03 — 从 cookie 拿 state, 跟 body.state 一起交给 service 校验。
   *
   * <p>Spring MVC 通过 {@link RequestContextHolder} 提供 当 前 请求线程的 {@link
   * HttpServletRequest},无需 @RequestParam 注入额外参数。
   */
  @Override
  public ResponseEntity<LoginResponse> authSsoCallback(SsoCallbackRequest ssoCallbackRequest) {
    String cookieValue = extractStateCookie(currentRequest());
    return ResponseEntity.ok(service.ssoCallback(ssoCallbackRequest, cookieValue));
  }

  @Override
  public ResponseEntity<LoginResponse> authSwitchTenant(SwitchTenantRequest switchTenantRequest) {
    return ResponseEntity.ok(service.switchTenant(currentClaims(), switchTenantRequest));
  }

  // === helpers（Controller 仅组装 HTTP 不写业务） ===

  private static void appendStateCookie(HttpServletResponse resp, String cookieValue) {
    Cookie cookie = new Cookie(StateCookieManager.cookieName(), cookieValue);
    cookie.setHttpOnly(true);
    cookie.setSecure(false); // dev:false;prod 切 true（环境变量控制）
    cookie.setPath("/api/auth/sso/callback");
    cookie.setMaxAge(StateCookieManager.maxAgeSeconds());
    cookie.setAttribute("SameSite", "Lax");
    resp.addCookie(cookie);
  }

  private static String extractStateCookie(HttpServletRequest req) {
    if (req == null || req.getCookies() == null) {
      return null;
    }
    for (Cookie c : req.getCookies()) {
      if (StateCookieManager.cookieName().equals(c.getName())) {
        return c.getValue();
      }
    }
    return null;
  }

  private static HttpServletRequest currentRequest() {
    if (RequestContextHolder.getRequestAttributes() instanceof ServletRequestAttributes attrs) {
      return attrs.getRequest();
    }
    return null;
  }

  private static HttpServletResponse currentResponse() {
    if (RequestContextHolder.getRequestAttributes() instanceof ServletRequestAttributes attrs) {
      return attrs.getResponse();
    }
    return null;
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
