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

/** CalculationMethod */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    date = "2026-09-04T01:01:08.136686300+08:00[Asia/Shanghai]",
    comments = "Generator version: 7.24.0")
public class CalculationMethod {

  private String inspectionObjectCode;

  private String inspectionParameterCode;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String testingStandardCode;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String reportNameCode;

  private CalculationAlgorithmType algorithmType;

  private Integer specimenCount;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String formula;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String conditions;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String roundingRule;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String remark;

  private Integer sortOrder;

  private String createdAt;

  private String updatedAt;

  public CalculationMethod() {
    super();
  }

  /** Constructor with only required parameters */
  public CalculationMethod(
      String inspectionObjectCode,
      String inspectionParameterCode,
      CalculationAlgorithmType algorithmType,
      Integer specimenCount,
      Integer sortOrder,
      String createdAt,
      String updatedAt) {
    this.inspectionObjectCode = inspectionObjectCode;
    this.inspectionParameterCode = inspectionParameterCode;
    this.algorithmType = algorithmType;
    this.specimenCount = specimenCount;
    this.sortOrder = sortOrder;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public CalculationMethod inspectionObjectCode(String inspectionObjectCode) {
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

  public CalculationMethod inspectionParameterCode(String inspectionParameterCode) {
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

  public CalculationMethod testingStandardCode(@Nullable String testingStandardCode) {
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

  public CalculationMethod reportNameCode(@Nullable String reportNameCode) {
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

  public CalculationMethod algorithmType(CalculationAlgorithmType algorithmType) {
    this.algorithmType = algorithmType;
    return this;
  }

  /**
   * Get algorithmType
   *
   * @return algorithmType
   */
  @NotNull
  @Valid
  @Schema(name = "algorithmType", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("algorithmType")
  public CalculationAlgorithmType getAlgorithmType() {
    return algorithmType;
  }

  @JsonProperty("algorithmType")
  public void setAlgorithmType(CalculationAlgorithmType algorithmType) {
    this.algorithmType = algorithmType;
  }

  public CalculationMethod specimenCount(Integer specimenCount) {
    this.specimenCount = specimenCount;
    return this;
  }

  /**
   * Get specimenCount
   *
   * @return specimenCount
   */
  @NotNull
  @Schema(name = "specimenCount", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("specimenCount")
  public Integer getSpecimenCount() {
    return specimenCount;
  }

  @JsonProperty("specimenCount")
  public void setSpecimenCount(Integer specimenCount) {
    this.specimenCount = specimenCount;
  }

  public CalculationMethod formula(@Nullable String formula) {
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

  public CalculationMethod conditions(@Nullable String conditions) {
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

  public CalculationMethod roundingRule(@Nullable String roundingRule) {
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

  public CalculationMethod remark(@Nullable String remark) {
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

  public CalculationMethod sortOrder(Integer sortOrder) {
    this.sortOrder = sortOrder;
    return this;
  }

  /**
   * Get sortOrder
   *
   * @return sortOrder
   */
  @NotNull
  @Schema(name = "sortOrder", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("sortOrder")
  public Integer getSortOrder() {
    return sortOrder;
  }

  @JsonProperty("sortOrder")
  public void setSortOrder(Integer sortOrder) {
    this.sortOrder = sortOrder;
  }

  public CalculationMethod createdAt(String createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  /**
   * Get createdAt
   *
   * @return createdAt
   */
  @NotNull
  @Schema(name = "createdAt", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("createdAt")
  public String getCreatedAt() {
    return createdAt;
  }

  @JsonProperty("createdAt")
  public void setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
  }

  public CalculationMethod updatedAt(String updatedAt) {
    this.updatedAt = updatedAt;
    return this;
  }

  /**
   * Get updatedAt
   *
   * @return updatedAt
   */
  @NotNull
  @Schema(name = "updatedAt", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("updatedAt")
  public String getUpdatedAt() {
    return updatedAt;
  }

  @JsonProperty("updatedAt")
  public void setUpdatedAt(String updatedAt) {
    this.updatedAt = updatedAt;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CalculationMethod calculationMethod = (CalculationMethod) o;
    return Objects.equals(this.inspectionObjectCode, calculationMethod.inspectionObjectCode)
        && Objects.equals(this.inspectionParameterCode, calculationMethod.inspectionParameterCode)
        && Objects.equals(this.testingStandardCode, calculationMethod.testingStandardCode)
        && Objects.equals(this.reportNameCode, calculationMethod.reportNameCode)
        && Objects.equals(this.algorithmType, calculationMethod.algorithmType)
        && Objects.equals(this.specimenCount, calculationMethod.specimenCount)
        && Objects.equals(this.formula, calculationMethod.formula)
        && Objects.equals(this.conditions, calculationMethod.conditions)
        && Objects.equals(this.roundingRule, calculationMethod.roundingRule)
        && Objects.equals(this.remark, calculationMethod.remark)
        && Objects.equals(this.sortOrder, calculationMethod.sortOrder)
        && Objects.equals(this.createdAt, calculationMethod.createdAt)
        && Objects.equals(this.updatedAt, calculationMethod.updatedAt);
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
        sortOrder,
        createdAt,
        updatedAt);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CalculationMethod {\n");
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
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
    sb.append("    updatedAt: ").append(toIndentedString(updatedAt)).append("\n");
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
