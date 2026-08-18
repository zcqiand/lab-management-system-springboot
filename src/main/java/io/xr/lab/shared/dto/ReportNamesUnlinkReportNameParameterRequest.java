package io.xr.lab.shared.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.constraints.*;
import java.util.*;
import java.util.Objects;
import org.springframework.lang.Nullable;

/** ReportNamesUnlinkReportNameParameterRequest */
@JsonTypeName("ReportNames_unlinkReportNameParameter_request")
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    date = "2026-08-18T09:31:54.550738400+08:00[Asia/Shanghai]",
    comments = "Generator version: 7.24.0")
public class ReportNamesUnlinkReportNameParameterRequest {

  private String reportNameCode;

  private String inspectionParameterCode;

  public ReportNamesUnlinkReportNameParameterRequest() {
    super();
  }

  /** Constructor with only required parameters */
  public ReportNamesUnlinkReportNameParameterRequest(
      String reportNameCode, String inspectionParameterCode) {
    this.reportNameCode = reportNameCode;
    this.inspectionParameterCode = inspectionParameterCode;
  }

  public ReportNamesUnlinkReportNameParameterRequest reportNameCode(String reportNameCode) {
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

  public ReportNamesUnlinkReportNameParameterRequest inspectionParameterCode(
      String inspectionParameterCode) {
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ReportNamesUnlinkReportNameParameterRequest reportNamesUnlinkReportNameParameterRequest =
        (ReportNamesUnlinkReportNameParameterRequest) o;
    return Objects.equals(
            this.reportNameCode, reportNamesUnlinkReportNameParameterRequest.reportNameCode)
        && Objects.equals(
            this.inspectionParameterCode,
            reportNamesUnlinkReportNameParameterRequest.inspectionParameterCode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(reportNameCode, inspectionParameterCode);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ReportNamesUnlinkReportNameParameterRequest {\n");
    sb.append("    reportNameCode: ").append(toIndentedString(reportNameCode)).append("\n");
    sb.append("    inspectionParameterCode: ")
        .append(toIndentedString(inspectionParameterCode))
        .append("\n");
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
