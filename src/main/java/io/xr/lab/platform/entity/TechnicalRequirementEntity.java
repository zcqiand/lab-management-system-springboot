package io.xr.lab.platform.entity;

import io.xr.lab.platform.entity.enums.RequirementComparisonConverter;
import io.xr.lab.platform.entity.enums.RequirementJudgmentModeConverter;
import io.xr.lab.platform.entity.enums.RequirementValueTypeConverter;
import io.xr.lab.platform.entity.enums.RequirementVerificationStatusConverter;
import io.xr.lab.shared.dto.RequirementComparison;
import io.xr.lab.shared.dto.RequirementJudgmentMode;
import io.xr.lab.shared.dto.RequirementValueType;
import io.xr.lab.shared.dto.RequirementVerificationStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

/**
 * V005__init_technical_requirements.sql — 技术要求维护（M06.F06）。PK = (inspection_object_code,
 * inspection_parameter_code, judgment_standard_code) per V005；V012 进一步加 tenant_id 隔离，但此处用业务三键 +
 * tenant 查询过滤（list 端点保留多种过滤语义，详见 service）。
 */
@Entity
@Table(name = "inspection_technical_requirements")
@IdClass(TechnicalRequirementKey.class)
public class TechnicalRequirementEntity {

  @Id
  @Column(name = "inspection_object_code", length = 64, nullable = false)
  private String inspectionObjectCode;

  @Id
  @Column(name = "inspection_parameter_code", length = 64, nullable = false)
  private String inspectionParameterCode;

  @Id
  @Column(name = "judgment_standard_code", length = 64, nullable = false)
  private String judgmentStandardCode;

  @Column(name = "tenant_id", length = 64, nullable = false)
  private String tenantId = "";

  @Column(name = "conditions")
  private String conditions;

  @Convert(converter = RequirementValueTypeConverter.class)
  @Column(name = "value_type", nullable = false)
  private RequirementValueType valueType;

  @Column(name = "min_value")
  private Integer minValue;

  @Column(name = "max_value")
  private Integer maxValue;

  @Column(name = "target_value")
  private String targetValue;

  @Column(name = "expression")
  private String expression;

  @Column(name = "unit")
  private String unit;

  @Convert(converter = RequirementComparisonConverter.class)
  @Column(name = "comparison", nullable = false)
  private RequirementComparison comparison;

  @Convert(converter = RequirementJudgmentModeConverter.class)
  @Column(name = "judgment_mode", nullable = false)
  private RequirementJudgmentMode judgmentMode;

  @Convert(converter = RequirementVerificationStatusConverter.class)
  @Column(name = "verification_status", nullable = false)
  private RequirementVerificationStatus verificationStatus;

  @Column(name = "clause")
  private String clause;

  @Column(name = "source_page")
  private Integer sourcePage;

  @Column(name = "source_hash")
  private String sourceHash;

  @Column(name = "brand")
  private String brand;

  @Column(name = "model")
  private String model;

  @Column(name = "grade")
  private String grade;

  @Column(name = "spec")
  private String spec;

  @Column(name = "sieve")
  private String sieve;

  @Column(name = "remark")
  private String remark;

  @Column(name = "sort_order", nullable = false)
  private Integer sortOrder = 0;

  @Column(name = "created_at", nullable = false)
  private String createdAt = "";

  @Column(name = "updated_at", nullable = false)
  private String updatedAt = "";

  // getters/setters (terse — Lombok-style not used per B1 conventions)

  public String getInspectionObjectCode() {
    return inspectionObjectCode;
  }

  public void setInspectionObjectCode(String v) {
    this.inspectionObjectCode = v;
  }

  public String getInspectionParameterCode() {
    return inspectionParameterCode;
  }

  public void setInspectionParameterCode(String v) {
    this.inspectionParameterCode = v;
  }

  public String getJudgmentStandardCode() {
    return judgmentStandardCode;
  }

  public void setJudgmentStandardCode(String v) {
    this.judgmentStandardCode = v;
  }

  public String getTenantId() {
    return tenantId;
  }

  public void setTenantId(String v) {
    this.tenantId = v;
  }

  public String getConditions() {
    return conditions;
  }

  public void setConditions(String v) {
    this.conditions = v;
  }

  public RequirementValueType getValueType() {
    return valueType;
  }

  public void setValueType(RequirementValueType v) {
    this.valueType = v;
  }

  public Integer getMinValue() {
    return minValue;
  }

  public void setMinValue(Integer v) {
    this.minValue = v;
  }

  public Integer getMaxValue() {
    return maxValue;
  }

  public void setMaxValue(Integer v) {
    this.maxValue = v;
  }

  public String getTargetValue() {
    return targetValue;
  }

  public void setTargetValue(String v) {
    this.targetValue = v;
  }

  public String getExpression() {
    return expression;
  }

  public void setExpression(String v) {
    this.expression = v;
  }

  public String getUnit() {
    return unit;
  }

  public void setUnit(String v) {
    this.unit = v;
  }

  public RequirementComparison getComparison() {
    return comparison;
  }

  public void setComparison(RequirementComparison v) {
    this.comparison = v;
  }

  public RequirementJudgmentMode getJudgmentMode() {
    return judgmentMode;
  }

  public void setJudgmentMode(RequirementJudgmentMode v) {
    this.judgmentMode = v;
  }

  public RequirementVerificationStatus getVerificationStatus() {
    return verificationStatus;
  }

  public void setVerificationStatus(RequirementVerificationStatus v) {
    this.verificationStatus = v;
  }

  public String getClause() {
    return clause;
  }

  public void setClause(String v) {
    this.clause = v;
  }

  public Integer getSourcePage() {
    return sourcePage;
  }

  public void setSourcePage(Integer v) {
    this.sourcePage = v;
  }

  public String getSourceHash() {
    return sourceHash;
  }

  public void setSourceHash(String v) {
    this.sourceHash = v;
  }

  public String getBrand() {
    return brand;
  }

  public void setBrand(String v) {
    this.brand = v;
  }

  public String getModel() {
    return model;
  }

  public void setModel(String v) {
    this.model = v;
  }

  public String getGrade() {
    return grade;
  }

  public void setGrade(String v) {
    this.grade = v;
  }

  public String getSpec() {
    return spec;
  }

  public void setSpec(String v) {
    this.spec = v;
  }

  public String getSieve() {
    return sieve;
  }

  public void setSieve(String v) {
    this.sieve = v;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String v) {
    this.remark = v;
  }

  public Integer getSortOrder() {
    return sortOrder;
  }

  public void setSortOrder(Integer v) {
    this.sortOrder = v;
  }

  public String getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(String v) {
    this.createdAt = v;
  }

  public String getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(String v) {
    this.updatedAt = v;
  }
}
