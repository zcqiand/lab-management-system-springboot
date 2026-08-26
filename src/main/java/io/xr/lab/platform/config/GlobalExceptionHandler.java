package io.xr.lab.platform.config;

import io.xr.lab.platform.auth.sso.SaasAuthException;
import io.xr.lab.shared.dto.ErrorResponse;
import java.util.NoSuchElementException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 错误码映射（对齐 lab-msw 的 {code, message} 错误形状）：
 *
 * <ul>
 *   <li>IllegalArgumentException -> 400 BAD_REQUEST
 *   <li>SecurityException / AuthenticationException -> 401 INVALID_CREDENTIALS
 *   <li>NoSuchElementException -> 404 NOT_FOUND
 *   <li>SaasAuthException 按子类分流（对齐 lab-msw 契约：code 重放/过期是 400 INVALID_GRANT）： InvalidGrant -> 400 /
 *       UnauthorizedClient -> 401 / UpstreamUnavailable -> 502
 * </ul>
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ErrorResponse> badRequest(IllegalArgumentException e) {
    return respond(HttpStatus.BAD_REQUEST, "BAD_REQUEST", e.getMessage());
  }

  @ExceptionHandler({SecurityException.class, AuthenticationException.class})
  public ResponseEntity<ErrorResponse> unauthorized(Exception e) {
    return respond(HttpStatus.UNAUTHORIZED, "INVALID_CREDENTIALS", e.getMessage());
  }

  /**
   * SSO 上游（saas IdP）失败 — 按子类分流，带 saas 侧错误详情。曾统一 502（v0.1.16 前是 500 无 body）， 但 code 重放/过期是 saas 的明确
   * 4xx 拒绝（RFC 6749 §5.2 invalid_grant），压成 502 与 lab-msw 契约（400 INVALID_GRANT）不一致，且 CF 会把 5xx
   * 换皮，浏览器只见裸 502 排障困难。
   */
  @ExceptionHandler(SaasAuthException.class)
  public ResponseEntity<ErrorResponse> saasUpstream(SaasAuthException e) {
    if (e instanceof SaasAuthException.InvalidGrant) {
      return respond(HttpStatus.BAD_REQUEST, "INVALID_GRANT", e.getMessage());
    }
    if (e instanceof SaasAuthException.UnauthorizedClient) {
      return respond(HttpStatus.UNAUTHORIZED, "INVALID_CREDENTIALS", e.getMessage());
    }
    return respond(HttpStatus.BAD_GATEWAY, "SAAS_UPSTREAM_ERROR", e.getMessage());
  }

  @ExceptionHandler(NoSuchElementException.class)
  public ResponseEntity<ErrorResponse> notFound(NoSuchElementException e) {
    return respond(HttpStatus.NOT_FOUND, "NOT_FOUND", e.getMessage());
  }

  private ResponseEntity<ErrorResponse> respond(HttpStatus status, String code, String message) {
    return ResponseEntity.status(status).body(new ErrorResponse().code(code).message(message));
  }
}
