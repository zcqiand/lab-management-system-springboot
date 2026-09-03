package io.xr.lab.shared.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.*;
import java.util.Objects;
import org.springframework.lang.Nullable;

/** DashboardStats */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    date = "2026-09-04T01:01:08.136686300+08:00[Asia/Shanghai]",
    comments = "Generator version: 7.24.0")
public class DashboardStats {

  private Integer contractCount;

  private Integer receiptCount;

  private Integer sampleCount;

  private DashboardStatsReportCountByStatus reportCountByStatus;

  private Integer pendingTaskCount;

  private Integer todayTestCount;

  private DashboardStatsQualifiedRateByMaterial qualifiedRateByMaterial;

  private DashboardStatsReportOutputByStatus reportOutputByStatus;

  private DashboardStatsFunnelByStage funnelByStage;

  public DashboardStats() {
    super();
  }

  /** Constructor with only required parameters */
  public DashboardStats(
      Integer contractCount,
      Integer receiptCount,
      Integer sampleCount,
      DashboardStatsReportCountByStatus reportCountByStatus,
      Integer pendingTaskCount,
      Integer todayTestCount,
      DashboardStatsQualifiedRateByMaterial qualifiedRateByMaterial,
      DashboardStatsReportOutputByStatus reportOutputByStatus,
      DashboardStatsFunnelByStage funnelByStage) {
    this.contractCount = contractCount;
    this.receiptCount = receiptCount;
    this.sampleCount = sampleCount;
    this.reportCountByStatus = reportCountByStatus;
    this.pendingTaskCount = pendingTaskCount;
    this.todayTestCount = todayTestCount;
    this.qualifiedRateByMaterial = qualifiedRateByMaterial;
    this.reportOutputByStatus = reportOutputByStatus;
    this.funnelByStage = funnelByStage;
  }

  public DashboardStats contractCount(Integer contractCount) {
    this.contractCount = contractCount;
    return this;
  }

  /**
   * Get contractCount
   *
   * @return contractCount
   */
  @NotNull
  @Schema(name = "contractCount", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("contractCount")
  public Integer getContractCount() {
    return contractCount;
  }

  @JsonProperty("contractCount")
  public void setContractCount(Integer contractCount) {
    this.contractCount = contractCount;
  }

  public DashboardStats receiptCount(Integer receiptCount) {
    this.receiptCount = receiptCount;
    return this;
  }

  /**
   * Get receiptCount
   *
   * @return receiptCount
   */
  @NotNull
  @Schema(name = "receiptCount", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("receiptCount")
  public Integer getReceiptCount() {
    return receiptCount;
  }

  @JsonProperty("receiptCount")
  public void setReceiptCount(Integer receiptCount) {
    this.receiptCount = receiptCount;
  }

  public DashboardStats sampleCount(Integer sampleCount) {
    this.sampleCount = sampleCount;
    return this;
  }

  /**
   * Get sampleCount
   *
   * @return sampleCount
   */
  @NotNull
  @Schema(name = "sampleCount", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("sampleCount")
  public Integer getSampleCount() {
    return sampleCount;
  }

  @JsonProperty("sampleCount")
  public void setSampleCount(Integer sampleCount) {
    this.sampleCount = sampleCount;
  }

  public DashboardStats reportCountByStatus(DashboardStatsReportCountByStatus reportCountByStatus) {
    this.reportCountByStatus = reportCountByStatus;
    return this;
  }

  /**
   * Get reportCountByStatus
   *
   * @return reportCountByStatus
   */
  @NotNull
  @Valid
  @Schema(name = "reportCountByStatus", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("reportCountByStatus")
  public DashboardStatsReportCountByStatus getReportCountByStatus() {
    return reportCountByStatus;
  }

  @JsonProperty("reportCountByStatus")
  public void setReportCountByStatus(DashboardStatsReportCountByStatus reportCountByStatus) {
    this.reportCountByStatus = reportCountByStatus;
  }

  public DashboardStats pendingTaskCount(Integer pendingTaskCount) {
    this.pendingTaskCount = pendingTaskCount;
    return this;
  }

  /**
   * Get pendingTaskCount
   *
   * @return pendingTaskCount
   */
  @NotNull
  @Schema(name = "pendingTaskCount", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("pendingTaskCount")
  public Integer getPendingTaskCount() {
    return pendingTaskCount;
  }

  @JsonProperty("pendingTaskCount")
  public void setPendingTaskCount(Integer pendingTaskCount) {
    this.pendingTaskCount = pendingTaskCount;
  }

  public DashboardStats todayTestCount(Integer todayTestCount) {
    this.todayTestCount = todayTestCount;
    return this;
  }

  /**
   * Get todayTestCount
   *
   * @return todayTestCount
   */
  @NotNull
  @Schema(name = "todayTestCount", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("todayTestCount")
  public Integer getTodayTestCount() {
    return todayTestCount;
  }

  @JsonProperty("todayTestCount")
  public void setTodayTestCount(Integer todayTestCount) {
    this.todayTestCount = todayTestCount;
  }

  public DashboardStats qualifiedRateByMaterial(
      DashboardStatsQualifiedRateByMaterial qualifiedRateByMaterial) {
    this.qualifiedRateByMaterial = qualifiedRateByMaterial;
    return this;
  }

  /**
   * Get qualifiedRateByMaterial
   *
   * @return qualifiedRateByMaterial
   */
  @NotNull
  @Valid
  @Schema(name = "qualifiedRateByMaterial", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("qualifiedRateByMaterial")
  public DashboardStatsQualifiedRateByMaterial getQualifiedRateByMaterial() {
    return qualifiedRateByMaterial;
  }

  @JsonProperty("qualifiedRateByMaterial")
  public void setQualifiedRateByMaterial(
      DashboardStatsQualifiedRateByMaterial qualifiedRateByMaterial) {
    this.qualifiedRateByMaterial = qualifiedRateByMaterial;
  }

  public DashboardStats reportOutputByStatus(
      DashboardStatsReportOutputByStatus reportOutputByStatus) {
    this.reportOutputByStatus = reportOutputByStatus;
    return this;
  }

  /**
   * Get reportOutputByStatus
   *
   * @return reportOutputByStatus
   */
  @NotNull
  @Valid
  @Schema(name = "reportOutputByStatus", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("reportOutputByStatus")
  public DashboardStatsReportOutputByStatus getReportOutputByStatus() {
    return reportOutputByStatus;
  }

  @JsonProperty("reportOutputByStatus")
  public void setReportOutputByStatus(DashboardStatsReportOutputByStatus reportOutputByStatus) {
    this.reportOutputByStatus = reportOutputByStatus;
  }

  public DashboardStats funnelByStage(DashboardStatsFunnelByStage funnelByStage) {
    this.funnelByStage = funnelByStage;
    return this;
  }

  /**
   * Get funnelByStage
   *
   * @return funnelByStage
   */
  @NotNull
  @Valid
  @Schema(name = "funnelByStage", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("funnelByStage")
  public DashboardStatsFunnelByStage getFunnelByStage() {
    return funnelByStage;
  }

  @JsonProperty("funnelByStage")
  public void setFunnelByStage(DashboardStatsFunnelByStage funnelByStage) {
    this.funnelByStage = funnelByStage;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DashboardStats dashboardStats = (DashboardStats) o;
    return Objects.equals(this.contractCount, dashboardStats.contractCount)
        && Objects.equals(this.receiptCount, dashboardStats.receiptCount)
        && Objects.equals(this.sampleCount, dashboardStats.sampleCount)
        && Objects.equals(this.reportCountByStatus, dashboardStats.reportCountByStatus)
        && Objects.equals(this.pendingTaskCount, dashboardStats.pendingTaskCount)
        && Objects.equals(this.todayTestCount, dashboardStats.todayTestCount)
        && Objects.equals(this.qualifiedRateByMaterial, dashboardStats.qualifiedRateByMaterial)
        && Objects.equals(this.reportOutputByStatus, dashboardStats.reportOutputByStatus)
        && Objects.equals(this.funnelByStage, dashboardStats.funnelByStage);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        contractCount,
        receiptCount,
        sampleCount,
        reportCountByStatus,
        pendingTaskCount,
        todayTestCount,
        qualifiedRateByMaterial,
        reportOutputByStatus,
        funnelByStage);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DashboardStats {\n");
    sb.append("    contractCount: ").append(toIndentedString(contractCount)).append("\n");
    sb.append("    receiptCount: ").append(toIndentedString(receiptCount)).append("\n");
    sb.append("    sampleCount: ").append(toIndentedString(sampleCount)).append("\n");
    sb.append("    reportCountByStatus: ")
        .append(toIndentedString(reportCountByStatus))
        .append("\n");
    sb.append("    pendingTaskCount: ").append(toIndentedString(pendingTaskCount)).append("\n");
    sb.append("    todayTestCount: ").append(toIndentedString(todayTestCount)).append("\n");
    sb.append("    qualifiedRateByMaterial: ")
        .append(toIndentedString(qualifiedRateByMaterial))
        .append("\n");
    sb.append("    reportOutputByStatus: ")
        .append(toIndentedString(reportOutputByStatus))
        .append("\n");
    sb.append("    funnelByStage: ").append(toIndentedString(funnelByStage)).append("\n");
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
