package io.xr.lab.platform.config;

import io.xr.lab.platform.auth.sso.SaasAuthClient;
import io.xr.lab.platform.auth.sso.SaasMeClient;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * SSO 客户端 bean 配置（ADR-0008）。
 *
 * <p>两个 profile：
 *
 * <ul>
 *   <li>{@code no-sso}（dev 默认）:注册 {@link NoopSaasAuthClient} + {@link NoopSaasMeClient},无任何外部调用，
 *       行为固定成 admin session（与 lab-msw handlers-extra.ts 保持一致），便于 dev 不启 saas 也能跑通。
 *   <li>{@code default}（prod / CI）:注册真 HTTP 客户端，要求 4 个 saas env 必填（{@code LAB_SAAS_CLIENT_ID} 等）。
 * </ul>
 */
@Configuration
public class SsoBeansConfig {

  @Bean
  @Profile("no-sso")
  public SaasAuthClient noopSaasAuthClient() {
    return new NoopSaasAuthClient();
  }

  @Bean
  @Profile("no-sso")
  public SaasMeClient noopSaasMeClient() {
    return new NoopSaasMeClient();
  }

  @Bean
  @Profile("!no-sso")
  public SaasAuthClient saasAuthClient(LabConfig labConfig) {
    return new SaasAuthClient(
        labConfig.sso().saasBase(),
        labConfig.sso().clientId(),
        labConfig.sso().clientSecret(),
        labConfig.sso().defaultTenantId());
  }

  @Bean
  @Profile("!no-sso")
  public SaasMeClient saasMeClient(LabConfig labConfig) {
    return new SaasMeClient(labConfig.sso().saasBase());
  }

  // === noop 实现 ===

  /** dev 离线模式：authorize 返回 mock code,token/whoami/tenants 返回 admin + 3 租户种子。 */
  public static class NoopSaasAuthClient extends SaasAuthClient {
    @Override
    public AuthorizeCodeResponse authorize(String redirectUri, String scope, String state) {
      AuthorizeCodeResponse resp = new AuthorizeCodeResponse();
      resp.setCode("dev-code");
      resp.setState(state);
      return resp;
    }

    @Override
    public TokenResponse token(
        String grantType, String code, String refreshToken, String redirectUri) {
      TokenResponse t = new TokenResponse();
      t.setAccessToken("dev-access-token");
      t.setRefreshToken("dev-refresh-token");
      t.setTokenType("Bearer");
      t.setExpiresIn(3600);
      t.setScope("openid");
      return t;
    }
  }

  public static class NoopSaasMeClient extends SaasMeClient {
    @Override
    public SaasCurrentUser whoami(String saasAccessToken) {
      SaasCurrentUser u = new SaasCurrentUser();
      u.setId("USER-A");
      u.setEmail("admin@lab.local");
      u.setDisplayName("管理员");
      u.setMemberships(tenants());
      return u;
    }

    @Override
    public List<SaasTenantMembership> listMyTenants(String saasAccessToken) {
      return tenants();
    }

    private static List<SaasTenantMembership> tenants() {
      return List.of(
          membership("TENANT-001", List.of("admin")),
          membership("TENANT-002", List.of("technician")),
          membership("TENANT-003", List.of("viewer")));
    }

    private static SaasTenantMembership membership(String tenantId, List<String> roleIds) {
      SaasTenantMembership m = new SaasTenantMembership();
      m.setId("mem-" + tenantId);
      m.setUserId("USER-A");
      m.setTenantId(tenantId);
      m.setRoleIds(roleIds);
      m.setStatus("active");
      return m;
    }
  }
}
