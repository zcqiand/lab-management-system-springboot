package io.xr.lab.platform.config;

import io.xr.lab.platform.auth.jwt.LabJwtSigner;
import io.xr.lab.platform.auth.jwt.NimbusLabJwtDecoderFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * B1 安全基座（对齐 saas-springboot SecurityConfig 惯例）。
 *
 * <p>放行匿名端点：login / refresh / sso/**。其余 /api/auth/**（me / menus / permissions / switch-tenant /
 * logout）需 Bearer。业务端点（B2 起）默认 authenticated。
 *
 * <p>JWT decoder 由 {@link NimbusLabJwtDecoderFactory} 构造（HS256 真签名验证,覆盖原 DevJwtDecoder 的 alg=none
 * 直通漏洞）。
 */
@Configuration
public class SecurityConfig {

  @Value(
      "${lab.cors.allowed-origins:http://localhost:5173,http://localhost:5174,http://localhost:3000}")
  private String allowedOriginsCsv;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf(csrf -> csrf.disable())
        .cors(cors -> cors.configurationSource(corsConfigurationSource()))
        .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(
            authz ->
                authz
                    .requestMatchers("/api/auth/login", "/api/auth/refresh", "/api/auth/sso/**")
                    .permitAll()
                    .anyRequest()
                    .authenticated())
        .oauth2ResourceServer(o -> o.jwt(jwt -> {}));
    return http.build();
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration config = new CorsConfiguration();
    for (String origin : allowedOriginsCsv.split(",")) {
      String trimmed = origin.trim();
      if (!trimmed.isEmpty()) {
        config.addAllowedOrigin(trimmed);
      }
    }
    config.addAllowedMethod("*");
    config.addAllowedHeader("*");
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", config);
    return source;
  }

  /** 真签 HS256 JWT decoder。 */
  @Bean
  public JwtDecoder jwtDecoder(LabJwtSigner signer) {
    return NimbusLabJwtDecoderFactory.build(signer);
  }
}
