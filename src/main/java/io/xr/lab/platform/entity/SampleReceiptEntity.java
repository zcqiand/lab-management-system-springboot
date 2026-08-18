package io.xr.lab.platform.entity;

import io.xr.lab.platform.entity.enums.FlowStatusConverter;
import io.xr.lab.platform.entity.enums.ReceiptResultConverter;
import io.xr.lab.shared.dto.FlowStatus;
import io.xr.lab.shared.dto.ReceiptResult;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

/**
 * V002__init_sample_receipts_samples.sql — 接样单（M03.F01/F02/F05-F09）。报告字段已并入。 flow_history 3 个 jsonb
 * 字段 （judgment_basis/testing_basis/test_parameters）走 @JdbcTypeCode(SqlTypes.JSON) 映射字符串。
 */
@Entity
@Table(name = "sample_receipts")
public class SampleReceiptEntity {

  @Id
  @Column(name = "id", length = 64, nullable = false)
  private String id;

  @Column(name = "contract_id", length = 64, nullable = false)
  private String contractId;

  @Column(name = "commission_code", length = 64, nullable = false)
  private String commissionCode;

  @Column(name = "commission_date", nullable = false)
  private String commissionDate;

  @Column(name = "commission_register_code")
  private String commissionRegisterCode;

  @Column(name = "commission_register_date")
  private String commissionRegisterDate;

  @Column(name = "category_code", length = 64, nullable = false)
  private String categoryCode;

  @Column(name = "project_name")
  private String projectName;

  @Column(name = "client_unit")
  private String clientUnit;

  @Column(name = "building_unit")
  private String buildingUnit;

  @Column(name = "supervisor_unit")
  private String supervisorUnit;

  @Column(name = "construction_unit")
  private String constructionUnit;

  @Column(name = "witness_unit")
  private String witnessUnit;

  @Column(name = "sampling_location")
  private String samplingLocation;

  @Column(name = "witness")
  private String witness;

  @Column(name = "witness_phone")
  private String witnessPhone;

  @Column(name = "inspector")
  private String inspector;

  @Column(name = "inspector_phone")
  private String inspectorPhone;

  @Column(name = "received_by", nullable = false)
  private String receivedBy;

  @Column(name = "sample_source", nullable = false)
  private String sampleSource;

  @Column(name = "test_category", nullable = false)
  private String testCategory;

  @Column(name = "test_environment")
  private String testEnvironment;

  @Column(name = "main_equipment")
  private String mainEquipment;

  @Column(name = "test_operator")
  private String testOperator;

  @Column(name = "test_start_date")
  private String testStartDate;

  @Column(name = "test_end_date")
  private String testEndDate;

  @Column(name = "original_record_no")
  private String originalRecordNo;

  @Column(name = "remark")
  private String remark;

  @JdbcTypeCode(SqlTypes.JSON)
  @Column(name = "judgment_basis", columnDefinition = "jsonb")
  private String judgmentBasis;

  @JdbcTypeCode(SqlTypes.JSON)
  @Column(name = "testing_basis", columnDefinition = "jsonb")
  private String testingBasis;

  @JdbcTypeCode(SqlTypes.JSON)
  @Column(name = "test_parameters", columnDefinition = "jsonb")
  private String testParameters;

  @Convert(converter = FlowStatusConverter.class)
  @Column(name = "flow_status", nullable = false)
  private FlowStatus flowStatus;

  @JdbcTypeCode(SqlTypes.JSON)
  @Column(name = "flow_history", columnDefinition = "jsonb", nullable = false)
  private String flowHistory = "[]";

  @Column(name = "last_submitted_by")
  private String lastSubmittedBy;

  @Column(name = "assignee_id")
  private String assigneeId;

  @Column(name = "assignee_name")
  private String assigneeName;

  @Column(name = "planned_test_date")
  private String plannedTestDate;

  @Column(name = "report_code")
  private String reportCode;

  @Column(name = "report_date")
  private String reportDate;

  @Column(name = "conclusion")
  private String conclusion;

  @Convert(converter = ReceiptResultConverter.class)
  @Column(name = "result")
  private ReceiptResult result = ReceiptResult.EMPTY;

  @Column(name = "issued_at")
  private java.time.OffsetDateTime issuedAt;

  @Column(name = "tenant_id", length = 64, nullable = false)
  private String tenantId = "";

  @Column(name = "created_at", nullable = false)
  private String createdAt = "";

  @Column(name = "updated_at", nullable = false)
  private String updatedAt = "";

  public String getId() {
    return id;
  }

  public void setId(String v) {
    this.id = v;
  }

  public String getContractId() {
    return contractId;
  }

  public void setContractId(String v) {
    this.contractId = v;
  }

  public String getCommissionCode() {
    return commissionCode;
  }

  public void setCommissionCode(String v) {
    this.commissionCode = v;
  }

  public String getCommissionDate() {
    return commissionDate;
  }

  public void setCommissionDate(String v) {
    this.commissionDate = v;
  }

  public String getCommissionRegisterCode() {
    return commissionRegisterCode;
  }

  public void setCommissionRegisterCode(String v) {
    this.commissionRegisterCode = v;
  }

  public String getCommissionRegisterDate() {
    return commissionRegisterDate;
  }

  public void setCommissionRegisterDate(String v) {
    this.commissionRegisterDate = v;
  }

  public String getCategoryCode() {
    return categoryCode;
  }

  public void setCategoryCode(String v) {
    this.categoryCode = v;
  }

  public String getProjectName() {
    return projectName;
  }

  public void setProjectName(String v) {
    this.projectName = v;
  }

  public String getClientUnit() {
    return clientUnit;
  }

  public void setClientUnit(String v) {
    this.clientUnit = v;
  }

  public String getBuildingUnit() {
    return buildingUnit;
  }

  public void setBuildingUnit(String v) {
    this.buildingUnit = v;
  }

  public String getSupervisorUnit() {
    return supervisorUnit;
  }

  public void setSupervisorUnit(String v) {
    this.supervisorUnit = v;
  }

  public String getConstructionUnit() {
    return constructionUnit;
  }

  public void setConstructionUnit(String v) {
    this.constructionUnit = v;
  }

  public String getWitnessUnit() {
    return witnessUnit;
  }

  public void setWitnessUnit(String v) {
    this.witnessUnit = v;
  }

  public String getSamplingLocation() {
    return samplingLocation;
  }

  public void setSamplingLocation(String v) {
    this.samplingLocation = v;
  }

  public String getWitness() {
    return witness;
  }

  public void setWitness(String v) {
    this.witness = v;
  }

  public String getWitnessPhone() {
    return witnessPhone;
  }

  public void setWitnessPhone(String v) {
    this.witnessPhone = v;
  }

  public String getInspector() {
    return inspector;
  }

  public void setInspector(String v) {
    this.inspector = v;
  }

  public String getInspectorPhone() {
    return inspectorPhone;
  }

  public void setInspectorPhone(String v) {
    this.inspectorPhone = v;
  }

  public String getReceivedBy() {
    return receivedBy;
  }

  public void setReceivedBy(String v) {
    this.receivedBy = v;
  }

  public String getSampleSource() {
    return sampleSource;
  }

  public void setSampleSource(String v) {
    this.sampleSource = v;
  }

  public String getTestCategory() {
    return testCategory;
  }

  public void setTestCategory(String v) {
    this.testCategory = v;
  }

  public String getTestEnvironment() {
    return testEnvironment;
  }

  public void setTestEnvironment(String v) {
    this.testEnvironment = v;
  }

  public String getMainEquipment() {
    return mainEquipment;
  }

  public void setMainEquipment(String v) {
    this.mainEquipment = v;
  }

  public String getTestOperator() {
    return testOperator;
  }

  public void setTestOperator(String v) {
    this.testOperator = v;
  }

  public String getTestStartDate() {
    return testStartDate;
  }

  public void setTestStartDate(String v) {
    this.testStartDate = v;
  }

  public String getTestEndDate() {
    return testEndDate;
  }

  public void setTestEndDate(String v) {
    this.testEndDate = v;
  }

  public String getOriginalRecordNo() {
    return originalRecordNo;
  }

  public void setOriginalRecordNo(String v) {
    this.originalRecordNo = v;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String v) {
    this.remark = v;
  }

  public String getJudgmentBasis() {
    return judgmentBasis;
  }

  public void setJudgmentBasis(String v) {
    this.judgmentBasis = v;
  }

  public String getTestingBasis() {
    return testingBasis;
  }

  public void setTestingBasis(String v) {
    this.testingBasis = v;
  }

  public String getTestParameters() {
    return testParameters;
  }

  public void setTestParameters(String v) {
    this.testParameters = v;
  }

  public FlowStatus getFlowStatus() {
    return flowStatus;
  }

  public void setFlowStatus(FlowStatus v) {
    this.flowStatus = v;
  }

  public String getFlowHistory() {
    return flowHistory;
  }

  public void setFlowHistory(String v) {
    this.flowHistory = v;
  }

  public String getLastSubmittedBy() {
    return lastSubmittedBy;
  }

  public void setLastSubmittedBy(String v) {
    this.lastSubmittedBy = v;
  }

  public String getAssigneeId() {
    return assigneeId;
  }

  public void setAssigneeId(String v) {
    this.assigneeId = v;
  }

  public String getAssigneeName() {
    return assigneeName;
  }

  public void setAssigneeName(String v) {
    this.assigneeName = v;
  }

  public String getPlannedTestDate() {
    return plannedTestDate;
  }

  public void setPlannedTestDate(String v) {
    this.plannedTestDate = v;
  }

  public String getReportCode() {
    return reportCode;
  }

  public void setReportCode(String v) {
    this.reportCode = v;
  }

  public String getReportDate() {
    return reportDate;
  }

  public void setReportDate(String v) {
    this.reportDate = v;
  }

  public String getConclusion() {
    return conclusion;
  }

  public void setConclusion(String v) {
    this.conclusion = v;
  }

  public ReceiptResult getResult() {
    return result;
  }

  public void setResult(ReceiptResult v) {
    this.result = v;
  }

  public java.time.OffsetDateTime getIssuedAt() {
    return issuedAt;
  }

  public void setIssuedAt(java.time.OffsetDateTime v) {
    this.issuedAt = v;
  }

  public String getTenantId() {
    return tenantId;
  }

  public void setTenantId(String v) {
    this.tenantId = v;
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
