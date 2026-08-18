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

/** CreateCalculationRuleRequest */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    date = "2026-08-18T09:31:54.550738400+08:00[Asia/Shanghai]",
    comments = "Generator version: 7.24.0")
public class CreateCalculationRuleRequest {

  private String inspectionObjectCode;

  private String inspectionParameterCode;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String testingStandardCode;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String reportNameCode;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable CalculationAlgorithmType algorithmType;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable Integer specimenCount;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String formula;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String conditions;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String roundingRule;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String remark;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable Integer sortOrder;

  public CreateCalculationRuleRequest() {
    super();
  }

  /** Constructor with only required parameters */
  public CreateCalculationRuleRequest(String inspectionObjectCode, String inspectionParameterCode) {
    this.inspectionObjectCode = inspectionObjectCode;
    this.inspectionParameterCode = inspectionParameterCode;
  }

  public CreateCalculationRuleRequest inspectionObjectCode(String inspectionObjectCode) {
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

  public CreateCalculationRuleRequest inspectionParameterCode(String inspectionParameterCode) {
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

  public CreateCalculationRuleRequest testingStandardCode(@Nullable String testingStandardCode) {
    this.testingStandardCode = testingStandardCode;
    return this;
  }

  /**
   * Get testingStandardCode
   *
   * @return testingStandardCode
   */
  @Schema(name = "testingStandardCode", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("testingStandardCode")
  public @Nullable String getTestingStandardCode() {
    return testingStandardCode;
  }

  @JsonProperty("testingStandardCode")
  public void setTestingStandardCode(@Nullable String testingStandardCode) {
    this.testingStandardCode = testingStandardCode;
  }

  public CreateCalculationRuleRequest reportNameCode(@Nullable String reportNameCode) {
    this.reportNameCode = reportNameCode;
    return this;
  }

  /**
   * Get reportNameCode
   *
   * @return reportNameCode
   */
  @Schema(name = "reportNameCode", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("reportNameCode")
  public @Nullable String getReportNameCode() {
    return reportNameCode;
  }

  @JsonProperty("reportNameCode")
  public void setReportNameCode(@Nullable String reportNameCode) {
    this.reportNameCode = reportNameCode;
  }

  public CreateCalculationRuleRequest algorithmType(
      @Nullable CalculationAlgorithmType algorithmType) {
    this.algorithmType = algorithmType;
    return this;
  }

  /**
   * Get algorithmType
   *
   * @return algorithmType
   */
  @Valid
  @Schema(name = "algorithmType", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("algorithmType")
  public @Nullable CalculationAlgorithmType getAlgorithmType() {
    return algorithmType;
  }

  @JsonProperty("algorithmType")
  public void setAlgorithmType(@Nullable CalculationAlgorithmType algorithmType) {
    this.algorithmType = algorithmType;
  }

  public CreateCalculationRuleRequest specimenCount(@Nullable Integer specimenCount) {
    this.specimenCount = specimenCount;
    return this;
  }

  /**
   * Get specimenCount
   *
   * @return specimenCount
   */
  @Schema(name = "specimenCount", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("specimenCount")
  public @Nullable Integer getSpecimenCount() {
    return specimenCount;
  }

  @JsonProperty("specimenCount")
  public void setSpecimenCount(@Nullable Integer specimenCount) {
    this.specimenCount = specimenCount;
  }

  public CreateCalculationRuleRequest formula(@Nullable String formula) {
    this.formula = formula;
    return this;
  }

  /**
   * Get formula
   *
   * @return formula
   */
  @Schema(name = "formula", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("formula")
  public @Nullable String getFormula() {
    return formula;
  }

  @JsonProperty("formula")
  public void setFormula(@Nullable String formula) {
    this.formula = formula;
  }

  public CreateCalculationRuleRequest conditions(@Nullable String conditions) {
    this.conditions = conditions;
    return this;
  }

  /**
   * Get conditions
   *
   * @return conditions
   */
  @Schema(name = "conditions", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("conditions")
  public @Nullable String getConditions() {
    return conditions;
  }

  @JsonProperty("conditions")
  public void setConditions(@Nullable String conditions) {
    this.conditions = conditions;
  }

  public CreateCalculationRuleRequest roundingRule(@Nullable String roundingRule) {
    this.roundingRule = roundingRule;
    return this;
  }

  /**
   * Get roundingRule
   *
   * @return roundingRule
   */
  @Schema(name = "roundingRule", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("roundingRule")
  public @Nullable String getRoundingRule() {
    return roundingRule;
  }

  @JsonProperty("roundingRule")
  public void setRoundingRule(@Nullable String roundingRule) {
    this.roundingRule = roundingRule;
  }

  public CreateCalculationRuleRequest remark(@Nullable String remark) {
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

  public CreateCalculationRuleRequest sortOrder(@Nullable Integer sortOrder) {
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
    CreateCalculationRuleRequest createCalculationRuleRequest = (CreateCalculationRuleRequest) o;
    return Objects.equals(
            this.inspectionObjectCode, createCalculationRuleRequest.inspectionObjectCode)
        && Objects.equals(
            this.inspectionParameterCode, createCalculationRuleRequest.inspectionParameterCode)
        && Objects.equals(
            this.testingStandardCode, createCalculationRuleRequest.testingStandardCode)
        && Objects.equals(this.reportNameCode, createCalculationRuleRequest.reportNameCode)
        && Objects.equals(this.algorithmType, createCalculationRuleRequest.algorithmType)
        && Objects.equals(this.specimenCount, createCalculationRuleRequest.specimenCount)
        && Objects.equals(this.formula, createCalculationRuleRequest.formula)
        && Objects.equals(this.conditions, createCalculationRuleRequest.conditions)
        && Objects.equals(this.roundingRule, createCalculationRuleRequest.roundingRule)
        && Objects.equals(this.remark, createCalculationRuleRequest.remark)
        && Objects.equals(this.sortOrder, createCalculationRuleRequest.sortOrder);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        inspectionObjectCode,
        inspectionParameterCode,
        testingStandardCode,
        reportNameCode,
        algorithmType,
        specimenCount,
        formula,
        conditions,
        roundingRule,
        remark,
        sortOrder);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreateCalculationRuleRequest {\n");
    sb.append("    inspectionObjectCode: ")
        .append(toIndentedString(inspectionObjectCode))
        .append("\n");
    sb.append("    inspectionParameterCode: ")
        .append(toIndentedString(inspectionParameterCode))
        .append("\n");
    sb.append("    testingStandardCode: ")
        .append(toIndentedString(testingStandardCode))
        .append("\n");
    sb.append("    reportNameCode: ").append(toIndentedString(reportNameCode)).append("\n");
    sb.append("    algorithmType: ").append(toIndentedString(algorithmType)).append("\n");
    sb.append("    specimenCount: ").append(toIndentedString(specimenCount)).append("\n");
    sb.append("    formula: ").append(toIndentedString(formula)).append("\n");
    sb.append("    conditions: ").append(toIndentedString(conditions)).append("\n");
    sb.append("    roundingRule: ").append(toIndentedString(roundingRule)).append("\n");
    sb.append("    remark: ").append(toIndentedString(remark)).append("\n");
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
