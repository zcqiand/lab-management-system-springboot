package io.xr.lab.platform.auth.sso;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;

/**
 * SaasMeClient — saas /me/whoami + /me/tenants 调用。
 *
 * <p>lab 拿到 saas access token 后,必须用 Bearer 头鉴权才能拿 CurrentUser(里面含 email + memberships + tenantId)。
 * saas 这两个端点不要求 tenant_id 路径参数,直接走 Bearer 即可。
 *
 * <p>本类不直接 @Component，由 {@link io.xr.lab.platform.config.SsoBeansConfig} 无条件注册真实现（no-sso 降级 profile
 * 已按 2026-09-20 人裁移除）。
 *
 * <p>构造期 fail-fast（env 缺失即抛 IllegalStateException 阻断 bean 创建）—— CT_CONSTRUCTOR_THROW 是 SpotBugs
 * 已知误报（final 字段单赋值场景），已在 spotbugs-exclude.xml 全局豁免。
 */
public class SaasMeClient {

  /**
   * M09.F03.I04 — /me/menus 返 Map<appCode, List<EffectiveMenuNode>> 的 TypeReference（命名静态内部类以满足
   * SpotBugs SIC_INNER_SHOULD_BE_STATIC_ANON）。
   */
  private static final org.springframework.core.ParameterizedTypeReference<
          java.util.Map<String, List<SaasMenuNode>>>
      MENUS_MAP_TYPE =
          org.springframework.core.ParameterizedTypeReference.forType(
              new com.fasterxml.jackson.core.type.TypeReference<
                  java.util.Map<String, List<SaasMenuNode>>>() {}.getType());

  private final RestClient http;

  public SaasMeClient(String saasBase) {
    if (saasBase == null || saasBase.isEmpty()) {
      throw new IllegalStateException("lab.sso.saas-base required for SaasMeClient");
    }
    this.http = SaasHttp.build(saasBase);
  }

  /** 无参构造器（用于测试替身子类继承,跳过 env 校验）。 */
  protected SaasMeClient() {
    this.http = null;
  }

  /** 拉当前用户 id/email/displayName/memberships。 */
  public SaasCurrentUser whoami(String saasAccessToken) {
    try {
      return http.get()
          .uri("/api/v1/me")
          .header(HttpHeaders.AUTHORIZATION, "Bearer " + saasAccessToken)
          .retrieve()
          .body(SaasCurrentUser.class);
    } catch (HttpClientErrorException e) {
      throw new SaasAuthException.InvalidGrant(
          "saas /me " + e.getStatusCode() + " " + truncate(e.getResponseBodyAsString(), 200));
    } catch (HttpServerErrorException e) {
      throw new SaasAuthException.UpstreamUnavailable("saas /me 5xx: " + e.getStatusCode(), e);
    } catch (ResourceAccessException e) {
      throw new SaasAuthException.UpstreamUnavailable("saas /me connect failed", e);
    }
  }

  /** 拉当前用户的所有租户 membership。 */
  public List<SaasTenantMembership> listMyTenants(String saasAccessToken) {
    try {
      SaasTenantMembership[] arr =
          http.get()
              .uri("/api/v1/me/tenants")
              .header(HttpHeaders.AUTHORIZATION, "Bearer " + saasAccessToken)
              .retrieve()
              .body(SaasTenantMembership[].class);
      return arr == null ? List.of() : List.of(arr);
    } catch (HttpClientErrorException e) {
      throw new SaasAuthException.InvalidGrant(
          "saas /me/tenants "
              + e.getStatusCode()
              + " "
              + truncate(e.getResponseBodyAsString(), 200));
    } catch (HttpServerErrorException e) {
      throw new SaasAuthException.UpstreamUnavailable(
          "saas /me/tenants 5xx: " + e.getStatusCode(), e);
    } catch (ResourceAccessException e) {
      throw new SaasAuthException.UpstreamUnavailable("saas /me/tenants connect failed", e);
    }
  }

  /**
   * 拉当前用户在指定 app 下的授权菜单树。
   *
   * <p>2026-08-28 saas MeService.getMyMenus 真实现后，/me/menus 返 Map<appCode, List<EffectiveMenuNode>>
   * （一次性返回该用户在所有 app 下的有效菜单）。本方法拉整张 Map 后按 appCode 取子树。
   */
  public List<SaasMenuNode> listMyMenus(String saasAccessToken, String appCode) {
    try {
      java.util.Map<String, List<SaasMenuNode>> map =
          http.get()
              .uri("/api/v1/me/menus")
              .header(HttpHeaders.AUTHORIZATION, "Bearer " + saasAccessToken)
              .retrieve()
              .body(MENUS_MAP_TYPE);
      if (map == null) {
        return List.of();
      }
      List<SaasMenuNode> tree = map.get(appCode);
      return tree == null ? List.of() : tree;
    } catch (HttpClientErrorException e) {
      throw new SaasAuthException.InvalidGrant(
          "saas /me/menus " + e.getStatusCode() + " " + truncate(e.getResponseBodyAsString(), 200));
    } catch (HttpServerErrorException e) {
      throw new SaasAuthException.UpstreamUnavailable(
          "saas /me/menus 5xx: " + e.getStatusCode(), e);
    } catch (ResourceAccessException e) {
      throw new SaasAuthException.UpstreamUnavailable("saas /me/menus connect failed", e);
    }
  }

  /**
   * 拉平台租户列表（saas GET /api/v1/admin/tenants，guard 只验 JWT——任何登录用户可读）。
   *
   * <p>2026-09-15 租户显示名：memberships 契约（/me、/me/tenants）只有 tenantId 不带名字， lab 侧租户切换器曾显示一串
   * UUID。SSO/refresh 瞬时持 accessToken 时调本方法建 tenantId→{name, tenantKey} 映射填真名（lab-nextjs
   * sso/callback 同款修复）。
   */
  public List<SaasPlatformTenant> listPlatformTenants(String saasAccessToken) {
    try {
      SaasPlatformTenantPage page =
          http.get()
              .uri("/api/v1/admin/tenants?page=0&pageSize=100")
              .header(HttpHeaders.AUTHORIZATION, "Bearer " + saasAccessToken)
              .retrieve()
              .body(SaasPlatformTenantPage.class);
      return page == null || page.getItems() == null ? List.of() : page.getItems();
    } catch (HttpClientErrorException e) {
      throw new SaasAuthException.InvalidGrant(
          "saas /admin/tenants "
              + e.getStatusCode()
              + " "
              + truncate(e.getResponseBodyAsString(), 200));
    } catch (HttpServerErrorException e) {
      throw new SaasAuthException.UpstreamUnavailable(
          "saas /admin/tenants 5xx: " + e.getStatusCode(), e);
    } catch (ResourceAccessException e) {
      throw new SaasAuthException.UpstreamUnavailable("saas /admin/tenants connect failed", e);
    }
  }

  private static String truncate(String s, int max) {
    if (s == null) return "";
    return s.length() <= max ? s : s.substring(0, max) + "...";
  }

  /**
   * saas /api/v1/me/menus 返回的 EffectiveMenuNode（树形：children 递归）。 字段与 saas DB MenuRow 一致；lab 侧映射见
   * {@link SaasMenuMapper}。
   */
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class SaasMenuNode {
    @JsonProperty("id")
    private String id;

    @JsonProperty("appId")
    private String appId;

    @JsonProperty("parentId")
    private String parentId;

    @JsonProperty("code")
    private String code;

    /**
     * saas 实际下发字段是 title（2026-09-14 实测 /me/menus payload：
     * id/clientId/parentId/title/type/path/icon/sortOrder/children）。 原绑 "name" 恒为 null → mapper 兜底
     * label=code，菜单显示英文码 （与 lab-nextjs menu-snapshot / lab-aspnetcore SaasMenuNode 同一轮 2026-09-08
     * /apps 重命名漂移）。
     */
    @JsonProperty("title")
    private String name;

    @JsonProperty("path")
    private String path;

    @JsonProperty("icon")
    private String icon;

    @JsonProperty("type")
    private String type;

    @JsonProperty("sortOrder")
    private Integer sortOrder;

    @JsonProperty("children")
    private List<SaasMenuNode> children;

    public String getId() {
      return id;
    }

    public void setId(String id) {
      this.id = id;
    }

    public String getAppId() {
      return appId;
    }

    public void setAppId(String appId) {
      this.appId = appId;
    }

    public String getParentId() {
      return parentId;
    }

    public void setParentId(String parentId) {
      this.parentId = parentId;
    }

    public String getCode() {
      return code;
    }

    public void setCode(String code) {
      this.code = code;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getPath() {
      return path;
    }

    public void setPath(String path) {
      this.path = path;
    }

    public String getIcon() {
      return icon;
    }

    public void setIcon(String icon) {
      this.icon = icon;
    }

    public String getType() {
      return type;
    }

    public void setType(String type) {
      this.type = type;
    }

    public Integer getSortOrder() {
      return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
      this.sortOrder = sortOrder;
    }

    public List<SaasMenuNode> getChildren() {
      return children;
    }

    public void setChildren(List<SaasMenuNode> children) {
      this.children = children;
    }
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class SaasCurrentUser {
    @JsonProperty("id")
    private String id;

    @JsonProperty("email")
    private String email;

    @JsonProperty("displayName")
    private String displayName;

    @JsonProperty("memberships")
    private List<SaasTenantMembership> memberships;

    @JsonProperty("currentTenantId")
    private String currentTenantId;

    public String getId() {
      return id;
    }

    public void setId(String id) {
      this.id = id;
    }

    public String getEmail() {
      return email;
    }

    public void setEmail(String email) {
      this.email = email;
    }

    public String getDisplayName() {
      return displayName;
    }

    public void setDisplayName(String displayName) {
      this.displayName = displayName;
    }

    public List<SaasTenantMembership> getMemberships() {
      return memberships;
    }

    public void setMemberships(List<SaasTenantMembership> memberships) {
      this.memberships = memberships;
    }

    public String getCurrentTenantId() {
      return currentTenantId;
    }

    public void setCurrentTenantId(String currentTenantId) {
      this.currentTenantId = currentTenantId;
    }
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class SaasTenantMembership {
    @JsonProperty("id")
    private String id;

    @JsonProperty("userId")
    private String userId;

    @JsonProperty("tenantId")
    private String tenantId;

    @JsonProperty("roleIds")
    private List<String> roleIds;

    @JsonProperty("status")
    private String status;

    @JsonProperty("joinedAt")
    private String joinedAt;

    public String getId() {
      return id;
    }

    public void setId(String id) {
      this.id = id;
    }

    public String getUserId() {
      return userId;
    }

    public void setUserId(String userId) {
      this.userId = userId;
    }

    public String getTenantId() {
      return tenantId;
    }

    public void setTenantId(String tenantId) {
      this.tenantId = tenantId;
    }

    public List<String> getRoleIds() {
      return roleIds;
    }

    public void setRoleIds(List<String> roleIds) {
      this.roleIds = roleIds;
    }

    public String getStatus() {
      return status;
    }

    public void setStatus(String status) {
      this.status = status;
    }

    public String getJoinedAt() {
      return joinedAt;
    }

    public void setJoinedAt(String joinedAt) {
      this.joinedAt = joinedAt;
    }
  }

  /** saas GET /api/v1/admin/tenants 分页壳（与家族 list 端点约定同形：page=0/pageSize=20）。 */
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class SaasPlatformTenantPage {
    @JsonProperty("items")
    private List<SaasPlatformTenant> items;

    @JsonProperty("total")
    private Long total;

    public List<SaasPlatformTenant> getItems() {
      return items;
    }

    public void setItems(List<SaasPlatformTenant> items) {
      this.items = items;
    }

    public Long getTotal() {
      return total;
    }

    public void setTotal(Long total) {
      this.total = total;
    }
  }

  /**
   * saas 平台租户行（id/name/tenantKey）。name/tenantKey 用于 memberships 的
   * tenantId→显示名映射（tenantKey→MyTenant.code，name→MyTenant.name）。
   */
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class SaasPlatformTenant {
    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("tenantKey")
    private String tenantKey;

    public String getId() {
      return id;
    }

    public void setId(String id) {
      this.id = id;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getTenantKey() {
      return tenantKey;
    }

    public void setTenantKey(String tenantKey) {
      this.tenantKey = tenantKey;
    }
  }
}
