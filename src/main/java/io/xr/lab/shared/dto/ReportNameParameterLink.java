package io.xr.lab.shared.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.constraints.*;
import java.util.*;
import java.util.Objects;
import org.springframework.lang.Nullable;

/** ReportNameParameterLink */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    date = "2026-08-20T13:31:51.674991500+08:00[Asia/Shanghai]",
    comments = "Generator version: 7.24.0")
public class ReportNameParameterLink {

  private String reportNameCode;

  private String inspectionParameterCode;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String remark;

  public ReportNameParameterLink() {
    super();
  }

  /** Constructor with only required parameters */
  public ReportNameParameterLink(String reportNameCode, String inspectionParameterCode) {
    this.reportNameCode = reportNameCode;
    this.inspectionParameterCode = inspectionParameterCode;
  }

  public ReportNameParameterLink reportNameCode(String reportNameCode) {
    this.reportNameCode = reportNameCode;
    return this;
  }

  /**
   * Get reportNameCode
   *
   * @return reportNameCode
   */
  @NotNull
  @Schema(name = "reportNameCode", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("reportNameCode")
  public String getReportNameCode() {
    return reportNameCode;
  }

  @JsonProperty("reportNameCode")
  public void setReportNameCode(String reportNameCode) {
    this.reportNameCode = reportNameCode;
  }

  public ReportNameParameterLink inspectionParameterCode(String inspectionParameterCode) {
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

  public ReportNameParameterLink remark(@Nullable String remark) {
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
    ReportNameParameterLink reportNameParameterLink = (ReportNameParameterLink) o;
    return Objects.equals(this.reportNameCode, reportNameParameterLink.reportNameCode)
        && Objects.equals(
            this.inspectionParameterCode, reportNameParameterLink.inspectionParameterCode)
        && Objects.equals(this.remark, reportNameParameterLink.remark);
  }

  @Override
  public int hashCode() {
    return Objects.hash(reportNameCode, inspectionParameterCode, remark);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ReportNameParameterLink {\n");
    sb.append("    reportNameCode: ").append(toIndentedString(reportNameCode)).append("\n");
    sb.append("    inspectionParameterCode: ")
        .append(toIndentedString(inspectionParameterCode))
        .append("\n");
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
