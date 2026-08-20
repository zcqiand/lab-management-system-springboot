package io.xr.lab.shared.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.constraints.*;
import java.util.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.springframework.lang.Nullable;

/** CreateParamInterfaceRequest */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    date = "2026-08-20T13:31:51.674991500+08:00[Asia/Shanghai]",
    comments = "Generator version: 7.24.0")
public class CreateParamInterfaceRequest {

  private String code;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String name;

  private String componentPath;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String description;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable Boolean isOfficial;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable Integer sortOrder;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private Map<String, Object> config = new HashMap<>();

  public CreateParamInterfaceRequest() {
    super();
  }

  /** Constructor with only required parameters */
  public CreateParamInterfaceRequest(String code, String componentPath) {
    this.code = code;
    this.componentPath = componentPath;
  }

  public CreateParamInterfaceRequest code(String code) {
    this.code = code;
    return this;
  }

  /**
   * Get code
   *
   * @return code
   */
  @NotNull
  @Schema(name = "code", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("code")
  public String getCode() {
    return code;
  }

  @JsonProperty("code")
  public void setCode(String code) {
    this.code = code;
  }

  public CreateParamInterfaceRequest name(@Nullable String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   *
   * @return name
   */
  @Schema(name = "name", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("name")
  public @Nullable String getName() {
    return name;
  }

  @JsonProperty("name")
  public void setName(@Nullable String name) {
    this.name = name;
  }

  public CreateParamInterfaceRequest componentPath(String componentPath) {
    this.componentPath = componentPath;
    return this;
  }

  /**
   * Get componentPath
   *
   * @return componentPath
   */
  @NotNull
  @Schema(name = "componentPath", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("componentPath")
  public String getComponentPath() {
    return componentPath;
  }

  @JsonProperty("componentPath")
  public void setComponentPath(String componentPath) {
    this.componentPath = componentPath;
  }

  public CreateParamInterfaceRequest description(@Nullable String description) {
    this.description = description;
    return this;
  }

  /**
   * Get description
   *
   * @return description
   */
  @Schema(name = "description", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("description")
  public @Nullable String getDescription() {
    return description;
  }

  @JsonProperty("description")
  public void setDescription(@Nullable String description) {
    this.description = description;
  }

  public CreateParamInterfaceRequest isOfficial(@Nullable Boolean isOfficial) {
    this.isOfficial = isOfficial;
    return this;
  }

  /**
   * Get isOfficial
   *
   * @return isOfficial
   */
  @Schema(name = "isOfficial", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("isOfficial")
  public @Nullable Boolean getIsOfficial() {
    return isOfficial;
  }

  @JsonProperty("isOfficial")
  public void setIsOfficial(@Nullable Boolean isOfficial) {
    this.isOfficial = isOfficial;
  }

  public CreateParamInterfaceRequest sortOrder(@Nullable Integer sortOrder) {
    this.sortOrder = sortOrder;
    return this;
  }

  /**
   * Get sortOrder
   *
   * @return sortOrder
   */
  @Schema(name = "sortOrder", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("sortOrder")
  public @Nullable Integer getSortOrder() {
    return sortOrder;
  }

  @JsonProperty("sortOrder")
  public void setSortOrder(@Nullable Integer sortOrder) {
    this.sortOrder = sortOrder;
  }

  public CreateParamInterfaceRequest config(Map<String, Object> config) {
    this.config = config;
    return this;
  }

  public CreateParamInterfaceRequest putConfigItem(String key, Object configItem) {
    if (this.config == null) {
      this.config = new HashMap<>();
    }
    this.config.put(key, configItem);
    return this;
  }

  /**
   * Get config
   *
   * @return config
   */
  @Schema(name = "config", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("config")
  public Map<String, Object> getConfig() {
    return config;
  }

  @JsonProperty("config")
  public void setConfig(Map<String, Object> config) {
    this.config = config;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CreateParamInterfaceRequest createParamInterfaceRequest = (CreateParamInterfaceRequest) o;
    return Objects.equals(this.code, createParamInterfaceRequest.code)
        && Objects.equals(this.name, createParamInterfaceRequest.name)
        && Objects.equals(this.componentPath, createParamInterfaceRequest.componentPath)
        && Objects.equals(this.description, createParamInterfaceRequest.description)
        && Objects.equals(this.isOfficial, createParamInterfaceRequest.isOfficial)
        && Objects.equals(this.sortOrder, createParamInterfaceRequest.sortOrder)
        && Objects.equals(this.config, createParamInterfaceRequest.config);
  }

  @Override
  public int hashCode() {
    return Objects.hash(code, name, componentPath, description, isOfficial, sortOrder, config);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreateParamInterfaceRequest {\n");
    sb.append("    code: ").append(toIndentedString(code)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    componentPath: ").append(toIndentedString(componentPath)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    isOfficial: ").append(toIndentedString(isOfficial)).append("\n");
    sb.append("    sortOrder: ").append(toIndentedString(sortOrder)).append("\n");
    sb.append("    config: ").append(toIndentedString(config)).append("\n");
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
