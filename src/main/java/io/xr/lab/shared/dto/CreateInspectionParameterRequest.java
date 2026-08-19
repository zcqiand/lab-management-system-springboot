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

/** CreateInspectionParameterRequest */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    date = "2026-08-19T17:37:44.319774200+08:00[Asia/Shanghai]",
    comments = "Generator version: 7.24.0")
public class CreateInspectionParameterRequest {

  private String code;

  private String name;

  private String rawName;

  private String canonicalName;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String methodText;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private List<String> aliases = new ArrayList<>();

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String unit;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable InspectionParameterSourceType sourceType;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable Integer sortOrder;

  public CreateInspectionParameterRequest() {
    super();
  }

  /** Constructor with only required parameters */
  public CreateInspectionParameterRequest(
      String code, String name, String rawName, String canonicalName) {
    this.code = code;
    this.name = name;
    this.rawName = rawName;
    this.canonicalName = canonicalName;
  }

  public CreateInspectionParameterRequest code(String code) {
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

  public CreateInspectionParameterRequest name(String name) {
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

  public CreateInspectionParameterRequest rawName(String rawName) {
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

  public CreateInspectionParameterRequest canonicalName(String canonicalName) {
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

  public CreateInspectionParameterRequest methodText(@Nullable String methodText) {
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

  public CreateInspectionParameterRequest aliases(List<String> aliases) {
    this.aliases = aliases;
    return this;
  }

  public CreateInspectionParameterRequest addAliasesItem(String aliasesItem) {
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
  @Schema(name = "aliases", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("aliases")
  public List<String> getAliases() {
    return aliases;
  }

  @JsonProperty("aliases")
  public void setAliases(List<String> aliases) {
    this.aliases = aliases;
  }

  public CreateInspectionParameterRequest unit(@Nullable String unit) {
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

  public CreateInspectionParameterRequest sourceType(
      @Nullable InspectionParameterSourceType sourceType) {
    this.sourceType = sourceType;
    return this;
  }

  /**
   * Get sourceType
   *
   * @return sourceType
   */
  @Valid
  @Schema(name = "sourceType", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("sourceType")
  public @Nullable InspectionParameterSourceType getSourceType() {
    return sourceType;
  }

  @JsonProperty("sourceType")
  public void setSourceType(@Nullable InspectionParameterSourceType sourceType) {
    this.sourceType = sourceType;
  }

  public CreateInspectionParameterRequest sortOrder(@Nullable Integer sortOrder) {
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CreateInspectionParameterRequest createInspectionParameterRequest =
        (CreateInspectionParameterRequest) o;
    return Objects.equals(this.code, createInspectionParameterRequest.code)
        && Objects.equals(this.name, createInspectionParameterRequest.name)
        && Objects.equals(this.rawName, createInspectionParameterRequest.rawName)
        && Objects.equals(this.canonicalName, createInspectionParameterRequest.canonicalName)
        && Objects.equals(this.methodText, createInspectionParameterRequest.methodText)
        && Objects.equals(this.aliases, createInspectionParameterRequest.aliases)
        && Objects.equals(this.unit, createInspectionParameterRequest.unit)
        && Objects.equals(this.sourceType, createInspectionParameterRequest.sourceType)
        && Objects.equals(this.sortOrder, createInspectionParameterRequest.sortOrder);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        code, name, rawName, canonicalName, methodText, aliases, unit, sourceType, sortOrder);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreateInspectionParameterRequest {\n");
    sb.append("    code: ").append(toIndentedString(code)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    rawName: ").append(toIndentedString(rawName)).append("\n");
    sb.append("    canonicalName: ").append(toIndentedString(canonicalName)).append("\n");
    sb.append("    methodText: ").append(toIndentedString(methodText)).append("\n");
    sb.append("    aliases: ").append(toIndentedString(aliases)).append("\n");
    sb.append("    unit: ").append(toIndentedString(unit)).append("\n");
    sb.append("    sourceType: ").append(toIndentedString(sourceType)).append("\n");
    sb.append("    sortOrder: ").append(toIndentedString(sortOrder)).append("\n");
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
