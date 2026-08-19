package io.xr.lab.shared.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.constraints.*;
import java.util.*;
import java.util.Objects;
import org.springframework.lang.Nullable;

/** DashboardStatsReportCountByStatus */
@JsonTypeName("DashboardStats_reportCountByStatus")
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    date = "2026-08-19T17:37:44.319774200+08:00[Asia/Shanghai]",
    comments = "Generator version: 7.24.0")
public class DashboardStatsReportCountByStatus {

  private Integer draft;

  private Integer reviewing;

  private Integer issued;

  public DashboardStatsReportCountByStatus() {
    super();
  }

  /** Constructor with only required parameters */
  public DashboardStatsReportCountByStatus(Integer draft, Integer reviewing, Integer issued) {
    this.draft = draft;
    this.reviewing = reviewing;
    this.issued = issued;
  }

  public DashboardStatsReportCountByStatus draft(Integer draft) {
    this.draft = draft;
    return this;
  }

  /**
   * Get draft
   *
   * @return draft
   */
  @NotNull
  @Schema(name = "draft", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("draft")
  public Integer getDraft() {
    return draft;
  }

  @JsonProperty("draft")
  public void setDraft(Integer draft) {
    this.draft = draft;
  }

  public DashboardStatsReportCountByStatus reviewing(Integer reviewing) {
    this.reviewing = reviewing;
    return this;
  }

  /**
   * Get reviewing
   *
   * @return reviewing
   */
  @NotNull
  @Schema(name = "reviewing", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("reviewing")
  public Integer getReviewing() {
    return reviewing;
  }

  @JsonProperty("reviewing")
  public void setReviewing(Integer reviewing) {
    this.reviewing = reviewing;
  }

  public DashboardStatsReportCountByStatus issued(Integer issued) {
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
    DashboardStatsReportCountByStatus dashboardStatsReportCountByStatus =
        (DashboardStatsReportCountByStatus) o;
    return Objects.equals(this.draft, dashboardStatsReportCountByStatus.draft)
        && Objects.equals(this.reviewing, dashboardStatsReportCountByStatus.reviewing)
        && Objects.equals(this.issued, dashboardStatsReportCountByStatus.issued);
  }

  @Override
  public int hashCode() {
    return Objects.hash(draft, reviewing, issued);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DashboardStatsReportCountByStatus {\n");
    sb.append("    draft: ").append(toIndentedString(draft)).append("\n");
    sb.append("    reviewing: ").append(toIndentedString(reviewing)).append("\n");
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
