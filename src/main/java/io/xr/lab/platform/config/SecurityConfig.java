package io.xr.lab.platform.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
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
 * <p>dev JwtDecoder 信任 alg=none 自签 token（见 {@link io.xr.lab.platform.service.AuthService}
 * 的签发说明）；production 换 issuer-uri + NimbusJwtDecoder。
 */
@Configuration
public class SecurityConfig {

  /**
   * CORS 白名单：lab-react(:5173) / lab-vue(:5174) / lab-nextjs(:3000，与 saas 共用本机端口)。
   * LAB_CORS_ALLOWED_ORIGINS env 覆盖。
   */
  @Value(
      "${lab.cors.allowed-origins:http://localhost:5173,http://localhost:5174,http://localhost:3000}")
  private List<String> allowedOrigins;

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
    for (String origin : allowedOrigins) {
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

  /**
   * dev JwtDecoder：手动 parse base64url payload，只看结构 + exp（过期则顺延 1h，容忍前端缓存的 旧 dev token）。镜像
   * saas-springboot DevJwtDecoder；production 删除此 bean 改 issuer-uri。
   */
  @Bean
  public JwtDecoder jwtDecoder() {
    return new DevJwtDecoder();
  }

  static class DevJwtDecoder implements JwtDecoder {
    // 命名 static 常量，避免每次 decode 新建匿名 TypeReference 子类（SpotBugs
    // SIC_INNER_SHOULD_BE_STATIC_ANON）。
    private static final TypeReference<Map<String, Object>> MAP_TYPE = new TypeReference<>() {};

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public Jwt decode(String token) throws JwtException {
      try {
        String[] parts = token.split("\\.");
        if (parts.length != 3) {
          throw new JwtException("Malformed JWT: expected 3 segments, got " + parts.length);
        }
        Map<String, Object> headers =
            mapper.readValue(
                new String(Base64.getUrlDecoder().decode(parts[0]), StandardCharsets.UTF_8),
                MAP_TYPE);
        Map<String, Object> claims =
            mapper.readValue(
                new String(Base64.getUrlDecoder().decode(parts[1]), StandardCharsets.UTF_8),
                MAP_TYPE);
        Instant now = Instant.now();
        Instant tokenExp =
            claims.get("exp") instanceof Number n
                ? Instant.ofEpochSecond(n.longValue())
                : now.plusSeconds(3600);
        Instant effectiveExp = tokenExp.isBefore(now) ? now.plusSeconds(3600) : tokenExp;
        return new Jwt(token, now, effectiveExp, headers, claims);
      } catch (JwtException e) {
        throw e;
      } catch (Exception e) {
        throw new JwtException("Failed to decode dev JWT: " + e.getMessage(), e);
      }
    }
  }
}
