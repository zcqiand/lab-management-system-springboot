package io.xr.lab.shared.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.constraints.*;
import java.util.*;
import java.util.Objects;
import org.springframework.lang.Nullable;

/** CreateInspectionObjectRequest */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    date = "2026-08-19T17:37:44.319774200+08:00[Asia/Shanghai]",
    comments = "Generator version: 7.24.0")
public class CreateInspectionObjectRequest {

  private String code;

  private String inspectionSpecialtyCode;

  private String sourceProjectNo;

  private String sourceProjectName;

  private String name;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable Boolean isOptionalForQualification;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable Boolean isOfficial;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable Boolean enabled;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable Integer sortOrder;

  public CreateInspectionObjectRequest() {
    super();
  }

  /** Constructor with only required parameters */
  public CreateInspectionObjectRequest(
      String code,
      String inspectionSpecialtyCode,
      String sourceProjectNo,
      String sourceProjectName,
      String name) {
    this.code = code;
    this.inspectionSpecialtyCode = inspectionSpecialtyCode;
    this.sourceProjectNo = sourceProjectNo;
    this.sourceProjectName = sourceProjectName;
    this.name = name;
  }

  public CreateInspectionObjectRequest code(String code) {
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

  public CreateInspectionObjectRequest inspectionSpecialtyCode(String inspectionSpecialtyCode) {
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

  public CreateInspectionObjectRequest sourceProjectNo(String sourceProjectNo) {
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

  public CreateInspectionObjectRequest sourceProjectName(String sourceProjectName) {
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

  public CreateInspectionObjectRequest name(String name) {
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

  public CreateInspectionObjectRequest isOptionalForQualification(
      @Nullable Boolean isOptionalForQualification) {
    this.isOptionalForQualification = isOptionalForQualification;
    return this;
  }

  /**
   * Get isOptionalForQualification
   *
   * @return isOptionalForQualification
   */
  @Schema(name = "isOptionalForQualification", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("isOptionalForQualification")
  public @Nullable Boolean getIsOptionalForQualification() {
    return isOptionalForQualification;
  }

  @JsonProperty("isOptionalForQualification")
  public void setIsOptionalForQualification(@Nullable Boolean isOptionalForQualification) {
    this.isOptionalForQualification = isOptionalForQualification;
  }

  public CreateInspectionObjectRequest isOfficial(@Nullable Boolean isOfficial) {
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

  public CreateInspectionObjectRequest enabled(@Nullable Boolean enabled) {
    this.enabled = enabled;
    return this;
  }

  /**
   * Get enabled
   *
   * @return enabled
   */
  @Schema(name = "enabled", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("enabled")
  public @Nullable Boolean getEnabled() {
    return enabled;
  }

  @JsonProperty("enabled")
  public void setEnabled(@Nullable Boolean enabled) {
    this.enabled = enabled;
  }

  public CreateInspectionObjectRequest sortOrder(@Nullable Integer sortOrder) {
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
    CreateInspectionObjectRequest createInspectionObjectRequest = (CreateInspectionObjectRequest) o;
    return Objects.equals(this.code, createInspectionObjectRequest.code)
        && Objects.equals(
            this.inspectionSpecialtyCode, createInspectionObjectRequest.inspectionSpecialtyCode)
        && Objects.equals(this.sourceProjectNo, createInspectionObjectRequest.sourceProjectNo)
        && Objects.equals(this.sourceProjectName, createInspectionObjectRequest.sourceProjectName)
        && Objects.equals(this.name, createInspectionObjectRequest.name)
        && Objects.equals(
            this.isOptionalForQualification,
            createInspectionObjectRequest.isOptionalForQualification)
        && Objects.equals(this.isOfficial, createInspectionObjectRequest.isOfficial)
        && Objects.equals(this.enabled, createInspectionObjectRequest.enabled)
        && Objects.equals(this.sortOrder, createInspectionObjectRequest.sortOrder);
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
        sortOrder);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreateInspectionObjectRequest {\n");
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
