package io.xr.lab.platform.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * lab.* 配置聚合。覆盖 jwt + sso + 数据源外的业务配置。
 *
 * <p>对应 src/main/resources/application.yml 中 {@code lab:} 段。
 *
 * @param jwt JWT 签发配置
 * @param sso saas OAuth 2.0 集成配置
 */
@ConfigurationProperties(prefix = "lab")
public record LabConfig(Jwt jwt, Sso sso) {

  public record Jwt(String issuer, long ttlSeconds, long refreshTtlSeconds) {}

  /**
   * @param saasBase saas 后端 API base（SaasAuthClient/SaasMeClient 调 /api/v1/oauth|me 用）
   * @param loginUrl saas IdP 登录页 base（authorizeUrl 拼 {loginUrl}/login?code=...；该页面 由 saas 前端提供，不在
   *     API 域名上）。空则回落 saasBase（dev 时 nextjs 同源）
   */
  public record Sso(
      String saasBase,
      String loginUrl,
      String clientId,
      String clientSecret,
      String defaultTenantId,
      String callbackRedirectBase) {

    public String effectiveLoginUrl() {
      return loginUrl == null || loginUrl.isBlank() ? saasBase : loginUrl;
    }
  }
}
