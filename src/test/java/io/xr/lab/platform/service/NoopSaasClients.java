package io.xr.lab.platform.service;

import io.xr.lab.platform.auth.sso.SaasAuthClient;
import io.xr.lab.platform.auth.sso.SaasMeClient;
import java.util.List;

/**
 * 测试替身（原 SsoBeansConfig Noop 内部类，2026-09-20 no-sso profile 废除时迁入测试树）。
 *
 * <p>生产侧恒真链（{@link io.xr.lab.platform.config.SsoBeansConfig} 无条件注册真 HTTP 客户端）后， src
 * 不再保留假实现；AuthService 单测仍需要一个不触网的 saas 替身。
 */
final class NoopSaasClients {

  private NoopSaasClients() {}

  /** token/serviceLogin 返回固定假 token，不触网。 */
  static class NoopSaasAuthClient extends SaasAuthClient {
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
      // 返回与 token() 同款假 accessToken，cacheMenusWithServiceAccount 走通
      // （NoopSaasMeClient.listMyMenus 返回空树 → 空快照也命中，/menus 不 503）
      TokenResponse t = new TokenResponse();
      t.setAccessToken("dev-service-access-token");
      t.setRefreshToken("dev-service-refresh-token");
      t.setTokenType("Bearer");
      t.setExpiresIn(3600);
      return t;
    }
  }

  /** whoami/tenants 返回固定 saas UUID 体系身份 + ACME Corp 显示名；listMyMenus 恒空树。 */
  static class NoopSaasMeClient extends SaasMeClient {
    @Override
    public SaasCurrentUser whoami(String saasAccessToken) {
      SaasCurrentUser u = new SaasCurrentUser();
      // 2026-09-03 租户体系对齐（aspnetcore 仓 specs/2026-09-03-me-tenant-alignment-design.md）：
      // saas 侧 id/租户改 UUID 体系（与真实 prod 及真实 HTTP 客户端行为一致），
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
      // 菜单快照不可用 → AuthService.cacheMenus 落 warn，menus() 走 FALLBACK_MENUS
      return List.of();
    }

    @Override
    public List<SaasPlatformTenant> listPlatformTenants(String saasAccessToken) {
      // 2026-09-15 租户显示名：与 saas_dev 种子同值（id -001 = ACME Corp / acme），
      // 演练 name/tenantKey 注入而非 UUID 充名。
      SaasPlatformTenant t = new SaasPlatformTenant();
      t.setId("00000000-0000-0000-0000-000000000001");
      t.setName("ACME Corp");
      t.setTenantKey("acme");
      return List.of(t);
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
