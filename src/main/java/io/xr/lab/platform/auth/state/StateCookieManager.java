package io.xr.lab.platform.auth.state;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * StateCookieManager — OAuth 2.0 state 参数 CSRF 保护（RFC 6749 §10.12）。
 *
 * <p>流程：
 *
 * <ol>
 *   <li>authorize 时 {@link #issue(String)} 生成随机 nonce + 业务载荷({@code redirect, ts})+ HS256 签名
 *   <li>Controller 把"nonce.signature.payload"塞 HttpOnly Secure Cookie {@code lab_sso_state}
 *   <li>authorize 把 nonce 当作 state 写到 saas `?state=...` query
 *   <li>callback 时 {@link #verify(String, String)} 校验 body.state == cookie 的 nonce，且 cookie
 *       签名有效、5min 内
 * </ol>
 *
 * <p>cookie Set-Cookie 属性：HttpOnly; Secure（dev=false）; SameSite=Lax; Path=/api/auth/sso/callback;
 * Max-Age=300
 *
 * <p>签名密钥复用 {@code LAB_JWT_SECRET}（同进程只需要一个 HMAC 密钥；分仓验证会同时验 JWT sig + state sig）。
 *
 * <p>{@code @SuppressFBWarnings}:CT_CONSTRUCTOR_THROW — 构造期 fail-fast 故意抛 IllegalStateException(env
 * 缺失即拒); Spring 容器包装,半初始化的 bean 不会泄漏。两个构造器同语义。
 */
@Component
@edu.umd.cs.findbugs.annotations.SuppressFBWarnings(
    value = "CT_CONSTRUCTOR_THROW",
    justification = "fail-fast 设计:env 缺失立即抛 IllegalStateException 阻断 bean 创建")
public class StateCookieManager {

  public static final String COOKIE_NAME = "lab_sso_state";
  private static final String ALG = "HmacSHA256";
  private static final long MAX_AGE_SECONDS = 300;

  private final SecretKeySpec key;
  private final ObjectMapper mapper = new ObjectMapper();
  private final SecureRandom random = new SecureRandom();

  public StateCookieManager(@Value("${lab.jwt.secret}") String secret) {
    if (secret == null || secret.getBytes(StandardCharsets.UTF_8).length < 32) {
      throw new IllegalStateException("LAB_JWT_SECRET required for StateCookieManager (≥32 bytes)");
    }
    this.key = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), ALG);
  }

  /** 测试用：直接传 secret。 */
  public StateCookieManager(String secret, boolean unused) {
    if (secret == null || secret.getBytes(StandardCharsets.UTF_8).length < 32) {
      throw new IllegalStateException("LAB_JWT_SECRET required for StateCookieManager (≥32 bytes)");
    }
    this.key = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), ALG);
  }

  /** 签发 state。返回封包字符串（nonce.signature.payload），前端 select 出 nonce 当 state 用。 */
  public SignedState issue(String businessRedirect) {
    byte[] nonceBytes = new byte[16];
    random.nextBytes(nonceBytes);
    String nonce = Base64.getUrlEncoder().withoutPadding().encodeToString(nonceBytes);
    long ts = Instant.now().getEpochSecond();
    String payloadJson =
        "{\"nonce\":\""
            + nonce
            + "\",\"redirect\":\""
            + escape(businessRedirect)
            + "\",\"ts\":"
            + ts
            + "}";
    String payload = b64url(payloadJson);
    String signature = hmac(nonce + "." + payload);
    return new SignedState(nonce, nonce + "." + signature + "." + payload, ts);
  }

  /**
   * 校验 cookie 与 body.state 是否一致、签名有效、5min 内。
   *
   * @param cookieValue Set-Cookie 里的 lab_sso_state 完整值
   * @param bodyState 请求 body.state
   * @return 解出来的业务 redirect
   */
  public String verify(String cookieValue, String bodyState) {
    if (cookieValue == null || cookieValue.isEmpty()) {
      throw new IllegalStateException("missing lab_sso_state cookie");
    }
    if (bodyState == null || bodyState.isEmpty()) {
      throw new IllegalStateException("missing state in body");
    }
    String[] parts = cookieValue.split("\\.");
    if (parts.length != 3) {
      throw new IllegalStateException("malformed lab_sso_state cookie");
    }
    String nonce = parts[0];
    String signature = parts[1];
    String payload = parts[2];
    String expectedSig = hmac(nonce + "." + payload);
    if (!constantTimeEquals(expectedSig, signature)) {
      throw new IllegalStateException("lab_sso_state signature mismatch");
    }
    if (!nonce.equals(bodyState)) {
      throw new IllegalStateException("state nonce mismatch (CSRF suspected)");
    }
    StatePayload sp;
    try {
      String json = new String(Base64.getUrlDecoder().decode(payload), StandardCharsets.UTF_8);
      sp = mapper.readValue(json, StatePayload.class);
    } catch (java.io.IOException e) {
      throw new IllegalStateException("lab_sso_state payload unparseable: " + e.getMessage(), e);
    }
    long now = Instant.now().getEpochSecond();
    if (sp.ts == 0 || now - sp.ts > MAX_AGE_SECONDS) {
      throw new IllegalStateException("lab_sso_state expired");
    }
    return sp.redirect == null ? "" : sp.redirect;
  }

  private String hmac(String input) {
    try {
      Mac mac = Mac.getInstance(ALG);
      mac.init(key);
      byte[] sig = mac.doFinal(input.getBytes(StandardCharsets.UTF_8));
      return Base64.getUrlEncoder().withoutPadding().encodeToString(sig);
    } catch (java.security.NoSuchAlgorithmException | java.security.InvalidKeyException e) {
      throw new IllegalStateException("HMAC failure", e);
    }
  }

  private static String b64url(String s) {
    return Base64.getUrlEncoder()
        .withoutPadding()
        .encodeToString(s.getBytes(StandardCharsets.UTF_8));
  }

  private static String escape(String s) {
    if (s == null) return "";
    return s.replace("\\", "\\\\").replace("\"", "\\\"");
  }

  private static boolean constantTimeEquals(String a, String b) {
    if (a.length() != b.length()) return false;
    int diff = 0;
    for (int i = 0; i < a.length(); i++) {
      diff |= a.charAt(i) ^ b.charAt(i);
    }
    return diff == 0;
  }

  public static String cookieName() {
    return COOKIE_NAME;
  }

  public static int maxAgeSeconds() {
    return (int) MAX_AGE_SECONDS;
  }

  public static class SignedState {
    private final String nonce;
    private final String cookieValue;
    private final long ts;

    public SignedState(String nonce, String cookieValue, long ts) {
      this.nonce = nonce;
      this.cookieValue = cookieValue;
      this.ts = ts;
    }

    public String nonce() {
      return nonce;
    }

    public String cookieValue() {
      return cookieValue;
    }

    public long ts() {
      return ts;
    }
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class StatePayload {
    @JsonProperty("nonce")
    public String nonce;

    @JsonProperty("redirect")
    public String redirect;

    @JsonProperty("ts")
    public long ts;
  }
}
