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

/** ObjectParameterLink */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    date = "2026-09-04T01:01:08.136686300+08:00[Asia/Shanghai]",
    comments = "Generator version: 7.24.0")
public class ObjectParameterLink {

  private String inspectionObjectCode;

  private String inspectionParameterCode;

  private QualificationLevel qualificationLevel;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable Integer sourcePage;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String remark;

  public ObjectParameterLink() {
    super();
  }

  /** Constructor with only required parameters */
  public ObjectParameterLink(
      String inspectionObjectCode,
      String inspectionParameterCode,
      QualificationLevel qualificationLevel) {
    this.inspectionObjectCode = inspectionObjectCode;
    this.inspectionParameterCode = inspectionParameterCode;
    this.qualificationLevel = qualificationLevel;
  }

  public ObjectParameterLink inspectionObjectCode(String inspectionObjectCode) {
    this.inspectionObjectCode = inspectionObjectCode;
    return this;
  }

  /**
   * Get inspectionObjectCode
   *
   * @return inspectionObjectCode
   */
  @NotNull
  @Schema(name = "inspectionObjectCode", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("inspectionObjectCode")
  public String getInspectionObjectCode() {
    return inspectionObjectCode;
  }

  @JsonProperty("inspectionObjectCode")
  public void setInspectionObjectCode(String inspectionObjectCode) {
    this.inspectionObjectCode = inspectionObjectCode;
  }

  public ObjectParameterLink inspectionParameterCode(String inspectionParameterCode) {
    this.inspectionParameterCode = inspectionParameterCode;
    return this;
  }

  /**
   * Get inspectionParameterCode
   *
   * @return inspectionParameterCode
   */
  @NotNull
  @Schema(name = "inspectionParameterCode", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("inspectionParameterCode")
  public String getInspectionParameterCode() {
    return inspectionParameterCode;
  }

  @JsonProperty("inspectionParameterCode")
  public void setInspectionParameterCode(String inspectionParameterCode) {
    this.inspectionParameterCode = inspectionParameterCode;
  }

  public ObjectParameterLink qualificationLevel(QualificationLevel qualificationLevel) {
    this.qualificationLevel = qualificationLevel;
    return this;
  }

  /**
   * Get qualificationLevel
   *
   * @return qualificationLevel
   */
  @NotNull
  @Valid
  @Schema(name = "qualificationLevel", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("qualificationLevel")
  public QualificationLevel getQualificationLevel() {
    return qualificationLevel;
  }

  @JsonProperty("qualificationLevel")
  public void setQualificationLevel(QualificationLevel qualificationLevel) {
    this.qualificationLevel = qualificationLevel;
  }

  public ObjectParameterLink sourcePage(@Nullable Integer sourcePage) {
    this.sourcePage = sourcePage;
    return this;
  }

  /**
   * Get sourcePage
   *
   * @return sourcePage
   */
  @Schema(name = "sourcePage", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("sourcePage")
  public @Nullable Integer getSourcePage() {
    return sourcePage;
  }

  @JsonProperty("sourcePage")
  public void setSourcePage(@Nullable Integer sourcePage) {
    this.sourcePage = sourcePage;
  }

  public ObjectParameterLink remark(@Nullable String remark) {
    this.remark = remark;
    return this;
  }

  /**
   * Get remark
   *
   * @return remark
   */
  @Schema(name = "remark", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("remark")
  public @Nullable String getRemark() {
    return remark;
  }

  @JsonProperty("remark")
  public void setRemark(@Nullable String remark) {
    this.remark = remark;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ObjectParameterLink objectParameterLink = (ObjectParameterLink) o;
    return Objects.equals(this.inspectionObjectCode, objectParameterLink.inspectionObjectCode)
        && Objects.equals(this.inspectionParameterCode, objectParameterLink.inspectionParameterCode)
        && Objects.equals(this.qualificationLevel, objectParameterLink.qualificationLevel)
        && Objects.equals(this.sourcePage, objectParameterLink.sourcePage)
        && Objects.equals(this.remark, objectParameterLink.remark);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        inspectionObjectCode, inspectionParameterCode, qualificationLevel, sourcePage, remark);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ObjectParameterLink {\n");
    sb.append("    inspectionObjectCode: ")
        .append(toIndentedString(inspectionObjectCode))
        .append("\n");
    sb.append("    inspectionParameterCode: ")
        .append(toIndentedString(inspectionParameterCode))
        .append("\n");
    sb.append("    qualificationLevel: ").append(toIndentedString(qualificationLevel)).append("\n");
    sb.append("    sourcePage: ").append(toIndentedString(sourcePage)).append("\n");
    sb.append("    remark: ").append(toIndentedString(remark)).append("\n");
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
