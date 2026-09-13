package io.xr.lab.platform.entity.Generated;

/**
 * DB-First 镜像：inspection_technical_requirements（ADR-0025/ADR-0033）。 由 scripts/scaffold-entities.mjs
 * 从 lab_dev 真库反推生成——<b>纯 POJO 镜像， 无 &#64;Entity，不参与运行时</b>；手写运行时 entity 见上级
 * io.xr.lab.platform.entity（带 &#64;Convert 业务枚举，Hibernate ddl-auto=validate 对真库持续校验）。
 *
 * <p>漂移防线：bash scripts/scaffold-entities.sh 对 git diff 检测本目录， DB 真演进时产物变化 → 显式 commit（DB-First
 * 标准工作流）。 字段含义见 lab-management-system-shared/src/db/schema.ts inspectionTechnicalRequirements。 真库复合
 * PK：(inspection_object_code, inspection_parameter_code, judgment_standard_code)——手写层对应 @IdClass。
 */
public class InspectionTechnicalRequirements {

  private String inspectionObjectCode; // PK, NOT NULL
  private String inspectionParameterCode; // PK, NOT NULL
  private String judgmentStandardCode; // PK, NOT NULL
  private String conditions;
  private String valueType; // NOT NULL
  private Integer minValue; // integer
  private Integer maxValue; // integer
  private String targetValue;
  private String expression;
  private String unit;
  private String comparison; // NOT NULL
  private String judgmentMode; // NOT NULL
  private String verificationStatus; // NOT NULL
  private String clause;
  private Integer sourcePage; // integer
  private String sourceHash;
  private String brand;
  private String model;
  private String grade;
  private String spec;
  private String sieve;
  private String remark;
  private Integer sortOrder; // NOT NULL, integer
  private String createdAt; // NOT NULL
  private String updatedAt; // NOT NULL
  private String tenantId; // NOT NULL

  public String getInspectionObjectCode() {
    return inspectionObjectCode;
  }

  public void setInspectionObjectCode(String inspectionObjectCode) {
    this.inspectionObjectCode = inspectionObjectCode;
  }

  public String getInspectionParameterCode() {
    return inspectionParameterCode;
  }

  public void setInspectionParameterCode(String inspectionParameterCode) {
    this.inspectionParameterCode = inspectionParameterCode;
  }

  public String getJudgmentStandardCode() {
    return judgmentStandardCode;
  }

  public void setJudgmentStandardCode(String judgmentStandardCode) {
    this.judgmentStandardCode = judgmentStandardCode;
  }

  public String getConditions() {
    return conditions;
  }

  public void setConditions(String conditions) {
    this.conditions = conditions;
  }

  public String getValueType() {
    return valueType;
  }

  public void setValueType(String valueType) {
    this.valueType = valueType;
  }

  public Integer getMinValue() {
    return minValue;
  }

  public void setMinValue(Integer minValue) {
    this.minValue = minValue;
  }

  public Integer getMaxValue() {
    return maxValue;
  }

  public void setMaxValue(Integer maxValue) {
    this.maxValue = maxValue;
  }

  public String getTargetValue() {
    return targetValue;
  }

  public void setTargetValue(String targetValue) {
    this.targetValue = targetValue;
  }

  public String getExpression() {
    return expression;
  }

  public void setExpression(String expression) {
    this.expression = expression;
  }

  public String getUnit() {
    return unit;
  }

  public void setUnit(String unit) {
    this.unit = unit;
  }

  public String getComparison() {
    return comparison;
  }

  public void setComparison(String comparison) {
    this.comparison = comparison;
  }

  public String getJudgmentMode() {
    return judgmentMode;
  }

  public void setJudgmentMode(String judgmentMode) {
    this.judgmentMode = judgmentMode;
  }

  public String getVerificationStatus() {
    return verificationStatus;
  }

  public void setVerificationStatus(String verificationStatus) {
    this.verificationStatus = verificationStatus;
  }

  public String getClause() {
    return clause;
  }

  public void setClause(String clause) {
    this.clause = clause;
  }

  public Integer getSourcePage() {
    return sourcePage;
  }

  public void setSourcePage(Integer sourcePage) {
    this.sourcePage = sourcePage;
  }

  public String getSourceHash() {
    return sourceHash;
  }

  public void setSourceHash(String sourceHash) {
    this.sourceHash = sourceHash;
  }

  public String getBrand() {
    return brand;
  }

  public void setBrand(String brand) {
    this.brand = brand;
  }

  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  public String getGrade() {
    return grade;
  }

  public void setGrade(String grade) {
    this.grade = grade;
  }

  public String getSpec() {
    return spec;
  }

  public void setSpec(String spec) {
    this.spec = spec;
  }

  public String getSieve() {
    return sieve;
  }

  public void setSieve(String sieve) {
    this.sieve = sieve;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public Integer getSortOrder() {
    return sortOrder;
  }

  public void setSortOrder(Integer sortOrder) {
    this.sortOrder = sortOrder;
  }

  public String getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
  }

  public String getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(String updatedAt) {
    this.updatedAt = updatedAt;
  }

  public String getTenantId() {
    return tenantId;
  }

  public void setTenantId(String tenantId) {
    this.tenantId = tenantId;
  }
}
