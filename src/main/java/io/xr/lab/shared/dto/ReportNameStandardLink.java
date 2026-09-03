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

/** ReportNameStandardLink */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    date = "2026-09-04T01:01:08.136686300+08:00[Asia/Shanghai]",
    comments = "Generator version: 7.24.0")
public class ReportNameStandardLink {

  private String reportNameCode;

  private String inspectionStandardCode;

  private InspectionStandardRole role;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String remark;

  public ReportNameStandardLink() {
    super();
  }

  /** Constructor with only required parameters */
  public ReportNameStandardLink(
      String reportNameCode, String inspectionStandardCode, InspectionStandardRole role) {
    this.reportNameCode = reportNameCode;
    this.inspectionStandardCode = inspectionStandardCode;
    this.role = role;
  }

  public ReportNameStandardLink reportNameCode(String reportNameCode) {
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

  public ReportNameStandardLink inspectionStandardCode(String inspectionStandardCode) {
    this.inspectionStandardCode = inspectionStandardCode;
    return this;
  }

  /**
   * Get inspectionStandardCode
   *
   * @return inspectionStandardCode
   */
  @NotNull
  @Schema(name = "inspectionStandardCode", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("inspectionStandardCode")
  public String getInspectionStandardCode() {
    return inspectionStandardCode;
  }

  @JsonProperty("inspectionStandardCode")
  public void setInspectionStandardCode(String inspectionStandardCode) {
    this.inspectionStandardCode = inspectionStandardCode;
  }

  public ReportNameStandardLink role(InspectionStandardRole role) {
    this.role = role;
    return this;
  }

  /**
   * Get role
   *
   * @return role
   */
  @NotNull
  @Valid
  @Schema(name = "role", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("role")
  public InspectionStandardRole getRole() {
    return role;
  }

  @JsonProperty("role")
  public void setRole(InspectionStandardRole role) {
    this.role = role;
  }

  public ReportNameStandardLink remark(@Nullable String remark) {
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
    ReportNameStandardLink reportNameStandardLink = (ReportNameStandardLink) o;
    return Objects.equals(this.reportNameCode, reportNameStandardLink.reportNameCode)
        && Objects.equals(
            this.inspectionStandardCode, reportNameStandardLink.inspectionStandardCode)
        && Objects.equals(this.role, reportNameStandardLink.role)
        && Objects.equals(this.remark, reportNameStandardLink.remark);
  }

  @Override
  public int hashCode() {
    return Objects.hash(reportNameCode, inspectionStandardCode, role, remark);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ReportNameStandardLink {\n");
    sb.append("    reportNameCode: ").append(toIndentedString(reportNameCode)).append("\n");
    sb.append("    inspectionStandardCode: ")
        .append(toIndentedString(inspectionStandardCode))
        .append("\n");
    sb.append("    role: ").append(toIndentedString(role)).append("\n");
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
