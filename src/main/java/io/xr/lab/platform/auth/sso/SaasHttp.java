package io.xr.lab.platform.auth.sso;

import java.net.http.HttpClient;
import org.springframework.http.MediaType;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

/**
 * SaasHttp — SaasAuthClient / SaasMeClient 共用的 RestClient 工厂。
 *
 * <p>为什么不用 {@code RestClient.builder().build()} 默认工厂：classpath 上无 Apache HttpClient / OkHttp
 * 时，Spring 回退 {@link JdkClientHttpRequestFactory}（JDK HttpClient）。JDK HttpClient 对 http:// 明文地址默认先发
 * h2c（HTTP/2 cleartext）升级请求，msw mock server 与部分网关不认， 直接断连 —— 表现为 {@code EOFException: EOF reached
 * while reading} / "header parser received no bytes"。强制 {@link HttpClient.Version#HTTP_1_1} 规避（saas
 * 端点是 JSON API，无 h2 收益）。
 */
final class SaasHttp {

  private SaasHttp() {}

  static RestClient build(String saasBase) {
    HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1).build();
    return RestClient.builder()
        .baseUrl(saasBase)
        .requestFactory(new JdkClientHttpRequestFactory(httpClient))
        .defaultHeader("Accept", MediaType.APPLICATION_JSON_VALUE)
        .build();
  }
}
