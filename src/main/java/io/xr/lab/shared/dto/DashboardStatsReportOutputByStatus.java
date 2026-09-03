package io.xr.lab.shared.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.constraints.*;
import java.util.*;
import java.util.Objects;
import org.springframework.lang.Nullable;

/** DashboardStatsReportOutputByStatus */
@JsonTypeName("DashboardStats_reportOutputByStatus")
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    date = "2026-09-04T01:01:08.136686300+08:00[Asia/Shanghai]",
    comments = "Generator version: 7.24.0")
public class DashboardStatsReportOutputByStatus {

  private Integer generated;

  private Integer pending;

  private Integer issued;

  public DashboardStatsReportOutputByStatus() {
    super();
  }

  /** Constructor with only required parameters */
  public DashboardStatsReportOutputByStatus(Integer generated, Integer pending, Integer issued) {
    this.generated = generated;
    this.pending = pending;
    this.issued = issued;
  }

  public DashboardStatsReportOutputByStatus generated(Integer generated) {
    this.generated = generated;
    return this;
  }

  /**
   * Get generated
   *
   * @return generated
   */
  @NotNull
  @Schema(name = "generated", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("generated")
  public Integer getGenerated() {
    return generated;
  }

  @JsonProperty("generated")
  public void setGenerated(Integer generated) {
    this.generated = generated;
  }

  public DashboardStatsReportOutputByStatus pending(Integer pending) {
    this.pending = pending;
    return this;
  }

  /**
   * Get pending
   *
   * @return pending
   */
  @NotNull
  @Schema(name = "pending", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("pending")
  public Integer getPending() {
    return pending;
  }

  @JsonProperty("pending")
  public void setPending(Integer pending) {
    this.pending = pending;
  }

  public DashboardStatsReportOutputByStatus issued(Integer issued) {
    this.issued = issued;
    return this;
  }

  /**
   * Get issued
   *
   * @return issued
   */
  @NotNull
  @Schema(name = "issued", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("issued")
  public Integer getIssued() {
    return issued;
  }

  @JsonProperty("issued")
  public void setIssued(Integer issued) {
    this.issued = issued;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DashboardStatsReportOutputByStatus dashboardStatsReportOutputByStatus =
        (DashboardStatsReportOutputByStatus) o;
    return Objects.equals(this.generated, dashboardStatsReportOutputByStatus.generated)
        && Objects.equals(this.pending, dashboardStatsReportOutputByStatus.pending)
        && Objects.equals(this.issued, dashboardStatsReportOutputByStatus.issued);
  }

  @Override
  public int hashCode() {
    return Objects.hash(generated, pending, issued);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DashboardStatsReportOutputByStatus {\n");
    sb.append("    generated: ").append(toIndentedString(generated)).append("\n");
    sb.append("    pending: ").append(toIndentedString(pending)).append("\n");
    sb.append("    issued: ").append(toIndentedString(issued)).append("\n");
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
