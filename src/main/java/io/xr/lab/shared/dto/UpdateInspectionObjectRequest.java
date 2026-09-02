package io.xr.lab.shared.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.constraints.*;
import java.util.*;
import java.util.Objects;
import org.springframework.lang.Nullable;

/** UpdateInspectionObjectRequest */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    date = "2026-09-02T22:35:42.457326500+08:00[Asia/Shanghai]",
    comments = "Generator version: 7.24.0")
public class UpdateInspectionObjectRequest {

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String inspectionSpecialtyCode;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String sourceProjectNo;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String sourceProjectName;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String name;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable Boolean isOptionalForQualification;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable Boolean isOfficial;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable Boolean enabled;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable Integer sortOrder;

  public UpdateInspectionObjectRequest inspectionSpecialtyCode(
      @Nullable String inspectionSpecialtyCode) {
    this.inspectionSpecialtyCode = inspectionSpecialtyCode;
    return this;
  }

  /**
   * Get inspectionSpecialtyCode
   *
   * @return inspectionSpecialtyCode
   */
  @Schema(name = "inspectionSpecialtyCode", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("inspectionSpecialtyCode")
  public @Nullable String getInspectionSpecialtyCode() {
    return inspectionSpecialtyCode;
  }

  @JsonProperty("inspectionSpecialtyCode")
  public void setInspectionSpecialtyCode(@Nullable String inspectionSpecialtyCode) {
    this.inspectionSpecialtyCode = inspectionSpecialtyCode;
  }

  public UpdateInspectionObjectRequest sourceProjectNo(@Nullable String sourceProjectNo) {
    this.sourceProjectNo = sourceProjectNo;
    return this;
  }

  /**
   * Get sourceProjectNo
   *
   * @return sourceProjectNo
   */
  @Schema(name = "sourceProjectNo", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("sourceProjectNo")
  public @Nullable String getSourceProjectNo() {
    return sourceProjectNo;
  }

  @JsonProperty("sourceProjectNo")
  public void setSourceProjectNo(@Nullable String sourceProjectNo) {
    this.sourceProjectNo = sourceProjectNo;
  }

  public UpdateInspectionObjectRequest sourceProjectName(@Nullable String sourceProjectName) {
    this.sourceProjectName = sourceProjectName;
    return this;
  }

  /**
   * Get sourceProjectName
   *
   * @return sourceProjectName
   */
  @Schema(name = "sourceProjectName", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("sourceProjectName")
  public @Nullable String getSourceProjectName() {
    return sourceProjectName;
  }

  @JsonProperty("sourceProjectName")
  public void setSourceProjectName(@Nullable String sourceProjectName) {
    this.sourceProjectName = sourceProjectName;
  }

  public UpdateInspectionObjectRequest name(@Nullable String name) {
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

  public UpdateInspectionObjectRequest isOptionalForQualification(
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

  public UpdateInspectionObjectRequest isOfficial(@Nullable Boolean isOfficial) {
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

  public UpdateInspectionObjectRequest enabled(@Nullable Boolean enabled) {
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

  public UpdateInspectionObjectRequest sortOrder(@Nullable Integer sortOrder) {
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
    UpdateInspectionObjectRequest updateInspectionObjectRequest = (UpdateInspectionObjectRequest) o;
    return Objects.equals(
            this.inspectionSpecialtyCode, updateInspectionObjectRequest.inspectionSpecialtyCode)
        && Objects.equals(this.sourceProjectNo, updateInspectionObjectRequest.sourceProjectNo)
        && Objects.equals(this.sourceProjectName, updateInspectionObjectRequest.sourceProjectName)
        && Objects.equals(this.name, updateInspectionObjectRequest.name)
        && Objects.equals(
            this.isOptionalForQualification,
            updateInspectionObjectRequest.isOptionalForQualification)
        && Objects.equals(this.isOfficial, updateInspectionObjectRequest.isOfficial)
        && Objects.equals(this.enabled, updateInspectionObjectRequest.enabled)
        && Objects.equals(this.sortOrder, updateInspectionObjectRequest.sortOrder);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
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
    sb.append("class UpdateInspectionObjectRequest {\n");
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
