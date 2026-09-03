package io.xr.lab.shared.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.constraints.*;
import java.util.*;
import java.util.Objects;
import org.springframework.lang.Nullable;

/** DashboardStatsFunnelByStage */
@JsonTypeName("DashboardStats_funnelByStage")
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    date = "2026-09-04T01:01:08.136686300+08:00[Asia/Shanghai]",
    comments = "Generator version: 7.24.0")
public class DashboardStatsFunnelByStage {

  private Integer pendingCollect;

  private Integer received;

  private Integer testing;

  private Integer reporting;

  private Integer reviewing;

  private Integer issued;

  public DashboardStatsFunnelByStage() {
    super();
  }

  /** Constructor with only required parameters */
  public DashboardStatsFunnelByStage(
      Integer pendingCollect,
      Integer received,
      Integer testing,
      Integer reporting,
      Integer reviewing,
      Integer issued) {
    this.pendingCollect = pendingCollect;
    this.received = received;
    this.testing = testing;
    this.reporting = reporting;
    this.reviewing = reviewing;
    this.issued = issued;
  }

  public DashboardStatsFunnelByStage pendingCollect(Integer pendingCollect) {
    this.pendingCollect = pendingCollect;
    return this;
  }

  /**
   * Get pendingCollect
   *
   * @return pendingCollect
   */
  @NotNull
  @Schema(name = "pending_collect", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("pending_collect")
  public Integer getPendingCollect() {
    return pendingCollect;
  }

  @JsonProperty("pending_collect")
  public void setPendingCollect(Integer pendingCollect) {
    this.pendingCollect = pendingCollect;
  }

  public DashboardStatsFunnelByStage received(Integer received) {
    this.received = received;
    return this;
  }

  /**
   * Get received
   *
   * @return received
   */
  @NotNull
  @Schema(name = "received", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("received")
  public Integer getReceived() {
    return received;
  }

  @JsonProperty("received")
  public void setReceived(Integer received) {
    this.received = received;
  }

  public DashboardStatsFunnelByStage testing(Integer testing) {
    this.testing = testing;
    return this;
  }

  /**
   * Get testing
   *
   * @return testing
   */
  @NotNull
  @Schema(name = "testing", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("testing")
  public Integer getTesting() {
    return testing;
  }

  @JsonProperty("testing")
  public void setTesting(Integer testing) {
    this.testing = testing;
  }

  public DashboardStatsFunnelByStage reporting(Integer reporting) {
    this.reporting = reporting;
    return this;
  }

  /**
   * Get reporting
   *
   * @return reporting
   */
  @NotNull
  @Schema(name = "reporting", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("reporting")
  public Integer getReporting() {
    return reporting;
  }

  @JsonProperty("reporting")
  public void setReporting(Integer reporting) {
    this.reporting = reporting;
  }

  public DashboardStatsFunnelByStage reviewing(Integer reviewing) {
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

  public DashboardStatsFunnelByStage issued(Integer issued) {
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
    DashboardStatsFunnelByStage dashboardStatsFunnelByStage = (DashboardStatsFunnelByStage) o;
    return Objects.equals(this.pendingCollect, dashboardStatsFunnelByStage.pendingCollect)
        && Objects.equals(this.received, dashboardStatsFunnelByStage.received)
        && Objects.equals(this.testing, dashboardStatsFunnelByStage.testing)
        && Objects.equals(this.reporting, dashboardStatsFunnelByStage.reporting)
        && Objects.equals(this.reviewing, dashboardStatsFunnelByStage.reviewing)
        && Objects.equals(this.issued, dashboardStatsFunnelByStage.issued);
  }

  @Override
  public int hashCode() {
    return Objects.hash(pendingCollect, received, testing, reporting, reviewing, issued);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DashboardStatsFunnelByStage {\n");
    sb.append("    pendingCollect: ").append(toIndentedString(pendingCollect)).append("\n");
    sb.append("    received: ").append(toIndentedString(received)).append("\n");
    sb.append("    testing: ").append(toIndentedString(testing)).append("\n");
    sb.append("    reporting: ").append(toIndentedString(reporting)).append("\n");
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
