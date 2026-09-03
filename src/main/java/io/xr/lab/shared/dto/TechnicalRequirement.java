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

/** TechnicalRequirement */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    date = "2026-09-04T01:01:08.136686300+08:00[Asia/Shanghai]",
    comments = "Generator version: 7.24.0")
public class TechnicalRequirement {

  private String tenantId;

  private String inspectionObjectCode;

  private String inspectionParameterCode;

  private String judgmentStandardCode;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String conditions;

  private RequirementValueType valueType;

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

  private RequirementComparison comparison;

  private RequirementJudgmentMode judgmentMode;

  private RequirementVerificationStatus verificationStatus;

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

  private Integer sortOrder;

  private String createdAt;

  private String updatedAt;

  public TechnicalRequirement() {
    super();
  }

  /** Constructor with only required parameters */
  public TechnicalRequirement(
      String tenantId,
      String inspectionObjectCode,
      String inspectionParameterCode,
      String judgmentStandardCode,
      RequirementValueType valueType,
      RequirementComparison comparison,
      RequirementJudgmentMode judgmentMode,
      RequirementVerificationStatus verificationStatus,
      Integer sortOrder,
      String createdAt,
      String updatedAt) {
    this.tenantId = tenantId;
    this.inspectionObjectCode = inspectionObjectCode;
    this.inspectionParameterCode = inspectionParameterCode;
    this.judgmentStandardCode = judgmentStandardCode;
    this.valueType = valueType;
    this.comparison = comparison;
    this.judgmentMode = judgmentMode;
    this.verificationStatus = verificationStatus;
    this.sortOrder = sortOrder;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public TechnicalRequirement tenantId(String tenantId) {
    this.tenantId = tenantId;
    return this;
  }

  /**
   * Get tenantId
   *
   * @return tenantId
   */
  @NotNull
  @Schema(name = "tenantId", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("tenantId")
  public String getTenantId() {
    return tenantId;
  }

  @JsonProperty("tenantId")
  public void setTenantId(String tenantId) {
    this.tenantId = tenantId;
  }

  public TechnicalRequirement inspectionObjectCode(String inspectionObjectCode) {
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

  public TechnicalRequirement inspectionParameterCode(String inspectionParameterCode) {
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

  public TechnicalRequirement judgmentStandardCode(String judgmentStandardCode) {
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

  public TechnicalRequirement conditions(@Nullable String conditions) {
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

  public TechnicalRequirement valueType(RequirementValueType valueType) {
    this.valueType = valueType;
    return this;
  }

  /**
   * Get valueType
   *
   * @return valueType
   */
  @NotNull
  @Valid
  @Schema(name = "valueType", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("valueType")
  public RequirementValueType getValueType() {
    return valueType;
  }

  @JsonProperty("valueType")
  public void setValueType(RequirementValueType valueType) {
    this.valueType = valueType;
  }

  public TechnicalRequirement minValue(@Nullable Integer minValue) {
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

  public TechnicalRequirement maxValue(@Nullable Integer maxValue) {
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

  public TechnicalRequirement targetValue(@Nullable String targetValue) {
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

  public TechnicalRequirement expression(@Nullable String expression) {
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

  public TechnicalRequirement unit(@Nullable String unit) {
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

  public TechnicalRequirement comparison(RequirementComparison comparison) {
    this.comparison = comparison;
    return this;
  }

  /**
   * Get comparison
   *
   * @return comparison
   */
  @NotNull
  @Valid
  @Schema(name = "comparison", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("comparison")
  public RequirementComparison getComparison() {
    return comparison;
  }

  @JsonProperty("comparison")
  public void setComparison(RequirementComparison comparison) {
    this.comparison = comparison;
  }

  public TechnicalRequirement judgmentMode(RequirementJudgmentMode judgmentMode) {
    this.judgmentMode = judgmentMode;
    return this;
  }

  /**
   * Get judgmentMode
   *
   * @return judgmentMode
   */
  @NotNull
  @Valid
  @Schema(name = "judgmentMode", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("judgmentMode")
  public RequirementJudgmentMode getJudgmentMode() {
    return judgmentMode;
  }

  @JsonProperty("judgmentMode")
  public void setJudgmentMode(RequirementJudgmentMode judgmentMode) {
    this.judgmentMode = judgmentMode;
  }

  public TechnicalRequirement verificationStatus(RequirementVerificationStatus verificationStatus) {
    this.verificationStatus = verificationStatus;
    return this;
  }

  /**
   * Get verificationStatus
   *
   * @return verificationStatus
   */
  @NotNull
  @Valid
  @Schema(name = "verificationStatus", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("verificationStatus")
  public RequirementVerificationStatus getVerificationStatus() {
    return verificationStatus;
  }

  @JsonProperty("verificationStatus")
  public void setVerificationStatus(RequirementVerificationStatus verificationStatus) {
    this.verificationStatus = verificationStatus;
  }

  public TechnicalRequirement clause(@Nullable String clause) {
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

  public TechnicalRequirement sourcePage(@Nullable Integer sourcePage) {
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

  public TechnicalRequirement sourceHash(@Nullable String sourceHash) {
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

  public TechnicalRequirement brand(@Nullable String brand) {
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

  public TechnicalRequirement model(@Nullable String model) {
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

  public TechnicalRequirement grade(@Nullable String grade) {
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

  public TechnicalRequirement spec(@Nullable String spec) {
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

  public TechnicalRequirement sieve(@Nullable String sieve) {
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

  public TechnicalRequirement remark(@Nullable String remark) {
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

  public TechnicalRequirement sortOrder(Integer sortOrder) {
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

  public TechnicalRequirement createdAt(String createdAt) {
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

  public TechnicalRequirement updatedAt(String updatedAt) {
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
    TechnicalRequirement technicalRequirement = (TechnicalRequirement) o;
    return Objects.equals(this.tenantId, technicalRequirement.tenantId)
        && Objects.equals(this.inspectionObjectCode, technicalRequirement.inspectionObjectCode)
        && Objects.equals(
            this.inspectionParameterCode, technicalRequirement.inspectionParameterCode)
        && Objects.equals(this.judgmentStandardCode, technicalRequirement.judgmentStandardCode)
        && Objects.equals(this.conditions, technicalRequirement.conditions)
        && Objects.equals(this.valueType, technicalRequirement.valueType)
        && Objects.equals(this.minValue, technicalRequirement.minValue)
        && Objects.equals(this.maxValue, technicalRequirement.maxValue)
        && Objects.equals(this.targetValue, technicalRequirement.targetValue)
        && Objects.equals(this.expression, technicalRequirement.expression)
        && Objects.equals(this.unit, technicalRequirement.unit)
        && Objects.equals(this.comparison, technicalRequirement.comparison)
        && Objects.equals(this.judgmentMode, technicalRequirement.judgmentMode)
        && Objects.equals(this.verificationStatus, technicalRequirement.verificationStatus)
        && Objects.equals(this.clause, technicalRequirement.clause)
        && Objects.equals(this.sourcePage, technicalRequirement.sourcePage)
        && Objects.equals(this.sourceHash, technicalRequirement.sourceHash)
        && Objects.equals(this.brand, technicalRequirement.brand)
        && Objects.equals(this.model, technicalRequirement.model)
        && Objects.equals(this.grade, technicalRequirement.grade)
        && Objects.equals(this.spec, technicalRequirement.spec)
        && Objects.equals(this.sieve, technicalRequirement.sieve)
        && Objects.equals(this.remark, technicalRequirement.remark)
        && Objects.equals(this.sortOrder, technicalRequirement.sortOrder)
        && Objects.equals(this.createdAt, technicalRequirement.createdAt)
        && Objects.equals(this.updatedAt, technicalRequirement.updatedAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        tenantId,
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
        sortOrder,
        createdAt,
        updatedAt);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TechnicalRequirement {\n");
    sb.append("    tenantId: ").append(toIndentedString(tenantId)).append("\n");
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
