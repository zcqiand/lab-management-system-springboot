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

/** ParamInterface */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    date = "2026-09-04T01:01:08.136686300+08:00[Asia/Shanghai]",
    comments = "Generator version: 7.24.0")
public class ParamInterface {

  private String code;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String name;

  private String componentPath;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String description;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable Boolean isOfficial;

  private Integer sortOrder;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private Map<String, Object> config = new HashMap<>();

  private String createdAt;

  private String updatedAt;

  public ParamInterface() {
    super();
  }

  /** Constructor with only required parameters */
  public ParamInterface(
      String code, String componentPath, Integer sortOrder, String createdAt, String updatedAt) {
    this.code = code;
    this.componentPath = componentPath;
    this.sortOrder = sortOrder;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public ParamInterface code(String code) {
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

  public ParamInterface name(@Nullable String name) {
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

  public ParamInterface componentPath(String componentPath) {
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

  public ParamInterface description(@Nullable String description) {
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

  public ParamInterface isOfficial(@Nullable Boolean isOfficial) {
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

  public ParamInterface sortOrder(Integer sortOrder) {
    this.sortOrder = sortOrder;
    return this;
  }

  /**
   * Get sortOrder
   *
   * @return sortOrder
   */
  @NotNull
  @Schema(name = "sortOrder", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("sortOrder")
  public Integer getSortOrder() {
    return sortOrder;
  }

  @JsonProperty("sortOrder")
  public void setSortOrder(Integer sortOrder) {
    this.sortOrder = sortOrder;
  }

  public ParamInterface config(Map<String, Object> config) {
    this.config = config;
    return this;
  }

  public ParamInterface putConfigItem(String key, Object configItem) {
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

  public ParamInterface createdAt(String createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  /**
   * Get createdAt
   *
   * @return createdAt
   */
  @NotNull
  @Schema(name = "createdAt", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("createdAt")
  public String getCreatedAt() {
    return createdAt;
  }

  @JsonProperty("createdAt")
  public void setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
  }

  public ParamInterface updatedAt(String updatedAt) {
    this.updatedAt = updatedAt;
    return this;
  }

  /**
   * Get updatedAt
   *
   * @return updatedAt
   */
  @NotNull
  @Schema(name = "updatedAt", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("updatedAt")
  public String getUpdatedAt() {
    return updatedAt;
  }

  @JsonProperty("updatedAt")
  public void setUpdatedAt(String updatedAt) {
    this.updatedAt = updatedAt;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ParamInterface paramInterface = (ParamInterface) o;
    return Objects.equals(this.code, paramInterface.code)
        && Objects.equals(this.name, paramInterface.name)
        && Objects.equals(this.componentPath, paramInterface.componentPath)
        && Objects.equals(this.description, paramInterface.description)
        && Objects.equals(this.isOfficial, paramInterface.isOfficial)
        && Objects.equals(this.sortOrder, paramInterface.sortOrder)
        && Objects.equals(this.config, paramInterface.config)
        && Objects.equals(this.createdAt, paramInterface.createdAt)
        && Objects.equals(this.updatedAt, paramInterface.updatedAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        code,
        name,
        componentPath,
        description,
        isOfficial,
        sortOrder,
        config,
        createdAt,
        updatedAt);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ParamInterface {\n");
    sb.append("    code: ").append(toIndentedString(code)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    componentPath: ").append(toIndentedString(componentPath)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    isOfficial: ").append(toIndentedString(isOfficial)).append("\n");
    sb.append("    sortOrder: ").append(toIndentedString(sortOrder)).append("\n");
    sb.append("    config: ").append(toIndentedString(config)).append("\n");
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
    sb.append("    updatedAt: ").append(toIndentedString(updatedAt)).append("\n");
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
