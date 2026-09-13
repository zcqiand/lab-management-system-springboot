package io.xr.lab.platform.entity.Generated;

/**
 * DB-First 镜像：inspection_calculation_methods（ADR-0025/ADR-0033）。 由 scripts/scaffold-entities.mjs 从
 * lab_dev 真库反推生成——<b>纯 POJO 镜像， 无 &#64;Entity，不参与运行时</b>；手写运行时 entity 见上级
 * io.xr.lab.platform.entity（带 &#64;Convert 业务枚举，Hibernate ddl-auto=validate 对真库持续校验）。
 *
 * <p>漂移防线：bash scripts/scaffold-entities.sh 对 git diff 检测本目录， DB 真演进时产物变化 → 显式 commit（DB-First
 * 标准工作流）。 字段含义见 lab-management-system-shared/src/db/schema.ts inspectionCalculationMethods。 真库复合
 * PK：(inspection_object_code, inspection_parameter_code)——手写层对应 @IdClass。
 */
public class InspectionCalculationMethods {

  private String inspectionObjectCode; // PK, NOT NULL
  private String inspectionParameterCode; // PK, NOT NULL
  private String testingStandardCode;
  private String reportNameCode;
  private String algorithmType; // NOT NULL
  private Integer specimenCount; // NOT NULL, integer
  private String formula;
  private String conditions;
  private String roundingRule;
  private String remark;
  private Integer sortOrder; // NOT NULL, integer
  private String createdAt; // NOT NULL
  private String updatedAt; // NOT NULL

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

  public String getTestingStandardCode() {
    return testingStandardCode;
  }

  public void setTestingStandardCode(String testingStandardCode) {
    this.testingStandardCode = testingStandardCode;
  }

  public String getReportNameCode() {
    return reportNameCode;
  }

  public void setReportNameCode(String reportNameCode) {
    this.reportNameCode = reportNameCode;
  }

  public String getAlgorithmType() {
    return algorithmType;
  }

  public void setAlgorithmType(String algorithmType) {
    this.algorithmType = algorithmType;
  }

  public Integer getSpecimenCount() {
    return specimenCount;
  }

  public void setSpecimenCount(Integer specimenCount) {
    this.specimenCount = specimenCount;
  }

  public String getFormula() {
    return formula;
  }

  public void setFormula(String formula) {
    this.formula = formula;
  }

  public String getConditions() {
    return conditions;
  }

  public void setConditions(String conditions) {
    this.conditions = conditions;
  }

  public String getRoundingRule() {
    return roundingRule;
  }

  public void setRoundingRule(String roundingRule) {
    this.roundingRule = roundingRule;
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
}
