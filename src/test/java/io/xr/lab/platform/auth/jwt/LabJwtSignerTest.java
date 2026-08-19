package io.xr.lab.platform.auth.jwt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.xr.harness.junit.Fn;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;

/**
 * LabJwtSigner 单测 — 真 HMAC HS256 签发/验证。
 *
 * <p>覆盖：
 *
 * <ul>
 *   <li>access token 签发 → NimbusJwtDecoder 解出 → claims 命中
 *   <li>refresh token 签发（typ=refresh, saas_refresh_token claim 内嵌）
 *   <li>篡改 payload 验签失败
 *   <li>缺 / 弱 LAB_JWT_SECRET 构造抛 {@link IllegalStateException}
 * </ul>
 */
class LabJwtSignerTest {

  private static final String SECRET =
      "test-lab-jwt-secret-test-lab-jwt-secret-test-lab-jwt-secret"; // ≥32B

  @Test
  @Fn({"M01.F05.I01", "M01.F05.I02", "M01.F05.I03", "M01.F05.I04"})
  void issue_accessToken_signedWithHS256_decodesSuccessfully() {
    LabJwtSigner signer = new LabJwtSigner(SECRET, "lab-test", 3600, 604800);
    JwtDecoder decoder = NimbusLabJwtDecoderFactory.build(signer);

    String token = signer.issue("USER-A", "TENANT-002");
    assertNotNull(token);
    String[] parts = token.split("\\.");
    assertEquals(3, parts.length, "JWT must have 3 dot-separated segments");
    assertEquals("HS256", decoder.decode(token).getHeaders().get("alg"));

    Map<String, Object> claims = decoder.decode(token).getClaims();
    assertEquals("USER-A", claims.get("sub"));
    assertEquals("TENANT-002", claims.get("tenant_id"));
    assertEquals("access", claims.get("typ"));
    assertEquals("lab-test", claims.get("iss"));
  }

  @Test
  @Fn({"M01.F05.I04"})
  void issueRefresh_embedsSaasRefreshToken() {
    LabJwtSigner signer = new LabJwtSigner(SECRET, "lab-test", 3600, 604800);
    JwtDecoder decoder = NimbusLabJwtDecoderFactory.build(signer);

    String token = signer.issueRefresh("USER-A", "saas-rt-xyz");
    Map<String, Object> claims = decoder.decode(token).getClaims();
    assertEquals("refresh", claims.get("typ"));
    assertEquals("saas-rt-xyz", claims.get("saas_refresh_token"));
    assertEquals("USER-A", claims.get("sub"));
  }

  @Test
  @Fn({"M01.F05.I01"})
  void tamperedPayload_signatureRejected() {
    LabJwtSigner signer = new LabJwtSigner(SECRET, "lab-test", 3600, 604800);
    JwtDecoder decoder = NimbusLabJwtDecoderFactory.build(signer);
    String token = signer.issue("USER-A", null);

    // 篡改 payload: 把最后 1 字符翻转
    String[] parts = token.split("\\.");
    char[] payloadBytes = parts[1].toCharArray();
    payloadBytes[0] = payloadBytes[0] == 'A' ? 'B' : 'A';
    String tampered = parts[0] + "." + new String(payloadBytes) + "." + parts[2];

    assertThrows(JwtException.class, () -> decoder.decode(tampered));
  }

  @Test
  void missingSecret_throwsAtConstruction() {
    IllegalStateException ex =
        assertThrows(
            IllegalStateException.class, () -> new LabJwtSigner(null, "lab-test", 3600, 604800));
    assertTrue(ex.getMessage().contains("LAB_JWT_SECRET"));
  }

  @Test
  void tooShortSecret_throwsAtConstruction() {
    assertThrows(
        IllegalStateException.class, () -> new LabJwtSigner("short", "lab-test", 3600, 604800));
  }
}
