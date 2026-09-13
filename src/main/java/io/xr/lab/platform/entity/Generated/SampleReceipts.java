package io.xr.lab.platform.entity.Generated;

import java.time.OffsetDateTime;

/**
 * DB-First 镜像：sample_receipts（ADR-0025/ADR-0033）。 由 scripts/scaffold-entities.mjs 从 lab_dev
 * 真库反推生成——<b>纯 POJO 镜像， 无 &#64;Entity，不参与运行时</b>；手写运行时 entity 见上级 io.xr.lab.platform.entity（带
 * &#64;Convert 业务枚举，Hibernate ddl-auto=validate 对真库持续校验）。
 *
 * <p>漂移防线：bash scripts/scaffold-entities.sh 对 git diff 检测本目录， DB 真演进时产物变化 → 显式 commit（DB-First
 * 标准工作流）。 字段含义见 lab-management-system-shared/src/db/schema.ts sampleReceipts。
 */
public class SampleReceipts {

  private String id; // PK, NOT NULL
  private String contractId; // NOT NULL
  private String commissionCode; // NOT NULL
  private String commissionDate; // NOT NULL
  private String commissionRegisterCode;
  private String commissionRegisterDate;
  private String categoryCode; // NOT NULL
  private String projectName;
  private String clientUnit;
  private String buildingUnit;
  private String supervisorUnit;
  private String constructionUnit;
  private String witnessUnit;
  private String samplingLocation;
  private String witness;
  private String witnessPhone;
  private String inspector;
  private String inspectorPhone;
  private String receivedBy; // NOT NULL
  private String sampleSource; // NOT NULL
  private String testCategory; // NOT NULL
  private String testEnvironment;
  private String mainEquipment;
  private String testOperator;
  private String testStartDate;
  private String testEndDate;
  private String originalRecordNo;
  private String remark;
  private String judgmentBasis; // jsonb
  private String testingBasis; // jsonb
  private String testParameters; // jsonb
  private String flowStatus; // NOT NULL
  private String flowHistory; // NOT NULL, jsonb
  private String lastSubmittedBy;
  private String assigneeId;
  private String assigneeName;
  private String plannedTestDate;
  private String reportCode;
  private String reportDate;
  private String conclusion;
  private String result;
  private OffsetDateTime issuedAt; // timestamp with time zone
  private String createdAt; // NOT NULL
  private String updatedAt; // NOT NULL
  private String tenantId; // NOT NULL

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getContractId() {
    return contractId;
  }

  public void setContractId(String contractId) {
    this.contractId = contractId;
  }

  public String getCommissionCode() {
    return commissionCode;
  }

  public void setCommissionCode(String commissionCode) {
    this.commissionCode = commissionCode;
  }

  public String getCommissionDate() {
    return commissionDate;
  }

  public void setCommissionDate(String commissionDate) {
    this.commissionDate = commissionDate;
  }

  public String getCommissionRegisterCode() {
    return commissionRegisterCode;
  }

  public void setCommissionRegisterCode(String commissionRegisterCode) {
    this.commissionRegisterCode = commissionRegisterCode;
  }

  public String getCommissionRegisterDate() {
    return commissionRegisterDate;
  }

  public void setCommissionRegisterDate(String commissionRegisterDate) {
    this.commissionRegisterDate = commissionRegisterDate;
  }

  public String getCategoryCode() {
    return categoryCode;
  }

  public void setCategoryCode(String categoryCode) {
    this.categoryCode = categoryCode;
  }

  public String getProjectName() {
    return projectName;
  }

  public void setProjectName(String projectName) {
    this.projectName = projectName;
  }

  public String getClientUnit() {
    return clientUnit;
  }

  public void setClientUnit(String clientUnit) {
    this.clientUnit = clientUnit;
  }

  public String getBuildingUnit() {
    return buildingUnit;
  }

  public void setBuildingUnit(String buildingUnit) {
    this.buildingUnit = buildingUnit;
  }

  public String getSupervisorUnit() {
    return supervisorUnit;
  }

  public void setSupervisorUnit(String supervisorUnit) {
    this.supervisorUnit = supervisorUnit;
  }

  public String getConstructionUnit() {
    return constructionUnit;
  }

  public void setConstructionUnit(String constructionUnit) {
    this.constructionUnit = constructionUnit;
  }

  public String getWitnessUnit() {
    return witnessUnit;
  }

  public void setWitnessUnit(String witnessUnit) {
    this.witnessUnit = witnessUnit;
  }

  public String getSamplingLocation() {
    return samplingLocation;
  }

  public void setSamplingLocation(String samplingLocation) {
    this.samplingLocation = samplingLocation;
  }

  public String getWitness() {
    return witness;
  }

  public void setWitness(String witness) {
    this.witness = witness;
  }

  public String getWitnessPhone() {
    return witnessPhone;
  }

  public void setWitnessPhone(String witnessPhone) {
    this.witnessPhone = witnessPhone;
  }

  public String getInspector() {
    return inspector;
  }

  public void setInspector(String inspector) {
    this.inspector = inspector;
  }

  public String getInspectorPhone() {
    return inspectorPhone;
  }

  public void setInspectorPhone(String inspectorPhone) {
    this.inspectorPhone = inspectorPhone;
  }

  public String getReceivedBy() {
    return receivedBy;
  }

  public void setReceivedBy(String receivedBy) {
    this.receivedBy = receivedBy;
  }

  public String getSampleSource() {
    return sampleSource;
  }

  public void setSampleSource(String sampleSource) {
    this.sampleSource = sampleSource;
  }

  public String getTestCategory() {
    return testCategory;
  }

  public void setTestCategory(String testCategory) {
    this.testCategory = testCategory;
  }

  public String getTestEnvironment() {
    return testEnvironment;
  }

  public void setTestEnvironment(String testEnvironment) {
    this.testEnvironment = testEnvironment;
  }

  public String getMainEquipment() {
    return mainEquipment;
  }

  public void setMainEquipment(String mainEquipment) {
    this.mainEquipment = mainEquipment;
  }

  public String getTestOperator() {
    return testOperator;
  }

  public void setTestOperator(String testOperator) {
    this.testOperator = testOperator;
  }

  public String getTestStartDate() {
    return testStartDate;
  }

  public void setTestStartDate(String testStartDate) {
    this.testStartDate = testStartDate;
  }

  public String getTestEndDate() {
    return testEndDate;
  }

  public void setTestEndDate(String testEndDate) {
    this.testEndDate = testEndDate;
  }

  public String getOriginalRecordNo() {
    return originalRecordNo;
  }

  public void setOriginalRecordNo(String originalRecordNo) {
    this.originalRecordNo = originalRecordNo;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public String getJudgmentBasis() {
    return judgmentBasis;
  }

  public void setJudgmentBasis(String judgmentBasis) {
    this.judgmentBasis = judgmentBasis;
  }

  public String getTestingBasis() {
    return testingBasis;
  }

  public void setTestingBasis(String testingBasis) {
    this.testingBasis = testingBasis;
  }

  public String getTestParameters() {
    return testParameters;
  }

  public void setTestParameters(String testParameters) {
    this.testParameters = testParameters;
  }

  public String getFlowStatus() {
    return flowStatus;
  }

  public void setFlowStatus(String flowStatus) {
    this.flowStatus = flowStatus;
  }

  public String getFlowHistory() {
    return flowHistory;
  }

  public void setFlowHistory(String flowHistory) {
    this.flowHistory = flowHistory;
  }

  public String getLastSubmittedBy() {
    return lastSubmittedBy;
  }

  public void setLastSubmittedBy(String lastSubmittedBy) {
    this.lastSubmittedBy = lastSubmittedBy;
  }

  public String getAssigneeId() {
    return assigneeId;
  }

  public void setAssigneeId(String assigneeId) {
    this.assigneeId = assigneeId;
  }

  public String getAssigneeName() {
    return assigneeName;
  }

  public void setAssigneeName(String assigneeName) {
    this.assigneeName = assigneeName;
  }

  public String getPlannedTestDate() {
    return plannedTestDate;
  }

  public void setPlannedTestDate(String plannedTestDate) {
    this.plannedTestDate = plannedTestDate;
  }

  public String getReportCode() {
    return reportCode;
  }

  public void setReportCode(String reportCode) {
    this.reportCode = reportCode;
  }

  public String getReportDate() {
    return reportDate;
  }

  public void setReportDate(String reportDate) {
    this.reportDate = reportDate;
  }

  public String getConclusion() {
    return conclusion;
  }

  public void setConclusion(String conclusion) {
    this.conclusion = conclusion;
  }

  public String getResult() {
    return result;
  }

  public void setResult(String result) {
    this.result = result;
  }

  public OffsetDateTime getIssuedAt() {
    return issuedAt;
  }

  public void setIssuedAt(OffsetDateTime issuedAt) {
    this.issuedAt = issuedAt;
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
