package io.xr.lab.platform.auth.jwt;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * LabJwtSigner — 真 HMAC HS256 JWT 签发/验证（对齐 B1 真后端 OAuth 2.0 + JWT 方案）。
 *
 * <p>读取 LAB_JWT_SECRET（≥32 字节，缺/弱抛 {@link IllegalStateException} 阻断 bean 创建）。提供：
 *
 * <ul>
 *   <li>{@link #issue(String, String)} — access token（typ=access, 1h TTL, 支持 tenant_id claim）
 *   <li>{@link #issueRefresh(String, String)} — refresh token（typ=refresh, 7d TTL, 内嵌 saas refresh
 *       token）
 *   <li>{@link #verify(String)} — 同步 HMAC 验签 + iss 校验 + exp 校验
 *   <li>{@link #secretKey()} — 暴露 {@link SecretKey} 给 {@link NimbusLabJwtDecoderFactory}
 * </ul>
 *
 * <p>JWT 头 alg 字段固定 HS256（不允许 alg=none）。三段格式
 * base64url(header).base64url(payload).base64url(HMAC-SHA256)。 payload JSON 字段按字典序输出（{@link
 * TreeMap}），保证签发和后端 hand-rolled 期望一致。
 *
 * <p>{@code @SuppressFBWarnings}:
 *
 * <ul>
 *   <li>CT_CONSTRUCTOR_THROW — 构造期 fail-fast 故意抛 IllegalStateException(env 缺失即拒); Spring 容器包装,半初始化的
 *       bean 不会泄漏
 *   <li>EI_EXPOSE_REP — secretKey() 必须返回原 SecretKey 供 NimbusJwtDecoder.withSecretKey 复用; SecretKey
 *       本身在 JDK 内部 immutable,暴露安全
 * </ul>
 */
@Component
@edu.umd.cs.findbugs.annotations.SuppressFBWarnings(
    value = {"CT_CONSTRUCTOR_THROW", "EI_EXPOSE_REP"},
    justification = "CT_CONSTRUCTOR_THROW 是 fail-fast 设计(env 校验);EI_EXPOSE_REP SecretKey immutable")
public class LabJwtSigner {

  private static final String ALG = "HmacSHA256";
  private static final String TYP_ACCESS = "access";
  private static final String TYP_REFRESH = "refresh";
  private static final String HEADER_JSON = "{\"alg\":\"HS256\",\"typ\":\"JWT\"}";
  private static final TypeReference<Map<String, Object>> MAP_TYPE = new TypeReference<>() {};

  private final SecretKey secretKey;
  private final String issuer;
  private final long accessTtlSeconds;
  private final long refreshTtlSeconds;
  private final ObjectMapper mapper = new ObjectMapper();

  public LabJwtSigner(
      @Value("${lab.jwt.secret}") String secret,
      @Value("${lab.jwt.issuer}") String issuer,
      @Value("${lab.jwt.ttl-seconds}") long accessTtlSeconds,
      @Value("${lab.jwt.refresh-ttl-seconds}") long refreshTtlSeconds) {
    if (secret == null || secret.isEmpty()) {
      throw new IllegalStateException(
          "LAB_JWT_SECRET required (>=32 bytes). Set via env var or lab.jwt.secret property.");
    }
    if (secret.getBytes(StandardCharsets.UTF_8).length < 32) {
      throw new IllegalStateException(
          "LAB_JWT_SECRET must be >=32 bytes (got "
              + secret.getBytes(StandardCharsets.UTF_8).length
              + "). Use openssl rand -base64 48.");
    }
    if (issuer == null || issuer.isEmpty()) {
      throw new IllegalStateException("lab.jwt.issuer required");
    }
    this.secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), ALG);
    this.issuer = issuer;
    this.accessTtlSeconds = accessTtlSeconds;
    this.refreshTtlSeconds = refreshTtlSeconds;
  }

  /** 公开 4-arg 构造器：测试代码直接 new;生产由 Spring 走 @Value 注入同一构造器。 */
  // 构造函数在类顶部已声明。

  /** 签发 access token。{@code tenantId} 可空，无选租户时不带 claim。 */
  public String issue(String userId, String tenantId) {
    long now = Instant.now().getEpochSecond();
    Map<String, Object> claims = new HashMap<>();
    claims.put("sub", userId);
    if (tenantId != null && !tenantId.isEmpty()) {
      claims.put("tenant_id", tenantId);
    }
    claims.put("iat", now);
    claims.put("exp", now + accessTtlSeconds);
    claims.put("typ", TYP_ACCESS);
    claims.put("iss", issuer);
    return sign(claims);
  }

  /** 签发 refresh token，载荷内嵌 saas refresh token（lab 自家 stateless 后端不持 saas 状态）。 */
  public String issueRefresh(String userId, String saasRefreshToken) {
    if (saasRefreshToken == null || saasRefreshToken.isEmpty()) {
      throw new IllegalArgumentException("saasRefreshToken required for refresh token");
    }
    long now = Instant.now().getEpochSecond();
    Map<String, Object> claims = new HashMap<>();
    claims.put("sub", userId);
    claims.put("saas_refresh_token", saasRefreshToken);
    claims.put("iat", now);
    claims.put("exp", now + refreshTtlSeconds);
    claims.put("typ", TYP_REFRESH);
    claims.put("iss", issuer);
    return sign(claims);
  }

  /** 同步 HMAC 验签 + iss 校验 + exp 校验。失败抛 {@link IllegalArgumentException}。 */
  public Map<String, Object> verify(String token) {
    if (token == null || token.isEmpty()) {
      throw new IllegalArgumentException("token is empty");
    }
    String[] parts = token.split("\\.");
    if (parts.length != 3) {
      throw new IllegalArgumentException("malformed JWT: expected 3 segments, got " + parts.length);
    }
    String signingInput = parts[0] + "." + parts[1];
    String expectedSig = hmacBase64Url(signingInput);
    if (!constantTimeEquals(expectedSig, parts[2])) {
      throw new IllegalArgumentException("bad signature");
    }
    Map<String, Object> claims;
    try {
      claims = mapper.readValue(Base64.getUrlDecoder().decode(parts[1]), MAP_TYPE);
    } catch (Exception e) {
      throw new IllegalArgumentException("invalid payload: " + e.getMessage(), e);
    }
    Object claimIss = claims.get("iss");
    if (claimIss == null || !issuer.equals(claimIss.toString())) {
      throw new IllegalArgumentException("bad issuer: " + claimIss);
    }
    Object exp = claims.get("exp");
    if (exp instanceof Number n) {
      long expSec = n.longValue();
      if (expSec < Instant.now().getEpochSecond()) {
        throw new IllegalArgumentException("token expired");
      }
    }
    return claims;
  }

  public SecretKey secretKey() {
    return secretKey;
  }

  // === 内部 helpers ===

  private String sign(Map<String, Object> claims) {
    String header = b64url(HEADER_JSON);
    String payload;
    try {
      payload = b64url(mapper.writeValueAsString(new TreeMap<>(claims)));
    } catch (Exception e) {
      throw new IllegalStateException("encode payload failed", e);
    }
    String signingInput = header + "." + payload;
    return signingInput + "." + hmacBase64Url(signingInput);
  }

  private String hmacBase64Url(String input) {
    try {
      javax.crypto.Mac mac = javax.crypto.Mac.getInstance(ALG);
      mac.init(secretKey);
      byte[] sig = mac.doFinal(input.getBytes(StandardCharsets.UTF_8));
      return Base64.getUrlEncoder().withoutPadding().encodeToString(sig);
    } catch (java.security.GeneralSecurityException e) {
      throw new IllegalStateException("HMAC failure", e);
    }
  }

  private static String b64url(String s) {
    return Base64.getUrlEncoder()
        .withoutPadding()
        .encodeToString(s.getBytes(StandardCharsets.UTF_8));
  }

  private static boolean constantTimeEquals(String a, String b) {
    if (a.length() != b.length()) return false;
    int diff = 0;
    for (int i = 0; i < a.length(); i++) {
      diff |= a.charAt(i) ^ b.charAt(i);
    }
    return diff == 0;
  }
}
