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

    @Override
    public TokenResponse serviceLogin(String username, String password) {
      // noop：返回与 token() 同款假 accessToken，cacheMenusWithServiceAccount 走通
      // （NoopSaasMeClient.listMyMenus 返回空树 → 空快照也命中，/menus 不 503）
      TokenResponse t = new TokenResponse();
      t.setAccessToken("dev-service-access-token");
      t.setRefreshToken("dev-service-refresh-token");
      t.setTokenType("Bearer");
      t.setExpiresIn(3600);
      return t;
    }
  }

  public static class NoopSaasMeClient extends SaasMeClient {
    @Override
    public SaasCurrentUser whoami(String saasAccessToken) {
      SaasCurrentUser u = new SaasCurrentUser();
      // 2026-09-03 租户体系对齐（aspnetcore 仓 specs/2026-09-03-me-tenant-alignment-design.md）：
      // saas 侧 id/租户改 UUID 体系（与 prod 及 aspnetcore NoopSaasMeClient 一致），
      // 与 lab demo 目录（USER-A / TENANT-00x）可区分 —— 否则 me() 对齐测试区分不出两套体系。
      // email 不再撞 DEMO_USER（alice）→ SSO 走 upsert 路径（与真实 prod 相同）。
      u.setId("00000000-0000-0000-0000-b00000000001");
      u.setEmail("admin@lab.local");
      u.setDisplayName("管理员");
      u.setCurrentTenantId("00000000-0000-0000-0000-000000000001");
      u.setMemberships(tenants());
      return u;
    }

    @Override
    public List<SaasTenantMembership> listMyTenants(String saasAccessToken) {
      return tenants();
    }

    @Override
    public List<SaasMenuNode> listMyMenus(String saasAccessToken, String appCode) {
      // noop：菜单快照不可用 → AuthService.cacheMenus 落 warn，menus() 走 FALLBACK_MENUS
      return List.of();
    }

    private static List<SaasTenantMembership> tenants() {
      return List.of(membership("00000000-0000-0000-0000-000000000001", List.of("admin")));
    }

    private static SaasTenantMembership membership(String tenantId, List<String> roleIds) {
      SaasTenantMembership m = new SaasTenantMembership();
      m.setId("mem-" + tenantId);
      m.setUserId("00000000-0000-0000-0000-b00000000001");
      m.setTenantId(tenantId);
      m.setRoleIds(roleIds);
      m.setStatus("active");
      return m;
    }
  }
}
