package io.xr.lab.platform.config;

import io.xr.lab.platform.auth.sso.SaasAuthClient;
import io.xr.lab.platform.auth.sso.SaasMeClient;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * SSO 客户端 bean 配置（ADR-0008）。
 *
 * <p>恒真链：无条件注册真 HTTP 客户端（{@link SaasAuthClient} + {@link SaasMeClient}），要求 4 个 saas env 必填（ {@code
 * LAB_SAAS_CLIENT_ID} 等）。原 {@code no-sso} 降级 profile（Noop 假客户端 + {@code LAB_PROFILE} 开关）已按
 * 2026-09-20 人裁移除——lab 家族不再支持离线模式，ADR-0008 §6 有废止注记。
 *
 * <p>ADR-0019：application.yml 删字面默认值后,@PostConstruct 校验 SSO 凭据与 数据源连接串缺失即 throw（fail-fast）,与
 * saas-springboot JwtIssuer.java 模板对齐。
 */
@Configuration
public class SsoBeansConfig {

  private final LabConfig labConfig;

  public SsoBeansConfig(LabConfig labConfig) {
    this.labConfig = labConfig;
  }

  /** ADR-0019：必填校验（JWT 签发参数 + 服务账号凭据缺失即 throw，禁 demo 字面默认值）。 */
  @PostConstruct
  void validateConfig() {
    // JWT signing key 必填(即便密码登录会话也是 LabJwtSigner 真签 HS256)
    if (labConfig.jwt() == null
        || labConfig.jwt().issuer() == null
        || labConfig.jwt().issuer().isBlank()
        || labConfig.jwt().ttlSeconds() <= 0
        || labConfig.jwt().refreshTtlSeconds() <= 0) {
      throw new IllegalStateException(
          "lab.jwt.{issuer,ttl-seconds,refresh-ttl-seconds} 必填且非空/正整数 (ADR-0019 禁字面默认值). "
              + "Set in .env.local (dev) or env (prod).");
    }
    // SSO 凭据（service account，密码登录拉菜单快照用）
    // 2026-09-19 5.33：service-client-id 补必填（saas LoginRequest.clientId 契约必填,
    // 业务身份字段禁字面默认值——ADR-0019）
    if (labConfig.sso() == null
        || labConfig.sso().serviceUser() == null
        || labConfig.sso().serviceUser().isBlank()
        || labConfig.sso().servicePassword() == null
        || labConfig.sso().servicePassword().isBlank()
        || labConfig.sso().serviceClientId() == null
        || labConfig.sso().serviceClientId().isBlank()) {
      throw new IllegalStateException(
          "lab.sso.{service-user,service-password,service-client-id} 必填 (ADR-0019 禁"
              + " \"alice\"/\"dev123456\" 字面默认值). "
              + "Set in .env.local (dev) or env (prod).");
    }
  }

  @Bean
  public SaasAuthClient saasAuthClient(LabConfig labConfig) {
    return new SaasAuthClient(
        labConfig.sso().saasBase(),
        labConfig.sso().clientId(),
        labConfig.sso().clientSecret(),
        labConfig.sso().defaultTenantId(),
        labConfig.sso().serviceClientId());
  }

  @Bean
  public SaasMeClient saasMeClient(LabConfig labConfig) {
    return new SaasMeClient(labConfig.sso().saasBase());
  }
}
