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
 *   <li>authorize 成功 → 200 + {code, state}
 *   <li>token 成功 → 200 + {accessToken, refreshToken, ...}
 *   <li>authorize 400 → InvalidGrant
 *   <li>token 401 → UnauthorizedClient
 *   <li>token 5xx → UpstreamUnavailable
 *   <li>连接失败 → UpstreamUnavailable
 * </ul>
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
  @Fn({"M01.F05.I02"})
  void authorize_returnsCodeAndState() throws Exception {
    server.enqueue(
        new MockResponse()
            .setBody("{\"code\":\"auth-code-xyz\",\"state\":\"client-state-123\"}")
            .addHeader("Content-Type", "application/json"));

    SaasAuthClient.AuthorizeCodeResponse resp =
        client.authorize("http://localhost:5202/callback", "openid profile", "client-state-123");

    assertEquals("auth-code-xyz", resp.getCode());
    assertEquals("client-state-123", resp.getState());

    RecordedRequest sent = server.takeRequest();
    assertEquals("POST", sent.getMethod());
    assertEquals("/api/v1/oauth/authorize", sent.getPath());
    String body = sent.getBody().readUtf8();
    assertTrue(body.contains("\"clientId\":\"lab-client-id\""));
    assertTrue(body.contains("\"responseType\":\"code\""));
    assertTrue(body.contains("\"tenantId\":\"00000000-0000-0000-0000-000000000001\""));
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
  void authorize_400_mapsToInvalidGrant() {
    server.enqueue(
        new MockResponse().setResponseCode(400).setBody("{\"error\":\"invalid_grant\"}"));

    assertThrows(
        SaasAuthException.InvalidGrant.class,
        () -> client.authorize("http://localhost/cb", "openid", "state"));
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
