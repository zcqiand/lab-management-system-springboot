package io.xr.lab.platform.auth.sso;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;

/**
 * SaasAuthClient — 真对接 saas-identity-platform 的 OAuth 2.0 端点。
 *
 * <p>覆盖 RFC 6749 §4.1.1 (authorize) + §4.1.3 / §6 (token)。失败映射到 {@link SaasAuthException}：
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
 * <p>本类不直接 @Component，由 {@link io.xr.lab.platform.config.SsoBeansConfig} 按 profile 选 real/noop 实现。
 *
 * <p>{@code @SuppressFBWarnings}:CT_CONSTRUCTOR_THROW — 构造期 fail-fast 故意抛 IllegalStateException(env
 * 缺失即拒); Spring 容器包装,半初始化的 bean 不会泄漏。
 */
@edu.umd.cs.findbugs.annotations.SuppressFBWarnings(
    value = "CT_CONSTRUCTOR_THROW",
    justification = "fail-fast 设计:env 缺失立即抛 IllegalStateException 阻断 bean 创建")
public class SaasAuthClient {

  private final RestClient http;
  private final String clientId;
  private final String clientSecret;
  private final String defaultTenantId;

  public SaasAuthClient(
      String saasBase, String clientId, String clientSecret, String defaultTenantId) {
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
    this.http = SaasHttp.build(saasBase);
    this.clientId = clientId;
    this.clientSecret = clientSecret;
    this.defaultTenantId = defaultTenantId;
  }

  /** 无参构造器（用于 Noop 子类继承,跳过 env 校验）。 */
  protected SaasAuthClient() {
    this.http = null;
    this.clientId = null;
    this.clientSecret = null;
    this.defaultTenantId = null;
  }

  /** OAuth 2.0 §4.1.1 — 申请一次性 authorization code。 */
  public AuthorizeCodeResponse authorize(String redirectUri, String scope, String state) {
    Map<String, String> form =
        Map.of(
            "clientId", clientId,
            "redirectUri", redirectUri,
            "responseType", "code",
            "scope", scope,
            "state", state,
            "tenantId", defaultTenantId);
    try {
      return http.post()
          .uri("/api/v1/oauth/authorize")
          .contentType(MediaType.APPLICATION_JSON)
          .body(form)
          .retrieve()
          .body(AuthorizeCodeResponse.class);
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
  public static class AuthorizeCodeResponse {
    @JsonProperty("code")
    private String code;

    @JsonProperty("state")
    private String state;

    public String getCode() {
      return code;
    }

    public void setCode(String code) {
      this.code = code;
    }

    public String getState() {
      return state;
    }

    public void setState(String state) {
      this.state = state;
    }
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
}
