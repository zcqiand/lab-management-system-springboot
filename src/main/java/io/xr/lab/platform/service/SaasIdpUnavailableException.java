package io.xr.lab.platform.service;

/**
 * 登录路径 saas IdP 不可达/5xx（REQ-2026-023：五仓统一 502 SAAS_UNAVAILABLE）。
 *
 * <p>与 sso 路径的 {@code SaasAuthException.UpstreamUnavailable}（→ 502 SAAS_UPSTREAM_ERROR）
 * 分流：原生登录的错误码族与 nextjs/aspnetcore/rails/fastapi 对齐，不透传 saas 内部详情。
 */
public class SaasIdpUnavailableException extends RuntimeException {

  public SaasIdpUnavailableException(String message) {
    super(message);
  }
}
