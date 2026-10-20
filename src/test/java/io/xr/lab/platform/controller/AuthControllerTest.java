package io.xr.lab.platform.controller;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.xr.lab.platform.service.AuthService;
import io.xr.lab.shared.dto.LoginRequest;
import io.xr.lab.shared.dto.LoginResponse;
import org.junit.jupiter.api.Test;

/**
 * M01.F05.I06 原生登录端点单测（REQ-2026-023）： AuthController.authNativeLogin 与 web 登录（authLogin）同源——都委派
 * {@link AuthService#login}（同一凭据校验、同一 lab JWT 签发路径）。 service 层语义（saas 4xx→401 / 5xx→502 / 身份租户映射）已在
 * AuthServiceTest 全量覆盖（I01/I06 同源），此处锚定端点层委派契约。
 */
class AuthControllerTest {

  @Test
  void authNativeLogin_delegatesToSameServiceLoginAsWebLogin() {
    AuthService service = mock(AuthService.class);
    LoginRequest request = new LoginRequest().username("alice").password("dev123456");
    LoginResponse response = new LoginResponse();
    when(service.login(request)).thenReturn(response);

    LoginResponse actual = new AuthController(service).authNativeLogin(request).getBody();

    assertSame(response, actual);
    verify(service).login(request);
  }
}
