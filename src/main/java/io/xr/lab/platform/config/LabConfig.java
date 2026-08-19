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

  public record Sso(
      String saasBase,
      String clientId,
      String clientSecret,
      String defaultTenantId,
      String callbackRedirectBase) {}
}
