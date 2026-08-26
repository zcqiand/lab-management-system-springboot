package io.xr.lab.platform.config;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.xr.harness.junit.Fn;
import io.xr.lab.platform.auth.sso.SaasAuthException;
import io.xr.lab.shared.dto.ErrorResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * SaasAuthException 子类 → HTTP 状态码分流（GlobalExceptionHandler）。
 *
 * <p>跨端契约对齐 lab-msw（handlers-extra.ts sso/callback）：code 重放/过期 → 400 INVALID_GRANT；client 凭证错 →
 * 401；saas 不可达/5xx → 502。曾统一 502，CF 换皮后浏览器只见裸 502，与 msw 400 契约不一致。
 */
class GlobalExceptionHandlerTest {

  private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

  @Test
  @Fn({"M01.F05.I03"})
  void saasInvalidGrant_maps400() {
    ResponseEntity<ErrorResponse> resp =
        handler.saasUpstream(new SaasAuthException.InvalidGrant("saas 400 invalid_grant"));
    assertEquals(HttpStatus.BAD_REQUEST, resp.getStatusCode());
    assertEquals("INVALID_GRANT", resp.getBody().getCode());
  }

  @Test
  @Fn({"M01.F05.I03"})
  void saasUnauthorizedClient_maps401() {
    ResponseEntity<ErrorResponse> resp =
        handler.saasUpstream(
            new SaasAuthException.UnauthorizedClient("saas 401 unauthorized_client"));
    assertEquals(HttpStatus.UNAUTHORIZED, resp.getStatusCode());
    assertEquals("INVALID_CREDENTIALS", resp.getBody().getCode());
  }

  @Test
  @Fn({"M01.F05.I03"})
  void saasUpstreamUnavailable_maps502() {
    ResponseEntity<ErrorResponse> resp =
        handler.saasUpstream(new SaasAuthException.UpstreamUnavailable("saas connect failed"));
    assertEquals(HttpStatus.BAD_GATEWAY, resp.getStatusCode());
    assertEquals("SAAS_UPSTREAM_ERROR", resp.getBody().getCode());
  }
}
