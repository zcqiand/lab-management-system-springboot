package io.xr.lab.shared.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.*;
import java.util.Objects;
import org.springframework.lang.Nullable;

/** 已废弃 (ADR-0014):用 VITE_API_BASE_URL / NEXT_PUBLIC_API_BASE_URL 替代;4-backend 运行时切换配置 */
@Schema(
    name = "BackendConfig",
    description =
        "已废弃 (ADR-0014):用 VITE_API_BASE_URL / NEXT_PUBLIC_API_BASE_URL 替代;4-backend 运行时切换配置")
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    date = "2026-08-26T12:43:04.549030500+08:00[Asia/Shanghai]",
    comments = "Generator version: 7.24.0")
public class BackendConfig {

  private BackendId id;

  private String label;

  private String baseUrl;

  private AuthHeaderKind authHeader;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String ssoCallbackPath;

  private BackendFeatures features;

  public BackendConfig() {
    super();
  }

  /** Constructor with only required parameters */
  public BackendConfig(
      BackendId id,
      String label,
      String baseUrl,
      AuthHeaderKind authHeader,
      BackendFeatures features) {
    this.id = id;
    this.label = label;
    this.baseUrl = baseUrl;
    this.authHeader = authHeader;
    this.features = features;
  }

  public BackendConfig id(BackendId id) {
    this.id = id;
    return this;
  }

  /**
   * 槽位标识,必须是 BackendId 之一
   *
   * @return id
   */
  @NotNull
  @Valid
  @Schema(
      name = "id",
      description = "槽位标识,必须是 BackendId 之一",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("id")
  public BackendId getId() {
    return id;
  }

  @JsonProperty("id")
  public void setId(BackendId id) {
    this.id = id;
  }

  public BackendConfig label(String label) {
    this.label = label;
    return this;
  }

  /**
   * 显示名(MSW Mock / Next.js API / Spring Boot / ASP.NET Core)
   *
   * @return label
   */
  @NotNull
  @Schema(
      name = "label",
      description = "显示名(MSW Mock / Next.js API / Spring Boot / ASP.NET Core)",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("label")
  public String getLabel() {
    return label;
  }

  @JsonProperty("label")
  public void setLabel(String label) {
    this.label = label;
  }

  public BackendConfig baseUrl(String baseUrl) {
    this.baseUrl = baseUrl;
    return this;
  }

  /**
   * baseUrl 的展示值,例如 'http://localhost:3000/api'
   *
   * @return baseUrl
   */
  @NotNull
  @Schema(
      name = "baseUrl",
      description = "baseUrl 的展示值,例如 'http://localhost:3000/api'",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("baseUrl")
  public String getBaseUrl() {
    return baseUrl;
  }

  @JsonProperty("baseUrl")
  public void setBaseUrl(String baseUrl) {
    this.baseUrl = baseUrl;
  }

  public BackendConfig authHeader(AuthHeaderKind authHeader) {
    this.authHeader = authHeader;
    return this;
  }

  /**
   * token 头:Bearer 走 Authorization,部分老后端用 X-Auth-Token
   *
   * @return authHeader
   */
  @NotNull
  @Valid
  @Schema(
      name = "authHeader",
      description = "token 头:Bearer 走 Authorization,部分老后端用 X-Auth-Token",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("authHeader")
  public AuthHeaderKind getAuthHeader() {
    return authHeader;
  }

  @JsonProperty("authHeader")
  public void setAuthHeader(AuthHeaderKind authHeader) {
    this.authHeader = authHeader;
  }

  public BackendConfig ssoCallbackPath(@Nullable String ssoCallbackPath) {
    this.ssoCallbackPath = ssoCallbackPath;
    return this;
  }

  /**
   * SSO 回调路径(仅启用 SSO 的后端填写)
   *
   * @return ssoCallbackPath
   */
  @Schema(
      name = "ssoCallbackPath",
      description = "SSO 回调路径(仅启用 SSO 的后端填写)",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("ssoCallbackPath")
  public @Nullable String getSsoCallbackPath() {
    return ssoCallbackPath;
  }

  @JsonProperty("ssoCallbackPath")
  public void setSsoCallbackPath(@Nullable String ssoCallbackPath) {
    this.ssoCallbackPath = ssoCallbackPath;
  }

  public BackendConfig features(BackendFeatures features) {
    this.features = features;
    return this;
  }

  /**
   * 能力矩阵
   *
   * @return features
   */
  @NotNull
  @Valid
  @Schema(name = "features", description = "能力矩阵", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("features")
  public BackendFeatures getFeatures() {
    return features;
  }

  @JsonProperty("features")
  public void setFeatures(BackendFeatures features) {
    this.features = features;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BackendConfig backendConfig = (BackendConfig) o;
    return Objects.equals(this.id, backendConfig.id)
        && Objects.equals(this.label, backendConfig.label)
        && Objects.equals(this.baseUrl, backendConfig.baseUrl)
        && Objects.equals(this.authHeader, backendConfig.authHeader)
        && Objects.equals(this.ssoCallbackPath, backendConfig.ssoCallbackPath)
        && Objects.equals(this.features, backendConfig.features);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, label, baseUrl, authHeader, ssoCallbackPath, features);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BackendConfig {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    label: ").append(toIndentedString(label)).append("\n");
    sb.append("    baseUrl: ").append(toIndentedString(baseUrl)).append("\n");
    sb.append("    authHeader: ").append(toIndentedString(authHeader)).append("\n");
    sb.append("    ssoCallbackPath: ").append(toIndentedString(ssoCallbackPath)).append("\n");
    sb.append("    features: ").append(toIndentedString(features)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces (except the first line).
   */
  private String toIndentedString(@Nullable Object o) {
    return o == null ? "null" : o.toString().replace("\n", "\n    ");
  }
}
