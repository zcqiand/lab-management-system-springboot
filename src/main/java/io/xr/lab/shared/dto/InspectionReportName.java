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

/** InspectionReportName */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    date = "2026-08-19T17:37:44.319774200+08:00[Asia/Shanghai]",
    comments = "Generator version: 7.24.0")
public class InspectionReportName {

  private String code;

  private String name;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String fullName;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String templatePath;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String summaryName;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private List<@Valid ExtFieldDef> extFields = new ArrayList<>();

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String description;

  private Integer sortOrder;

  private String createdAt;

  private String updatedAt;

  public InspectionReportName() {
    super();
  }

  /** Constructor with only required parameters */
  public InspectionReportName(
      String code, String name, Integer sortOrder, String createdAt, String updatedAt) {
    this.code = code;
    this.name = name;
    this.sortOrder = sortOrder;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public InspectionReportName code(String code) {
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

  public InspectionReportName name(String name) {
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

  public InspectionReportName fullName(@Nullable String fullName) {
    this.fullName = fullName;
    return this;
  }

  /**
   * Get fullName
   *
   * @return fullName
   */
  @Schema(name = "fullName", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("fullName")
  public @Nullable String getFullName() {
    return fullName;
  }

  @JsonProperty("fullName")
  public void setFullName(@Nullable String fullName) {
    this.fullName = fullName;
  }

  public InspectionReportName templatePath(@Nullable String templatePath) {
    this.templatePath = templatePath;
    return this;
  }

  /**
   * Get templatePath
   *
   * @return templatePath
   */
  @Schema(name = "templatePath", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("templatePath")
  public @Nullable String getTemplatePath() {
    return templatePath;
  }

  @JsonProperty("templatePath")
  public void setTemplatePath(@Nullable String templatePath) {
    this.templatePath = templatePath;
  }

  public InspectionReportName summaryName(@Nullable String summaryName) {
    this.summaryName = summaryName;
    return this;
  }

  /**
   * Get summaryName
   *
   * @return summaryName
   */
  @Schema(name = "summaryName", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("summaryName")
  public @Nullable String getSummaryName() {
    return summaryName;
  }

  @JsonProperty("summaryName")
  public void setSummaryName(@Nullable String summaryName) {
    this.summaryName = summaryName;
  }

  public InspectionReportName extFields(List<@Valid ExtFieldDef> extFields) {
    this.extFields = extFields;
    return this;
  }

  public InspectionReportName addExtFieldsItem(ExtFieldDef extFieldsItem) {
    if (this.extFields == null) {
      this.extFields = new ArrayList<>();
    }
    this.extFields.add(extFieldsItem);
    return this;
  }

  /**
   * Get extFields
   *
   * @return extFields
   */
  @Valid
  @Schema(name = "extFields", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("extFields")
  public List<@Valid ExtFieldDef> getExtFields() {
    return extFields;
  }

  @JsonProperty("extFields")
  public void setExtFields(List<@Valid ExtFieldDef> extFields) {
    this.extFields = extFields;
  }

  public InspectionReportName description(@Nullable String description) {
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

  public InspectionReportName sortOrder(Integer sortOrder) {
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

  public InspectionReportName createdAt(String createdAt) {
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

  public InspectionReportName updatedAt(String updatedAt) {
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
    InspectionReportName inspectionReportName = (InspectionReportName) o;
    return Objects.equals(this.code, inspectionReportName.code)
        && Objects.equals(this.name, inspectionReportName.name)
        && Objects.equals(this.fullName, inspectionReportName.fullName)
        && Objects.equals(this.templatePath, inspectionReportName.templatePath)
        && Objects.equals(this.summaryName, inspectionReportName.summaryName)
        && Objects.equals(this.extFields, inspectionReportName.extFields)
        && Objects.equals(this.description, inspectionReportName.description)
        && Objects.equals(this.sortOrder, inspectionReportName.sortOrder)
        && Objects.equals(this.createdAt, inspectionReportName.createdAt)
        && Objects.equals(this.updatedAt, inspectionReportName.updatedAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        code,
        name,
        fullName,
        templatePath,
        summaryName,
        extFields,
        description,
        sortOrder,
        createdAt,
        updatedAt);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InspectionReportName {\n");
    sb.append("    code: ").append(toIndentedString(code)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    fullName: ").append(toIndentedString(fullName)).append("\n");
    sb.append("    templatePath: ").append(toIndentedString(templatePath)).append("\n");
    sb.append("    summaryName: ").append(toIndentedString(summaryName)).append("\n");
    sb.append("    extFields: ").append(toIndentedString(extFields)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
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
