package io.xr.lab.platform.auth.sso;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.LinkedHashMap;
import java.util.List;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;

/**
 * SaasAuthClient — 真对接 saas-identity-platform 的 OAuth 2.0 端点。
 *
 * <p>覆盖 RFC 6749 §4.1.3 / §6 (token) + saas 服务账号密码登录。失败映射到 {@link SaasAuthException}：
 *
 * <ul>
 *   <li>400 → {@link SaasAuthException.InvalidGrant}（invalid_grant / invalid_request）
 *   <li>401 → {@link SaasAuthException.UnauthorizedClient}（client_id / client_secret 错）
 *   <li>5xx / IO 失败 → {@link SaasAuthException.UpstreamUnavailable}
 * </ul>
 *
 * <p>请求体 application/json（saas 端契约源自 TypeSpec,saas-springboot Controller 收 JSON）。{@code client_id}
 * / {@code clientSecret} 走 body,不再加 Authorization Basic 头（saas 双接受,body 简单）。
 *
 * <p>本类不直接 @Component，由 {@link io.xr.lab.platform.config.SsoBeansConfig} 无条件注册真实现（no-sso 降级 profile
 * 已按 2026-09-20 人裁移除）。
 *
 * <p>构造期 fail-fast（env 缺失即抛 IllegalStateException 阻断 bean 创建）—— CT_CONSTRUCTOR_THROW 是 SpotBugs
 * 已知误报（final 字段单赋值场景），已在 spotbugs-exclude.xml 全局豁免。
 */
public class SaasAuthClient {

  private final RestClient http;
  private final String clientId;
  private final String clientSecret;
  private final String defaultTenantId;
  private final String serviceClientId;

  public SaasAuthClient(
      String saasBase,
      String clientId,
      String clientSecret,
      String defaultTenantId,
      String serviceClientId) {
    if (saasBase == null || saasBase.isEmpty()) {
      throw new IllegalStateException("lab.sso.saas-base required");
    }
    if (clientId == null || clientId.isEmpty()) {
      throw new IllegalStateException("LAB_SAAS_CLIENT_ID required");
    }
    if (clientSecret == null || clientSecret.isEmpty()) {
      throw new IllegalStateException("LAB_SAAS_CLIENT_SECRET required");
    }
    if (defaultTenantId == null || defaultTenantId.isEmpty()) {
      throw new IllegalStateException("LAB_SAAS_DEFAULT_TENANT_ID required");
    }
    if (serviceClientId == null || serviceClientId.isEmpty()) {
      throw new IllegalStateException("LAB_SAAS_SERVICE_CLIENT_ID required");
    }
    this.http = SaasHttp.build(saasBase);
    this.clientId = clientId;
    this.clientSecret = clientSecret;
    this.defaultTenantId = defaultTenantId;
    this.serviceClientId = serviceClientId;
  }

  /** 无参构造器（用于测试替身子类继承,跳过 env 校验）。 */
  protected SaasAuthClient() {
    this.http = null;
    this.clientId = null;
    this.clientSecret = null;
    this.defaultTenantId = null;
    this.serviceClientId = null;
  }

  /**
   * saas /api/v1/auth/login 密码登录（服务账号用）。lab 密码登录的 dev 用户无 saas 身份， login() 成功后用本方法以 env
   * 配置的服务账号（LAB_SAAS_SERVICE_USER/PASSWORD，dev 默认 alice/dev123456） 换 saas accessToken 再拉 /me/menus
   * 快照。失败映射与 OAuth 端点同款。
   *
   * <p>2026-09-19 5.33：body 对齐 saas LoginRequest 契约 {username, password, clientId}——clientId 取
   * LAB_SAAS_SERVICE_CLIENT_ID（oauth_client code 形，dev/prod = lab-management）；陈旧字段 tenantCode
   * 删（契约无此字段， 修前 saas 回 fieldErrors.clientId Required → 菜单快照 503）。
   */
  public TokenResponse serviceLogin(String username, String password) {
    LinkedHashMap<String, String> body = new LinkedHashMap<>();
    body.put("username", username);
    body.put("password", password);
    body.put("clientId", serviceClientId);
    try {
      return http.post()
          .uri("/api/v1/auth/login")
          .contentType(MediaType.APPLICATION_JSON)
          .body(body)
          .retrieve()
          .body(TokenResponse.class);
    } catch (HttpClientErrorException e) {
      throw mapClientError(e);
    } catch (HttpServerErrorException e) {
      throw new SaasAuthException.UpstreamUnavailable("saas upstream 5xx: " + e.getStatusCode(), e);
    } catch (ResourceAccessException e) {
      throw new SaasAuthException.UpstreamUnavailable("saas connect failed", e);
    }
  }

  // 2026-09-19 删 authorize()（OAuth §4.1.1 服务端 code 预拿）：2026-09-15 全家族收敛为
  // 「200 JSON 跳板」语义（返回 {authorizeUrl, state}，前端顶层导航到 saas 登录页），
  // 服务端不再预拿 code → 本方法零生产调用方（grep 全家族确认），死代码删除（裁定 1.3）。
  // code 换 token 的 §4.1.3 仍走 token()。

  /**
   * REQ-2026-023 T-2：原生登录账密校验代理——用<b>请求体账密</b>调 saas 密码登录，clientId 取 OAuth
   * clientId（LAB_SAAS_CLIENT_ID，构造器第 2 参），非服务账号的 serviceClientId。错误映射与 serviceLogin 同款（4xx →
   * InvalidGrant/UnauthorizedClient；5xx/IO → UpstreamUnavailable）。
   */
  public LoginBody userLogin(String username, String password) {
    LinkedHashMap<String, String> body = new LinkedHashMap<>();
    body.put("username", username);
    body.put("password", password);
    body.put("clientId", clientId);
    try {
      return http.post()
          .uri("/api/v1/auth/login")
          .contentType(MediaType.APPLICATION_JSON)
          .body(body)
          .retrieve()
          .body(LoginBody.class);
    } catch (HttpClientErrorException e) {
      throw mapClientError(e);
    } catch (HttpServerErrorException e) {
      throw new SaasAuthException.UpstreamUnavailable("saas upstream 5xx: " + e.getStatusCode(), e);
    } catch (ResourceAccessException e) {
      throw new SaasAuthException.UpstreamUnavailable("saas connect failed", e);
    }
  }

  /** OAuth 2.0 §4.1.3 / §6 — 拿 code 换 access token，或用 refresh_token 续。 */
  public TokenResponse token(
      String grantType, String code, String refreshToken, String redirectUri) {
    LinkedHashMap<String, String> body = new LinkedHashMap<>();
    body.put("grantType", grantType);
    body.put("clientId", clientId);
    body.put("clientSecret", clientSecret);
    body.put("tenantId", defaultTenantId);
    if (code != null) body.put("code", code);
    if (refreshToken != null) body.put("refreshToken", refreshToken);
    if (redirectUri != null) body.put("redirectUri", redirectUri);

    try {
      return http.post()
          .uri("/api/v1/oauth/token")
          .contentType(MediaType.APPLICATION_JSON)
          .body(body)
          .retrieve()
          .body(TokenResponse.class);
    } catch (HttpClientErrorException e) {
      throw mapClientError(e);
    } catch (HttpServerErrorException e) {
      throw new SaasAuthException.UpstreamUnavailable("saas upstream 5xx: " + e.getStatusCode(), e);
    } catch (ResourceAccessException e) {
      throw new SaasAuthException.UpstreamUnavailable("saas connect failed", e);
    }
  }

  private static SaasAuthException mapClientError(HttpClientErrorException e) {
    HttpStatusCode status = e.getStatusCode();
    if (status.value() == 401) {
      return new SaasAuthException.UnauthorizedClient("saas 401 unauthorized_client");
    }
    return new SaasAuthException.InvalidGrant(
        "saas " + status.value() + " " + truncate(e.getResponseBodyAsString(), 200));
  }

  private static String truncate(String s, int max) {
    if (s == null) return "";
    return s.length() <= max ? s : s.substring(0, max) + "...";
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class TokenResponse {
    @JsonProperty("accessToken")
    private String accessToken;

    @JsonProperty("refreshToken")
    private String refreshToken;

    @JsonProperty("tokenType")
    private String tokenType;

    @JsonProperty("expiresIn")
    private int expiresIn;

    @JsonProperty("scope")
    private String scope;

    public String getAccessToken() {
      return accessToken;
    }

    public void setAccessToken(String v) {
      this.accessToken = v;
    }

    public String getRefreshToken() {
      return refreshToken;
    }

    public void setRefreshToken(String v) {
      this.refreshToken = v;
    }

    public String getTokenType() {
      return tokenType;
    }

    public void setTokenType(String v) {
      this.tokenType = v;
    }

    public int getExpiresIn() {
      return expiresIn;
    }

    public void setExpiresIn(int v) {
      this.expiresIn = v;
    }

    public String getScope() {
      return scope;
    }

    public void setScope(String v) {
      this.scope = v;
    }
  }

  /**
   * REQ-2026-023：saas 密码登录响应体（{@code /api/v1/auth/login}）。user 无 displayName、 availableTenants
   * 无租户名——富化在 AuthService 侧 best-effort（admin/tenants）。
   */
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class LoginBody {
    @JsonProperty("user")
    private SaasLoginUser user;

    @JsonProperty("availableTenants")
    private List<SaasAvailableTenant> availableTenants;

    @JsonProperty("accessToken")
    private String accessToken;

    @JsonProperty("refreshToken")
    private String refreshToken;

    @JsonProperty("userId")
    private String userId;

    @JsonProperty("currentTenantId")
    private String currentTenantId;

    public SaasLoginUser getUser() {
      return user;
    }

    public void setUser(SaasLoginUser v) {
      this.user = v;
    }

    public List<SaasAvailableTenant> getAvailableTenants() {
      return availableTenants;
    }

    public void setAvailableTenants(List<SaasAvailableTenant> v) {
      this.availableTenants = v;
    }

    public String getAccessToken() {
      return accessToken;
    }

    public void setAccessToken(String v) {
      this.accessToken = v;
    }

    public String getRefreshToken() {
      return refreshToken;
    }

    public void setRefreshToken(String v) {
      this.refreshToken = v;
    }

    public String getUserId() {
      return userId;
    }

    public void setUserId(String v) {
      this.userId = v;
    }

    public String getCurrentTenantId() {
      return currentTenantId;
    }

    public void setCurrentTenantId(String v) {
      this.currentTenantId = v;
    }
  }

  /** saas 密码登录响应的 user 行（无 displayName——回退链在 AuthService 侧）。 */
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class SaasLoginUser {
    @JsonProperty("id")
    private String id;

    @JsonProperty("username")
    private String username;

    @JsonProperty("email")
    private String email;

    @JsonProperty("displayName")
    private String displayName;

    @JsonProperty("status")
    private String status;

    public String getId() {
      return id;
    }

    public void setId(String v) {
      this.id = v;
    }

    public String getUsername() {
      return username;
    }

    public void setUsername(String v) {
      this.username = v;
    }

    public String getEmail() {
      return email;
    }

    public void setEmail(String v) {
      this.email = v;
    }

    public String getDisplayName() {
      return displayName;
    }

    public void setDisplayName(String v) {
      this.displayName = v;
    }

    public String getStatus() {
      return status;
    }

    public void setStatus(String v) {
      this.status = v;
    }
  }

  /** saas 密码登录响应的 availableTenants 行（tenantId/roleIds/status，无租户名）。 */
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class SaasAvailableTenant {
    @JsonProperty("tenantId")
    private String tenantId;

    @JsonProperty("roleIds")
    private List<String> roleIds;

    @JsonProperty("status")
    private String status;

    public String getTenantId() {
      return tenantId;
    }

    public void setTenantId(String v) {
      this.tenantId = v;
    }

    public List<String> getRoleIds() {
      return roleIds;
    }

    public void setRoleIds(List<String> v) {
      this.roleIds = v;
    }

    public String getStatus() {
      return status;
    }

    public void setStatus(String v) {
      this.status = v;
    }
  }
}
