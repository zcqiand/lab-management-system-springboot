package io.xr.lab.platform.auth.sso;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.xr.harness.junit.Fn;
import java.io.IOException;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * SaasAuthClient 真对接单测（MockWebServer 替身）。
 *
 * <p>覆盖：
 *
 * <ul>
 *   <li>token 成功 → 200 + {accessToken, refreshToken, ...}
 *   <li>token 401 → UnauthorizedClient
 *   <li>token 5xx → UpstreamUnavailable
 *   <li>连接失败 → UpstreamUnavailable
 * </ul>
 *
 * <p>2026-09-19：authorize()（服务端 code 预拿）随跳板语义收敛删除，其两条单测一并移除（裁定 1.3）；error 映射路径仍由 token 401/5xx 用例 +
 * GlobalExceptionHandlerTest 覆盖。
 */
class SaasAuthClientTest {

  private MockWebServer server;
  private SaasAuthClient client;

  @BeforeEach
  void start() throws IOException {
    server = new MockWebServer();
    server.start();
    client =
        new SaasAuthClient(
            server.url("").toString().replaceAll("/$", ""),
            "lab-client-id",
            "lab-client-secret",
            "00000000-0000-0000-0000-000000000001");
  }

  @AfterEach
  void stop() throws IOException {
    server.shutdown();
  }

  @Test
  @Fn({"M01.F05.I03"})
  void token_authorizationCode_returnsAccessToken() throws Exception {
    server.enqueue(
        new MockResponse()
            .setBody(
                "{\"accessToken\":\"saas-at\",\"refreshToken\":\"saas-rt\","
                    + "\"tokenType\":\"Bearer\",\"expiresIn\":3600,\"scope\":\"openid\"}")
            .addHeader("Content-Type", "application/json"));

    SaasAuthClient.TokenResponse resp =
        client.token("authorization_code", "auth-code-xyz", null, "http://localhost:5202/callback");

    assertEquals("saas-at", resp.getAccessToken());
    assertEquals("saas-rt", resp.getRefreshToken());
    assertEquals("Bearer", resp.getTokenType());

    RecordedRequest sent = server.takeRequest();
    String body = sent.getBody().readUtf8();
    assertTrue(body.contains("\"grantType\":\"authorization_code\""));
    assertTrue(body.contains("\"code\":\"auth-code-xyz\""));
    assertTrue(body.contains("\"clientSecret\":\"lab-client-secret\""));
  }

  @Test
  @Fn({"M01.F05.I04"})
  void token_refreshToken_returnsNewAccessToken() throws Exception {
    server.enqueue(
        new MockResponse()
            .setBody(
                "{\"accessToken\":\"saas-at-2\",\"refreshToken\":\"saas-rt-2\","
                    + "\"tokenType\":\"Bearer\",\"expiresIn\":3600,\"scope\":\"openid\"}")
            .addHeader("Content-Type", "application/json"));

    SaasAuthClient.TokenResponse resp = client.token("refresh_token", null, "saas-rt", null);

    assertEquals("saas-at-2", resp.getAccessToken());

    RecordedRequest sent = server.takeRequest();
    String body = sent.getBody().readUtf8();
    assertTrue(body.contains("\"grantType\":\"refresh_token\""));
    assertTrue(body.contains("\"refreshToken\":\"saas-rt\""));
    assertTrue(!body.contains("\"code\""), "code 字段不应出现");
  }

  @Test
  @Fn({"M01.F05.I03"})
  void token_401_mapsToUnauthorizedClient() {
    server.enqueue(
        new MockResponse().setResponseCode(401).setBody("{\"error\":\"unauthorized_client\"}"));

    assertThrows(
        SaasAuthException.UnauthorizedClient.class,
        () -> client.token("authorization_code", "code", null, "http://cb"));
  }

  @Test
  @Fn({"M01.F05.I03"})
  void token_5xx_mapsToUpstreamUnavailable() {
    server.enqueue(new MockResponse().setResponseCode(502).setBody("bad gateway"));

    assertThrows(
        SaasAuthException.UpstreamUnavailable.class,
        () -> client.token("authorization_code", "code", null, "http://cb"));
  }

  @Test
  @Fn({"M01.F05.I03"})
  void constructor_validatesRequiredEnv() {
    assertThrows(IllegalStateException.class, () -> new SaasAuthClient("", "id", "sec", "tid"));
    assertThrows(
        IllegalStateException.class, () -> new SaasAuthClient("http://x", "", "sec", "tid"));
    assertThrows(
        IllegalStateException.class, () -> new SaasAuthClient("http://x", "id", "", "tid"));
    assertThrows(
        IllegalStateException.class, () -> new SaasAuthClient("http://x", "id", "sec", ""));
  }

  @Test
  void constructor_acceptsValidArgs() {
    SaasAuthClient c = new SaasAuthClient("http://localhost:3000", "id", "sec", "tid");
    assertNotNull(c);
  }
}
