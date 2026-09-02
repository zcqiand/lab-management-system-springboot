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

/** CreateTechnicalRequirementRequest */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    date = "2026-09-02T22:35:42.457326500+08:00[Asia/Shanghai]",
    comments = "Generator version: 7.24.0")
public class CreateTechnicalRequirementRequest {

  private String inspectionObjectCode;

  private String inspectionParameterCode;

  private String judgmentStandardCode;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String conditions;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable RequirementValueType valueType;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable Integer minValue;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable Integer maxValue;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String targetValue;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String expression;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String unit;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable RequirementComparison comparison;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable RequirementJudgmentMode judgmentMode;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable RequirementVerificationStatus verificationStatus;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String clause;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable Integer sourcePage;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String sourceHash;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String brand;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String model;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String grade;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String spec;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String sieve;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String remark;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable Integer sortOrder;

  public CreateTechnicalRequirementRequest() {
    super();
  }

  /** Constructor with only required parameters */
  public CreateTechnicalRequirementRequest(
      String inspectionObjectCode, String inspectionParameterCode, String judgmentStandardCode) {
    this.inspectionObjectCode = inspectionObjectCode;
    this.inspectionParameterCode = inspectionParameterCode;
    this.judgmentStandardCode = judgmentStandardCode;
  }

  public CreateTechnicalRequirementRequest inspectionObjectCode(String inspectionObjectCode) {
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

  public CreateTechnicalRequirementRequest inspectionParameterCode(String inspectionParameterCode) {
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

  public CreateTechnicalRequirementRequest judgmentStandardCode(String judgmentStandardCode) {
    this.judgmentStandardCode = judgmentStandardCode;
    return this;
  }

  /**
   * Get judgmentStandardCode
   *
   * @return judgmentStandardCode
   */
  @NotNull
  @Schema(name = "judgmentStandardCode", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("judgmentStandardCode")
  public String getJudgmentStandardCode() {
    return judgmentStandardCode;
  }

  @JsonProperty("judgmentStandardCode")
  public void setJudgmentStandardCode(String judgmentStandardCode) {
    this.judgmentStandardCode = judgmentStandardCode;
  }

  public CreateTechnicalRequirementRequest conditions(@Nullable String conditions) {
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

  public CreateTechnicalRequirementRequest valueType(@Nullable RequirementValueType valueType) {
    this.valueType = valueType;
    return this;
  }

  /**
   * Get valueType
   *
   * @return valueType
   */
  @Valid
  @Schema(name = "valueType", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("valueType")
  public @Nullable RequirementValueType getValueType() {
    return valueType;
  }

  @JsonProperty("valueType")
  public void setValueType(@Nullable RequirementValueType valueType) {
    this.valueType = valueType;
  }

  public CreateTechnicalRequirementRequest minValue(@Nullable Integer minValue) {
    this.minValue = minValue;
    return this;
  }

  /**
   * Get minValue
   *
   * @return minValue
   */
  @Schema(name = "minValue", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("minValue")
  public @Nullable Integer getMinValue() {
    return minValue;
  }

  @JsonProperty("minValue")
  public void setMinValue(@Nullable Integer minValue) {
    this.minValue = minValue;
  }

  public CreateTechnicalRequirementRequest maxValue(@Nullable Integer maxValue) {
    this.maxValue = maxValue;
    return this;
  }

  /**
   * Get maxValue
   *
   * @return maxValue
   */
  @Schema(name = "maxValue", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("maxValue")
  public @Nullable Integer getMaxValue() {
    return maxValue;
  }

  @JsonProperty("maxValue")
  public void setMaxValue(@Nullable Integer maxValue) {
    this.maxValue = maxValue;
  }

  public CreateTechnicalRequirementRequest targetValue(@Nullable String targetValue) {
    this.targetValue = targetValue;
    return this;
  }

  /**
   * Get targetValue
   *
   * @return targetValue
   */
  @Schema(name = "targetValue", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("targetValue")
  public @Nullable String getTargetValue() {
    return targetValue;
  }

  @JsonProperty("targetValue")
  public void setTargetValue(@Nullable String targetValue) {
    this.targetValue = targetValue;
  }

  public CreateTechnicalRequirementRequest expression(@Nullable String expression) {
    this.expression = expression;
    return this;
  }

  /**
   * Get expression
   *
   * @return expression
   */
  @Schema(name = "expression", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("expression")
  public @Nullable String getExpression() {
    return expression;
  }

  @JsonProperty("expression")
  public void setExpression(@Nullable String expression) {
    this.expression = expression;
  }

  public CreateTechnicalRequirementRequest unit(@Nullable String unit) {
    this.unit = unit;
    return this;
  }

  /**
   * Get unit
   *
   * @return unit
   */
  @Schema(name = "unit", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("unit")
  public @Nullable String getUnit() {
    return unit;
  }

  @JsonProperty("unit")
  public void setUnit(@Nullable String unit) {
    this.unit = unit;
  }

  public CreateTechnicalRequirementRequest comparison(@Nullable RequirementComparison comparison) {
    this.comparison = comparison;
    return this;
  }

  /**
   * Get comparison
   *
   * @return comparison
   */
  @Valid
  @Schema(name = "comparison", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("comparison")
  public @Nullable RequirementComparison getComparison() {
    return comparison;
  }

  @JsonProperty("comparison")
  public void setComparison(@Nullable RequirementComparison comparison) {
    this.comparison = comparison;
  }

  public CreateTechnicalRequirementRequest judgmentMode(
      @Nullable RequirementJudgmentMode judgmentMode) {
    this.judgmentMode = judgmentMode;
    return this;
  }

  /**
   * Get judgmentMode
   *
   * @return judgmentMode
   */
  @Valid
  @Schema(name = "judgmentMode", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("judgmentMode")
  public @Nullable RequirementJudgmentMode getJudgmentMode() {
    return judgmentMode;
  }

  @JsonProperty("judgmentMode")
  public void setJudgmentMode(@Nullable RequirementJudgmentMode judgmentMode) {
    this.judgmentMode = judgmentMode;
  }

  public CreateTechnicalRequirementRequest verificationStatus(
      @Nullable RequirementVerificationStatus verificationStatus) {
    this.verificationStatus = verificationStatus;
    return this;
  }

  /**
   * Get verificationStatus
   *
   * @return verificationStatus
   */
  @Valid
  @Schema(name = "verificationStatus", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("verificationStatus")
  public @Nullable RequirementVerificationStatus getVerificationStatus() {
    return verificationStatus;
  }

  @JsonProperty("verificationStatus")
  public void setVerificationStatus(@Nullable RequirementVerificationStatus verificationStatus) {
    this.verificationStatus = verificationStatus;
  }

  public CreateTechnicalRequirementRequest clause(@Nullable String clause) {
    this.clause = clause;
    return this;
  }

  /**
   * Get clause
   *
   * @return clause
   */
  @Schema(name = "clause", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("clause")
  public @Nullable String getClause() {
    return clause;
  }

  @JsonProperty("clause")
  public void setClause(@Nullable String clause) {
    this.clause = clause;
  }

  public CreateTechnicalRequirementRequest sourcePage(@Nullable Integer sourcePage) {
    this.sourcePage = sourcePage;
    return this;
  }

  /**
   * Get sourcePage
   *
   * @return sourcePage
   */
  @Schema(name = "sourcePage", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("sourcePage")
  public @Nullable Integer getSourcePage() {
    return sourcePage;
  }

  @JsonProperty("sourcePage")
  public void setSourcePage(@Nullable Integer sourcePage) {
    this.sourcePage = sourcePage;
  }

  public CreateTechnicalRequirementRequest sourceHash(@Nullable String sourceHash) {
    this.sourceHash = sourceHash;
    return this;
  }

  /**
   * Get sourceHash
   *
   * @return sourceHash
   */
  @Schema(name = "sourceHash", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("sourceHash")
  public @Nullable String getSourceHash() {
    return sourceHash;
  }

  @JsonProperty("sourceHash")
  public void setSourceHash(@Nullable String sourceHash) {
    this.sourceHash = sourceHash;
  }

  public CreateTechnicalRequirementRequest brand(@Nullable String brand) {
    this.brand = brand;
    return this;
  }

  /**
   * Get brand
   *
   * @return brand
   */
  @Schema(name = "brand", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("brand")
  public @Nullable String getBrand() {
    return brand;
  }

  @JsonProperty("brand")
  public void setBrand(@Nullable String brand) {
    this.brand = brand;
  }

  public CreateTechnicalRequirementRequest model(@Nullable String model) {
    this.model = model;
    return this;
  }

  /**
   * Get model
   *
   * @return model
   */
  @Schema(name = "model", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("model")
  public @Nullable String getModel() {
    return model;
  }

  @JsonProperty("model")
  public void setModel(@Nullable String model) {
    this.model = model;
  }

  public CreateTechnicalRequirementRequest grade(@Nullable String grade) {
    this.grade = grade;
    return this;
  }

  /**
   * Get grade
   *
   * @return grade
   */
  @Schema(name = "grade", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("grade")
  public @Nullable String getGrade() {
    return grade;
  }

  @JsonProperty("grade")
  public void setGrade(@Nullable String grade) {
    this.grade = grade;
  }

  public CreateTechnicalRequirementRequest spec(@Nullable String spec) {
    this.spec = spec;
    return this;
  }

  /**
   * Get spec
   *
   * @return spec
   */
  @Schema(name = "spec", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("spec")
  public @Nullable String getSpec() {
    return spec;
  }

  @JsonProperty("spec")
  public void setSpec(@Nullable String spec) {
    this.spec = spec;
  }

  public CreateTechnicalRequirementRequest sieve(@Nullable String sieve) {
    this.sieve = sieve;
    return this;
  }

  /**
   * Get sieve
   *
   * @return sieve
   */
  @Schema(name = "sieve", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("sieve")
  public @Nullable String getSieve() {
    return sieve;
  }

  @JsonProperty("sieve")
  public void setSieve(@Nullable String sieve) {
    this.sieve = sieve;
  }

  public CreateTechnicalRequirementRequest remark(@Nullable String remark) {
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

  public CreateTechnicalRequirementRequest sortOrder(@Nullable Integer sortOrder) {
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
    CreateTechnicalRequirementRequest createTechnicalRequirementRequest =
        (CreateTechnicalRequirementRequest) o;
    return Objects.equals(
            this.inspectionObjectCode, createTechnicalRequirementRequest.inspectionObjectCode)
        && Objects.equals(
            this.inspectionParameterCode, createTechnicalRequirementRequest.inspectionParameterCode)
        && Objects.equals(
            this.judgmentStandardCode, createTechnicalRequirementRequest.judgmentStandardCode)
        && Objects.equals(this.conditions, createTechnicalRequirementRequest.conditions)
        && Objects.equals(this.valueType, createTechnicalRequirementRequest.valueType)
        && Objects.equals(this.minValue, createTechnicalRequirementRequest.minValue)
        && Objects.equals(this.maxValue, createTechnicalRequirementRequest.maxValue)
        && Objects.equals(this.targetValue, createTechnicalRequirementRequest.targetValue)
        && Objects.equals(this.expression, createTechnicalRequirementRequest.expression)
        && Objects.equals(this.unit, createTechnicalRequirementRequest.unit)
        && Objects.equals(this.comparison, createTechnicalRequirementRequest.comparison)
        && Objects.equals(this.judgmentMode, createTechnicalRequirementRequest.judgmentMode)
        && Objects.equals(
            this.verificationStatus, createTechnicalRequirementRequest.verificationStatus)
        && Objects.equals(this.clause, createTechnicalRequirementRequest.clause)
        && Objects.equals(this.sourcePage, createTechnicalRequirementRequest.sourcePage)
        && Objects.equals(this.sourceHash, createTechnicalRequirementRequest.sourceHash)
        && Objects.equals(this.brand, createTechnicalRequirementRequest.brand)
        && Objects.equals(this.model, createTechnicalRequirementRequest.model)
        && Objects.equals(this.grade, createTechnicalRequirementRequest.grade)
        && Objects.equals(this.spec, createTechnicalRequirementRequest.spec)
        && Objects.equals(this.sieve, createTechnicalRequirementRequest.sieve)
        && Objects.equals(this.remark, createTechnicalRequirementRequest.remark)
        && Objects.equals(this.sortOrder, createTechnicalRequirementRequest.sortOrder);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        inspectionObjectCode,
        inspectionParameterCode,
        judgmentStandardCode,
        conditions,
        valueType,
        minValue,
        maxValue,
        targetValue,
        expression,
        unit,
        comparison,
        judgmentMode,
        verificationStatus,
        clause,
        sourcePage,
        sourceHash,
        brand,
        model,
        grade,
        spec,
        sieve,
        remark,
        sortOrder);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreateTechnicalRequirementRequest {\n");
    sb.append("    inspectionObjectCode: ")
        .append(toIndentedString(inspectionObjectCode))
        .append("\n");
    sb.append("    inspectionParameterCode: ")
        .append(toIndentedString(inspectionParameterCode))
        .append("\n");
    sb.append("    judgmentStandardCode: ")
        .append(toIndentedString(judgmentStandardCode))
        .append("\n");
    sb.append("    conditions: ").append(toIndentedString(conditions)).append("\n");
    sb.append("    valueType: ").append(toIndentedString(valueType)).append("\n");
    sb.append("    minValue: ").append(toIndentedString(minValue)).append("\n");
    sb.append("    maxValue: ").append(toIndentedString(maxValue)).append("\n");
    sb.append("    targetValue: ").append(toIndentedString(targetValue)).append("\n");
    sb.append("    expression: ").append(toIndentedString(expression)).append("\n");
    sb.append("    unit: ").append(toIndentedString(unit)).append("\n");
    sb.append("    comparison: ").append(toIndentedString(comparison)).append("\n");
    sb.append("    judgmentMode: ").append(toIndentedString(judgmentMode)).append("\n");
    sb.append("    verificationStatus: ").append(toIndentedString(verificationStatus)).append("\n");
    sb.append("    clause: ").append(toIndentedString(clause)).append("\n");
    sb.append("    sourcePage: ").append(toIndentedString(sourcePage)).append("\n");
    sb.append("    sourceHash: ").append(toIndentedString(sourceHash)).append("\n");
    sb.append("    brand: ").append(toIndentedString(brand)).append("\n");
    sb.append("    model: ").append(toIndentedString(model)).append("\n");
    sb.append("    grade: ").append(toIndentedString(grade)).append("\n");
    sb.append("    spec: ").append(toIndentedString(spec)).append("\n");
    sb.append("    sieve: ").append(toIndentedString(sieve)).append("\n");
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
