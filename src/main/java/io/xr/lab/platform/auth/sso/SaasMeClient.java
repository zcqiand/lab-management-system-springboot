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
 * <p>本类不直接 @Component，由 {@link io.xr.lab.platform.config.SsoBeansConfig} 按 profile 选 real/noop 实现。
 *
 * <p>{@code @SuppressFBWarnings}:CT_CONSTRUCTOR_THROW — 同 SaasAuthClient 的 fail-fast 设计。
 */
@edu.umd.cs.findbugs.annotations.SuppressFBWarnings(
    value = "CT_CONSTRUCTOR_THROW",
    justification = "fail-fast 设计:env 缺失立即抛 IllegalStateException 阻断 bean 创建")
public class SaasMeClient {

  private final RestClient http;

  public SaasMeClient(String saasBase) {
    if (saasBase == null || saasBase.isEmpty()) {
      throw new IllegalStateException("lab.sso.saas-base required for SaasMeClient");
    }
    this.http = SaasHttp.build(saasBase);
  }

  /** 无参构造器（用于 Noop 子类继承,跳过 env 校验）。 */
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

  /** 拉当前用户在指定 app 下的授权菜单树（saas EffectiveMenuNode[] 扁平树形数组）。 */
  public List<SaasMenuNode> listMyMenus(String saasAccessToken, String appCode) {
    try {
      SaasMenuNode[] arr =
          http.get()
              .uri("/api/v1/me/menus?appCode=" + appCode)
              .header(HttpHeaders.AUTHORIZATION, "Bearer " + saasAccessToken)
              .retrieve()
              .body(SaasMenuNode[].class);
      return arr == null ? List.of() : List.of(arr);
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

  private static String truncate(String s, int max) {
    if (s == null) return "";
    return s.length() <= max ? s : s.substring(0, max) + "...";
  }

  /**
   * saas /api/v1/me/menus 返回的 EffectiveMenuNode（树形：children 递归）。 字段与 saas DB MenuRow 一致；lab 侧映射见
   * {@link SaasMenuMapper}。
   */
  @JsonIgnoreProperties(ignoreUnknown = true)
  @edu.umd.cs.findbugs.annotations.SuppressFBWarnings(
      value = {"EI_EXPOSE_REP", "EI_EXPOSE_REP2"},
      justification =
          "DTO mirroring saas JSON contract; instances are read-only after deserialization")
  public static class SaasMenuNode {
    @JsonProperty("id")
    private String id;

    @JsonProperty("appId")
    private String appId;

    @JsonProperty("parentId")
    private String parentId;

    @JsonProperty("code")
    private String code;

    @JsonProperty("name")
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
  @edu.umd.cs.findbugs.annotations.SuppressFBWarnings(
      value = {"EI_EXPOSE_REP", "EI_EXPOSE_REP2"},
      justification =
          "DTO mirroring saas JSON contract; List returned/stored is read-only in this scope")
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
  @edu.umd.cs.findbugs.annotations.SuppressFBWarnings(
      value = {"EI_EXPOSE_REP", "EI_EXPOSE_REP2"},
      justification =
          "DTO mirroring saas JSON contract; List returned/stored is read-only in this scope")
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
}
