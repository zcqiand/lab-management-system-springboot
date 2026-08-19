package io.xr.lab.platform.auth.jwt;

import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

/**
 * 构造 Nimbus JwtDecoder，HS256 算法 + 强制签名验证（覆盖原 DevJwtDecoder 的『alg=none 直通』漏洞）。 镜像 saas-springboot /
 * lab-shared 的 TokenDecoder 工厂。
 */
public final class NimbusLabJwtDecoderFactory {

  private NimbusLabJwtDecoderFactory() {}

  public static JwtDecoder build(LabJwtSigner signer) {
    return NimbusJwtDecoder.withSecretKey(signer.secretKey())
        .macAlgorithm(MacAlgorithm.HS256)
        .build();
  }
}
