package io.xr.lab.platform.auth.sso;

/**
 * saas upstream 调用失败聚合。子类对应不同 HTTP 状态码语义：
 *
 * <ul>
 *   <li>{@link InvalidGrant} — HTTP 400，code 已用 / 过期 / grant_type 错
 *   <li>{@link UnauthorizedClient} — HTTP 401，client_id / client_secret 错
 *   <li>{@link UpstreamUnavailable} — HTTP 5xx / 连接失败
 * </ul>
 *
 * <p>GlobalExceptionHandler 把 SQLException 之外的异常映射到状态码时,对 {@link SaasAuthException} 按子类区分；上层
 * (AuthService) 取到子类后再映射回 OpenAPI 失败语义（400 / 401 / 502）,不把 saas 内部结构泄漏给前端。
 */
public sealed class SaasAuthException extends RuntimeException
    permits SaasAuthException.InvalidGrant,
        SaasAuthException.UnauthorizedClient,
        SaasAuthException.UpstreamUnavailable {

  private final int status;

  protected SaasAuthException(String message, int status) {
    super(message);
    this.status = status;
  }

  public int status() {
    return status;
  }

  public static final class InvalidGrant extends SaasAuthException {
    public InvalidGrant(String message) {
      super(message, 400);
    }
  }

  public static final class UnauthorizedClient extends SaasAuthException {
    public UnauthorizedClient(String message) {
      super(message, 401);
    }
  }

  public static final class UpstreamUnavailable extends SaasAuthException {
    public UpstreamUnavailable(String message) {
      super(message, 502);
    }

    public UpstreamUnavailable(String message, Throwable cause) {
      super(message, 502);
      initCause(cause);
    }
  }
}
