package io.xr.lab.shared.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.constraints.*;
import java.util.*;
import java.util.Objects;
import org.springframework.lang.Nullable;

/** ReportNamesUnlinkObjectReportNameRequest */
@JsonTypeName("ReportNames_unlinkObjectReportName_request")
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    date = "2026-09-02T21:47:39.355598900+08:00[Asia/Shanghai]",
    comments = "Generator version: 7.24.0")
public class ReportNamesUnlinkObjectReportNameRequest {

  private String inspectionObjectCode;

  private String reportNameCode;

  public ReportNamesUnlinkObjectReportNameRequest() {
    super();
  }

  /** Constructor with only required parameters */
  public ReportNamesUnlinkObjectReportNameRequest(
      String inspectionObjectCode, String reportNameCode) {
    this.inspectionObjectCode = inspectionObjectCode;
    this.reportNameCode = reportNameCode;
  }

  public ReportNamesUnlinkObjectReportNameRequest inspectionObjectCode(
      String inspectionObjectCode) {
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

  public ReportNamesUnlinkObjectReportNameRequest reportNameCode(String reportNameCode) {
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ReportNamesUnlinkObjectReportNameRequest reportNamesUnlinkObjectReportNameRequest =
        (ReportNamesUnlinkObjectReportNameRequest) o;
    return Objects.equals(
            this.inspectionObjectCode,
            reportNamesUnlinkObjectReportNameRequest.inspectionObjectCode)
        && Objects.equals(
            this.reportNameCode, reportNamesUnlinkObjectReportNameRequest.reportNameCode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(inspectionObjectCode, reportNameCode);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ReportNamesUnlinkObjectReportNameRequest {\n");
    sb.append("    inspectionObjectCode: ")
        .append(toIndentedString(inspectionObjectCode))
        .append("\n");
    sb.append("    reportNameCode: ").append(toIndentedString(reportNameCode)).append("\n");
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
