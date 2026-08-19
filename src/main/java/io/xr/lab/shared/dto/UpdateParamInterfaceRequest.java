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

/** UpdateParamInterfaceRequest */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    date = "2026-08-19T17:37:44.319774200+08:00[Asia/Shanghai]",
    comments = "Generator version: 7.24.0")
public class UpdateParamInterfaceRequest {

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String name;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String componentPath;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String description;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable Boolean isOfficial;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable Integer sortOrder;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private Map<String, Object> config = new HashMap<>();

  public UpdateParamInterfaceRequest name(@Nullable String name) {
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

  public UpdateParamInterfaceRequest componentPath(@Nullable String componentPath) {
    this.componentPath = componentPath;
    return this;
  }

  /**
   * Get componentPath
   *
   * @return componentPath
   */
  @Schema(name = "componentPath", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("componentPath")
  public @Nullable String getComponentPath() {
    return componentPath;
  }

  @JsonProperty("componentPath")
  public void setComponentPath(@Nullable String componentPath) {
    this.componentPath = componentPath;
  }

  public UpdateParamInterfaceRequest description(@Nullable String description) {
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

  public UpdateParamInterfaceRequest isOfficial(@Nullable Boolean isOfficial) {
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

  public UpdateParamInterfaceRequest sortOrder(@Nullable Integer sortOrder) {
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

  public UpdateParamInterfaceRequest config(Map<String, Object> config) {
    this.config = config;
    return this;
  }

  public UpdateParamInterfaceRequest putConfigItem(String key, Object configItem) {
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
    UpdateParamInterfaceRequest updateParamInterfaceRequest = (UpdateParamInterfaceRequest) o;
    return Objects.equals(this.name, updateParamInterfaceRequest.name)
        && Objects.equals(this.componentPath, updateParamInterfaceRequest.componentPath)
        && Objects.equals(this.description, updateParamInterfaceRequest.description)
        && Objects.equals(this.isOfficial, updateParamInterfaceRequest.isOfficial)
        && Objects.equals(this.sortOrder, updateParamInterfaceRequest.sortOrder)
        && Objects.equals(this.config, updateParamInterfaceRequest.config);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, componentPath, description, isOfficial, sortOrder, config);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UpdateParamInterfaceRequest {\n");
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
