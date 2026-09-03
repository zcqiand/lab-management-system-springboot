package io.xr.lab.shared.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.constraints.*;
import java.util.*;
import java.util.Objects;
import org.springframework.lang.Nullable;

/** InspectionObject */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    date = "2026-09-04T01:01:08.136686300+08:00[Asia/Shanghai]",
    comments = "Generator version: 7.24.0")
public class InspectionObject {

  private String code;

  private String inspectionSpecialtyCode;

  private String sourceProjectNo;

  private String sourceProjectName;

  private String name;

  private Boolean isOptionalForQualification;

  private Boolean isOfficial;

  private Boolean enabled;

  private Integer sortOrder;

  private String createdAt;

  private String updatedAt;

  public InspectionObject() {
    super();
  }

  /** Constructor with only required parameters */
  public InspectionObject(
      String code,
      String inspectionSpecialtyCode,
      String sourceProjectNo,
      String sourceProjectName,
      String name,
      Boolean isOptionalForQualification,
      Boolean isOfficial,
      Boolean enabled,
      Integer sortOrder,
      String createdAt,
      String updatedAt) {
    this.code = code;
    this.inspectionSpecialtyCode = inspectionSpecialtyCode;
    this.sourceProjectNo = sourceProjectNo;
    this.sourceProjectName = sourceProjectName;
    this.name = name;
    this.isOptionalForQualification = isOptionalForQualification;
    this.isOfficial = isOfficial;
    this.enabled = enabled;
    this.sortOrder = sortOrder;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public InspectionObject code(String code) {
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

  public InspectionObject inspectionSpecialtyCode(String inspectionSpecialtyCode) {
    this.inspectionSpecialtyCode = inspectionSpecialtyCode;
    return this;
  }

  /**
   * Get inspectionSpecialtyCode
   *
   * @return inspectionSpecialtyCode
   */
  @NotNull
  @Schema(name = "inspectionSpecialtyCode", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("inspectionSpecialtyCode")
  public String getInspectionSpecialtyCode() {
    return inspectionSpecialtyCode;
  }

  @JsonProperty("inspectionSpecialtyCode")
  public void setInspectionSpecialtyCode(String inspectionSpecialtyCode) {
    this.inspectionSpecialtyCode = inspectionSpecialtyCode;
  }

  public InspectionObject sourceProjectNo(String sourceProjectNo) {
    this.sourceProjectNo = sourceProjectNo;
    return this;
  }

  /**
   * Get sourceProjectNo
   *
   * @return sourceProjectNo
   */
  @NotNull
  @Schema(name = "sourceProjectNo", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("sourceProjectNo")
  public String getSourceProjectNo() {
    return sourceProjectNo;
  }

  @JsonProperty("sourceProjectNo")
  public void setSourceProjectNo(String sourceProjectNo) {
    this.sourceProjectNo = sourceProjectNo;
  }

  public InspectionObject sourceProjectName(String sourceProjectName) {
    this.sourceProjectName = sourceProjectName;
    return this;
  }

  /**
   * Get sourceProjectName
   *
   * @return sourceProjectName
   */
  @NotNull
  @Schema(name = "sourceProjectName", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("sourceProjectName")
  public String getSourceProjectName() {
    return sourceProjectName;
  }

  @JsonProperty("sourceProjectName")
  public void setSourceProjectName(String sourceProjectName) {
    this.sourceProjectName = sourceProjectName;
  }

  public InspectionObject name(String name) {
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

  public InspectionObject isOptionalForQualification(Boolean isOptionalForQualification) {
    this.isOptionalForQualification = isOptionalForQualification;
    return this;
  }

  /**
   * Get isOptionalForQualification
   *
   * @return isOptionalForQualification
   */
  @NotNull
  @Schema(name = "isOptionalForQualification", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("isOptionalForQualification")
  public Boolean getIsOptionalForQualification() {
    return isOptionalForQualification;
  }

  @JsonProperty("isOptionalForQualification")
  public void setIsOptionalForQualification(Boolean isOptionalForQualification) {
    this.isOptionalForQualification = isOptionalForQualification;
  }

  public InspectionObject isOfficial(Boolean isOfficial) {
    this.isOfficial = isOfficial;
    return this;
  }

  /**
   * Get isOfficial
   *
   * @return isOfficial
   */
  @NotNull
  @Schema(name = "isOfficial", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("isOfficial")
  public Boolean getIsOfficial() {
    return isOfficial;
  }

  @JsonProperty("isOfficial")
  public void setIsOfficial(Boolean isOfficial) {
    this.isOfficial = isOfficial;
  }

  public InspectionObject enabled(Boolean enabled) {
    this.enabled = enabled;
    return this;
  }

  /**
   * Get enabled
   *
   * @return enabled
   */
  @NotNull
  @Schema(name = "enabled", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("enabled")
  public Boolean getEnabled() {
    return enabled;
  }

  @JsonProperty("enabled")
  public void setEnabled(Boolean enabled) {
    this.enabled = enabled;
  }

  public InspectionObject sortOrder(Integer sortOrder) {
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

  public InspectionObject createdAt(String createdAt) {
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

  public InspectionObject updatedAt(String updatedAt) {
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
    InspectionObject inspectionObject = (InspectionObject) o;
    return Objects.equals(this.code, inspectionObject.code)
        && Objects.equals(this.inspectionSpecialtyCode, inspectionObject.inspectionSpecialtyCode)
        && Objects.equals(this.sourceProjectNo, inspectionObject.sourceProjectNo)
        && Objects.equals(this.sourceProjectName, inspectionObject.sourceProjectName)
        && Objects.equals(this.name, inspectionObject.name)
        && Objects.equals(
            this.isOptionalForQualification, inspectionObject.isOptionalForQualification)
        && Objects.equals(this.isOfficial, inspectionObject.isOfficial)
        && Objects.equals(this.enabled, inspectionObject.enabled)
        && Objects.equals(this.sortOrder, inspectionObject.sortOrder)
        && Objects.equals(this.createdAt, inspectionObject.createdAt)
        && Objects.equals(this.updatedAt, inspectionObject.updatedAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        code,
        inspectionSpecialtyCode,
        sourceProjectNo,
        sourceProjectName,
        name,
        isOptionalForQualification,
        isOfficial,
        enabled,
        sortOrder,
        createdAt,
        updatedAt);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InspectionObject {\n");
    sb.append("    code: ").append(toIndentedString(code)).append("\n");
    sb.append("    inspectionSpecialtyCode: ")
        .append(toIndentedString(inspectionSpecialtyCode))
        .append("\n");
    sb.append("    sourceProjectNo: ").append(toIndentedString(sourceProjectNo)).append("\n");
    sb.append("    sourceProjectName: ").append(toIndentedString(sourceProjectName)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    isOptionalForQualification: ")
        .append(toIndentedString(isOptionalForQualification))
        .append("\n");
    sb.append("    isOfficial: ").append(toIndentedString(isOfficial)).append("\n");
    sb.append("    enabled: ").append(toIndentedString(enabled)).append("\n");
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
