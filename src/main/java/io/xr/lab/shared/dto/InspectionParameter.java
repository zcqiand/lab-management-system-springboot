package io.xr.lab.shared.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.lang.Nullable;

/** InspectionParameter */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    date = "2026-08-19T17:37:44.319774200+08:00[Asia/Shanghai]",
    comments = "Generator version: 7.24.0")
public class InspectionParameter {

  private String code;

  private String name;

  private String rawName;

  private String canonicalName;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String methodText;

  private List<String> aliases = new ArrayList<>();

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String unit;

  private InspectionParameterSourceType sourceType;

  private Integer sortOrder;

  private String createdAt;

  private String updatedAt;

  public InspectionParameter() {
    super();
  }

  /** Constructor with only required parameters */
  public InspectionParameter(
      String code,
      String name,
      String rawName,
      String canonicalName,
      List<String> aliases,
      InspectionParameterSourceType sourceType,
      Integer sortOrder,
      String createdAt,
      String updatedAt) {
    this.code = code;
    this.name = name;
    this.rawName = rawName;
    this.canonicalName = canonicalName;
    this.aliases = aliases;
    this.sourceType = sourceType;
    this.sortOrder = sortOrder;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public InspectionParameter code(String code) {
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

  public InspectionParameter name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   *
   * @return name
   */
  @NotNull
  @Schema(name = "name", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("name")
  public String getName() {
    return name;
  }

  @JsonProperty("name")
  public void setName(String name) {
    this.name = name;
  }

  public InspectionParameter rawName(String rawName) {
    this.rawName = rawName;
    return this;
  }

  /**
   * Get rawName
   *
   * @return rawName
   */
  @NotNull
  @Schema(name = "rawName", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("rawName")
  public String getRawName() {
    return rawName;
  }

  @JsonProperty("rawName")
  public void setRawName(String rawName) {
    this.rawName = rawName;
  }

  public InspectionParameter canonicalName(String canonicalName) {
    this.canonicalName = canonicalName;
    return this;
  }

  /**
   * Get canonicalName
   *
   * @return canonicalName
   */
  @NotNull
  @Schema(name = "canonicalName", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("canonicalName")
  public String getCanonicalName() {
    return canonicalName;
  }

  @JsonProperty("canonicalName")
  public void setCanonicalName(String canonicalName) {
    this.canonicalName = canonicalName;
  }

  public InspectionParameter methodText(@Nullable String methodText) {
    this.methodText = methodText;
    return this;
  }

  /**
   * Get methodText
   *
   * @return methodText
   */
  @Schema(name = "methodText", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("methodText")
  public @Nullable String getMethodText() {
    return methodText;
  }

  @JsonProperty("methodText")
  public void setMethodText(@Nullable String methodText) {
    this.methodText = methodText;
  }

  public InspectionParameter aliases(List<String> aliases) {
    this.aliases = aliases;
    return this;
  }

  public InspectionParameter addAliasesItem(String aliasesItem) {
    if (this.aliases == null) {
      this.aliases = new ArrayList<>();
    }
    this.aliases.add(aliasesItem);
    return this;
  }

  /**
   * Get aliases
   *
   * @return aliases
   */
  @NotNull
  @Schema(name = "aliases", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("aliases")
  public List<String> getAliases() {
    return aliases;
  }

  @JsonProperty("aliases")
  public void setAliases(List<String> aliases) {
    this.aliases = aliases;
  }

  public InspectionParameter unit(@Nullable String unit) {
    this.unit = unit;
    return this;
  }

  /**
   * Get unit
   *
   * @return unit
   */
  @Schema(name = "unit", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("unit")
  public @Nullable String getUnit() {
    return unit;
  }

  @JsonProperty("unit")
  public void setUnit(@Nullable String unit) {
    this.unit = unit;
  }

  public InspectionParameter sourceType(InspectionParameterSourceType sourceType) {
    this.sourceType = sourceType;
    return this;
  }

  /**
   * Get sourceType
   *
   * @return sourceType
   */
  @NotNull
  @Valid
  @Schema(name = "sourceType", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("sourceType")
  public InspectionParameterSourceType getSourceType() {
    return sourceType;
  }

  @JsonProperty("sourceType")
  public void setSourceType(InspectionParameterSourceType sourceType) {
    this.sourceType = sourceType;
  }

  public InspectionParameter sortOrder(Integer sortOrder) {
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

  public InspectionParameter createdAt(String createdAt) {
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

  public InspectionParameter updatedAt(String updatedAt) {
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
    InspectionParameter inspectionParameter = (InspectionParameter) o;
    return Objects.equals(this.code, inspectionParameter.code)
        && Objects.equals(this.name, inspectionParameter.name)
        && Objects.equals(this.rawName, inspectionParameter.rawName)
        && Objects.equals(this.canonicalName, inspectionParameter.canonicalName)
        && Objects.equals(this.methodText, inspectionParameter.methodText)
        && Objects.equals(this.aliases, inspectionParameter.aliases)
        && Objects.equals(this.unit, inspectionParameter.unit)
        && Objects.equals(this.sourceType, inspectionParameter.sourceType)
        && Objects.equals(this.sortOrder, inspectionParameter.sortOrder)
        && Objects.equals(this.createdAt, inspectionParameter.createdAt)
        && Objects.equals(this.updatedAt, inspectionParameter.updatedAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        code,
        name,
        rawName,
        canonicalName,
        methodText,
        aliases,
        unit,
        sourceType,
        sortOrder,
        createdAt,
        updatedAt);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InspectionParameter {\n");
    sb.append("    code: ").append(toIndentedString(code)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    rawName: ").append(toIndentedString(rawName)).append("\n");
    sb.append("    canonicalName: ").append(toIndentedString(canonicalName)).append("\n");
    sb.append("    methodText: ").append(toIndentedString(methodText)).append("\n");
    sb.append("    aliases: ").append(toIndentedString(aliases)).append("\n");
    sb.append("    unit: ").append(toIndentedString(unit)).append("\n");
    sb.append("    sourceType: ").append(toIndentedString(sourceType)).append("\n");
    sb.append("    sortOrder: ").append(toIndentedString(sortOrder)).append("\n");
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
