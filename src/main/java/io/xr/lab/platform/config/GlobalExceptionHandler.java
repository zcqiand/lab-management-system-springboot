package io.xr.lab.platform.config;

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

  @ExceptionHandler(NoSuchElementException.class)
  public ResponseEntity<ErrorResponse> notFound(NoSuchElementException e) {
    return respond(HttpStatus.NOT_FOUND, "NOT_FOUND", e.getMessage());
  }

  private ResponseEntity<ErrorResponse> respond(HttpStatus status, String code, String message) {
    return ResponseEntity.status(status).body(new ErrorResponse().code(code).message(message));
  }
}
