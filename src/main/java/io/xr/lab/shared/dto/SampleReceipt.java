package io.xr.lab.shared.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.lang.Nullable;

/** SampleReceipt */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    date = "2026-09-02T22:35:42.457326500+08:00[Asia/Shanghai]",
    comments = "Generator version: 7.24.0")
public class SampleReceipt {

  private String id;

  private String tenantId;

  private String contractId;

  private String commissionCode;

  private String commissionDate;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String commissionRegisterCode;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String commissionRegisterDate;

  private String categoryCode;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String projectName;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String clientUnit;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String buildingUnit;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String supervisorUnit;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String constructionUnit;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String witnessUnit;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String samplingLocation;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String witness;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String witnessPhone;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String inspector;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String inspectorPhone;

  private String receivedBy;

  private String sampleSource;

  private String testCategory;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String testEnvironment;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String mainEquipment;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String testOperator;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String testStartDate;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String testEndDate;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String originalRecordNo;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String remark;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private List<String> judgmentBasis = new ArrayList<>();

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private List<String> testingBasis = new ArrayList<>();

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private List<String> testParameters = new ArrayList<>();

  private FlowStatus flowStatus;

  private List<@Valid FlowHistoryEntry> flowHistory = new ArrayList<>();

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String lastSubmittedBy;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String assigneeId;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String assigneeName;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String plannedTestDate;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String reportCode;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String reportDate;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String conclusion;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable ReceiptResult result;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String issuedAt;

  private String createdAt;

  private String updatedAt;

  public SampleReceipt() {
    super();
  }

  /** Constructor with only required parameters */
  public SampleReceipt(
      String id,
      String tenantId,
      String contractId,
      String commissionCode,
      String commissionDate,
      String categoryCode,
      String receivedBy,
      String sampleSource,
      String testCategory,
      FlowStatus flowStatus,
      List<@Valid FlowHistoryEntry> flowHistory,
      String createdAt,
      String updatedAt) {
    this.id = id;
    this.tenantId = tenantId;
    this.contractId = contractId;
    this.commissionCode = commissionCode;
    this.commissionDate = commissionDate;
    this.categoryCode = categoryCode;
    this.receivedBy = receivedBy;
    this.sampleSource = sampleSource;
    this.testCategory = testCategory;
    this.flowStatus = flowStatus;
    this.flowHistory = flowHistory;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public SampleReceipt id(String id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   *
   * @return id
   */
  @NotNull
  @Schema(name = "id", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("id")
  public String getId() {
    return id;
  }

  @JsonProperty("id")
  public void setId(String id) {
    this.id = id;
  }

  public SampleReceipt tenantId(String tenantId) {
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

  public SampleReceipt contractId(String contractId) {
    this.contractId = contractId;
    return this;
  }

  /**
   * Get contractId
   *
   * @return contractId
   */
  @NotNull
  @Schema(name = "contractId", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("contractId")
  public String getContractId() {
    return contractId;
  }

  @JsonProperty("contractId")
  public void setContractId(String contractId) {
    this.contractId = contractId;
  }

  public SampleReceipt commissionCode(String commissionCode) {
    this.commissionCode = commissionCode;
    return this;
  }

  /**
   * Get commissionCode
   *
   * @return commissionCode
   */
  @NotNull
  @Schema(name = "commissionCode", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("commissionCode")
  public String getCommissionCode() {
    return commissionCode;
  }

  @JsonProperty("commissionCode")
  public void setCommissionCode(String commissionCode) {
    this.commissionCode = commissionCode;
  }

  public SampleReceipt commissionDate(String commissionDate) {
    this.commissionDate = commissionDate;
    return this;
  }

  /**
   * Get commissionDate
   *
   * @return commissionDate
   */
  @NotNull
  @Schema(name = "commissionDate", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("commissionDate")
  public String getCommissionDate() {
    return commissionDate;
  }

  @JsonProperty("commissionDate")
  public void setCommissionDate(String commissionDate) {
    this.commissionDate = commissionDate;
  }

  public SampleReceipt commissionRegisterCode(@Nullable String commissionRegisterCode) {
    this.commissionRegisterCode = commissionRegisterCode;
    return this;
  }

  /**
   * Get commissionRegisterCode
   *
   * @return commissionRegisterCode
   */
  @Schema(name = "commissionRegisterCode", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("commissionRegisterCode")
  public @Nullable String getCommissionRegisterCode() {
    return commissionRegisterCode;
  }

  @JsonProperty("commissionRegisterCode")
  public void setCommissionRegisterCode(@Nullable String commissionRegisterCode) {
    this.commissionRegisterCode = commissionRegisterCode;
  }

  public SampleReceipt commissionRegisterDate(@Nullable String commissionRegisterDate) {
    this.commissionRegisterDate = commissionRegisterDate;
    return this;
  }

  /**
   * Get commissionRegisterDate
   *
   * @return commissionRegisterDate
   */
  @Schema(name = "commissionRegisterDate", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("commissionRegisterDate")
  public @Nullable String getCommissionRegisterDate() {
    return commissionRegisterDate;
  }

  @JsonProperty("commissionRegisterDate")
  public void setCommissionRegisterDate(@Nullable String commissionRegisterDate) {
    this.commissionRegisterDate = commissionRegisterDate;
  }

  public SampleReceipt categoryCode(String categoryCode) {
    this.categoryCode = categoryCode;
    return this;
  }

  /**
   * Get categoryCode
   *
   * @return categoryCode
   */
  @NotNull
  @Schema(name = "categoryCode", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("categoryCode")
  public String getCategoryCode() {
    return categoryCode;
  }

  @JsonProperty("categoryCode")
  public void setCategoryCode(String categoryCode) {
    this.categoryCode = categoryCode;
  }

  public SampleReceipt projectName(@Nullable String projectName) {
    this.projectName = projectName;
    return this;
  }

  /**
   * Get projectName
   *
   * @return projectName
   */
  @Schema(name = "projectName", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("projectName")
  public @Nullable String getProjectName() {
    return projectName;
  }

  @JsonProperty("projectName")
  public void setProjectName(@Nullable String projectName) {
    this.projectName = projectName;
  }

  public SampleReceipt clientUnit(@Nullable String clientUnit) {
    this.clientUnit = clientUnit;
    return this;
  }

  /**
   * Get clientUnit
   *
   * @return clientUnit
   */
  @Schema(name = "clientUnit", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("clientUnit")
  public @Nullable String getClientUnit() {
    return clientUnit;
  }

  @JsonProperty("clientUnit")
  public void setClientUnit(@Nullable String clientUnit) {
    this.clientUnit = clientUnit;
  }

  public SampleReceipt buildingUnit(@Nullable String buildingUnit) {
    this.buildingUnit = buildingUnit;
    return this;
  }

  /**
   * Get buildingUnit
   *
   * @return buildingUnit
   */
  @Schema(name = "buildingUnit", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("buildingUnit")
  public @Nullable String getBuildingUnit() {
    return buildingUnit;
  }

  @JsonProperty("buildingUnit")
  public void setBuildingUnit(@Nullable String buildingUnit) {
    this.buildingUnit = buildingUnit;
  }

  public SampleReceipt supervisorUnit(@Nullable String supervisorUnit) {
    this.supervisorUnit = supervisorUnit;
    return this;
  }

  /**
   * Get supervisorUnit
   *
   * @return supervisorUnit
   */
  @Schema(name = "supervisorUnit", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("supervisorUnit")
  public @Nullable String getSupervisorUnit() {
    return supervisorUnit;
  }

  @JsonProperty("supervisorUnit")
  public void setSupervisorUnit(@Nullable String supervisorUnit) {
    this.supervisorUnit = supervisorUnit;
  }

  public SampleReceipt constructionUnit(@Nullable String constructionUnit) {
    this.constructionUnit = constructionUnit;
    return this;
  }

  /**
   * Get constructionUnit
   *
   * @return constructionUnit
   */
  @Schema(name = "constructionUnit", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("constructionUnit")
  public @Nullable String getConstructionUnit() {
    return constructionUnit;
  }

  @JsonProperty("constructionUnit")
  public void setConstructionUnit(@Nullable String constructionUnit) {
    this.constructionUnit = constructionUnit;
  }

  public SampleReceipt witnessUnit(@Nullable String witnessUnit) {
    this.witnessUnit = witnessUnit;
    return this;
  }

  /**
   * Get witnessUnit
   *
   * @return witnessUnit
   */
  @Schema(name = "witnessUnit", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("witnessUnit")
  public @Nullable String getWitnessUnit() {
    return witnessUnit;
  }

  @JsonProperty("witnessUnit")
  public void setWitnessUnit(@Nullable String witnessUnit) {
    this.witnessUnit = witnessUnit;
  }

  public SampleReceipt samplingLocation(@Nullable String samplingLocation) {
    this.samplingLocation = samplingLocation;
    return this;
  }

  /**
   * Get samplingLocation
   *
   * @return samplingLocation
   */
  @Schema(name = "samplingLocation", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("samplingLocation")
  public @Nullable String getSamplingLocation() {
    return samplingLocation;
  }

  @JsonProperty("samplingLocation")
  public void setSamplingLocation(@Nullable String samplingLocation) {
    this.samplingLocation = samplingLocation;
  }

  public SampleReceipt witness(@Nullable String witness) {
    this.witness = witness;
    return this;
  }

  /**
   * Get witness
   *
   * @return witness
   */
  @Schema(name = "witness", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("witness")
  public @Nullable String getWitness() {
    return witness;
  }

  @JsonProperty("witness")
  public void setWitness(@Nullable String witness) {
    this.witness = witness;
  }

  public SampleReceipt witnessPhone(@Nullable String witnessPhone) {
    this.witnessPhone = witnessPhone;
    return this;
  }

  /**
   * Get witnessPhone
   *
   * @return witnessPhone
   */
  @Schema(name = "witnessPhone", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("witnessPhone")
  public @Nullable String getWitnessPhone() {
    return witnessPhone;
  }

  @JsonProperty("witnessPhone")
  public void setWitnessPhone(@Nullable String witnessPhone) {
    this.witnessPhone = witnessPhone;
  }

  public SampleReceipt inspector(@Nullable String inspector) {
    this.inspector = inspector;
    return this;
  }

  /**
   * Get inspector
   *
   * @return inspector
   */
  @Schema(name = "inspector", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("inspector")
  public @Nullable String getInspector() {
    return inspector;
  }

  @JsonProperty("inspector")
  public void setInspector(@Nullable String inspector) {
    this.inspector = inspector;
  }

  public SampleReceipt inspectorPhone(@Nullable String inspectorPhone) {
    this.inspectorPhone = inspectorPhone;
    return this;
  }

  /**
   * Get inspectorPhone
   *
   * @return inspectorPhone
   */
  @Schema(name = "inspectorPhone", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("inspectorPhone")
  public @Nullable String getInspectorPhone() {
    return inspectorPhone;
  }

  @JsonProperty("inspectorPhone")
  public void setInspectorPhone(@Nullable String inspectorPhone) {
    this.inspectorPhone = inspectorPhone;
  }

  public SampleReceipt receivedBy(String receivedBy) {
    this.receivedBy = receivedBy;
    return this;
  }

  /**
   * Get receivedBy
   *
   * @return receivedBy
   */
  @NotNull
  @Schema(name = "receivedBy", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("receivedBy")
  public String getReceivedBy() {
    return receivedBy;
  }

  @JsonProperty("receivedBy")
  public void setReceivedBy(String receivedBy) {
    this.receivedBy = receivedBy;
  }

  public SampleReceipt sampleSource(String sampleSource) {
    this.sampleSource = sampleSource;
    return this;
  }

  /**
   * Get sampleSource
   *
   * @return sampleSource
   */
  @NotNull
  @Schema(name = "sampleSource", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("sampleSource")
  public String getSampleSource() {
    return sampleSource;
  }

  @JsonProperty("sampleSource")
  public void setSampleSource(String sampleSource) {
    this.sampleSource = sampleSource;
  }

  public SampleReceipt testCategory(String testCategory) {
    this.testCategory = testCategory;
    return this;
  }

  /**
   * Get testCategory
   *
   * @return testCategory
   */
  @NotNull
  @Schema(name = "testCategory", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("testCategory")
  public String getTestCategory() {
    return testCategory;
  }

  @JsonProperty("testCategory")
  public void setTestCategory(String testCategory) {
    this.testCategory = testCategory;
  }

  public SampleReceipt testEnvironment(@Nullable String testEnvironment) {
    this.testEnvironment = testEnvironment;
    return this;
  }

  /**
   * Get testEnvironment
   *
   * @return testEnvironment
   */
  @Schema(name = "testEnvironment", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("testEnvironment")
  public @Nullable String getTestEnvironment() {
    return testEnvironment;
  }

  @JsonProperty("testEnvironment")
  public void setTestEnvironment(@Nullable String testEnvironment) {
    this.testEnvironment = testEnvironment;
  }

  public SampleReceipt mainEquipment(@Nullable String mainEquipment) {
    this.mainEquipment = mainEquipment;
    return this;
  }

  /**
   * Get mainEquipment
   *
   * @return mainEquipment
   */
  @Schema(name = "mainEquipment", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("mainEquipment")
  public @Nullable String getMainEquipment() {
    return mainEquipment;
  }

  @JsonProperty("mainEquipment")
  public void setMainEquipment(@Nullable String mainEquipment) {
    this.mainEquipment = mainEquipment;
  }

  public SampleReceipt testOperator(@Nullable String testOperator) {
    this.testOperator = testOperator;
    return this;
  }

  /**
   * Get testOperator
   *
   * @return testOperator
   */
  @Schema(name = "testOperator", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("testOperator")
  public @Nullable String getTestOperator() {
    return testOperator;
  }

  @JsonProperty("testOperator")
  public void setTestOperator(@Nullable String testOperator) {
    this.testOperator = testOperator;
  }

  public SampleReceipt testStartDate(@Nullable String testStartDate) {
    this.testStartDate = testStartDate;
    return this;
  }

  /**
   * Get testStartDate
   *
   * @return testStartDate
   */
  @Schema(name = "testStartDate", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("testStartDate")
  public @Nullable String getTestStartDate() {
    return testStartDate;
  }

  @JsonProperty("testStartDate")
  public void setTestStartDate(@Nullable String testStartDate) {
    this.testStartDate = testStartDate;
  }

  public SampleReceipt testEndDate(@Nullable String testEndDate) {
    this.testEndDate = testEndDate;
    return this;
  }

  /**
   * Get testEndDate
   *
   * @return testEndDate
   */
  @Schema(name = "testEndDate", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("testEndDate")
  public @Nullable String getTestEndDate() {
    return testEndDate;
  }

  @JsonProperty("testEndDate")
  public void setTestEndDate(@Nullable String testEndDate) {
    this.testEndDate = testEndDate;
  }

  public SampleReceipt originalRecordNo(@Nullable String originalRecordNo) {
    this.originalRecordNo = originalRecordNo;
    return this;
  }

  /**
   * Get originalRecordNo
   *
   * @return originalRecordNo
   */
  @Schema(name = "originalRecordNo", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("originalRecordNo")
  public @Nullable String getOriginalRecordNo() {
    return originalRecordNo;
  }

  @JsonProperty("originalRecordNo")
  public void setOriginalRecordNo(@Nullable String originalRecordNo) {
    this.originalRecordNo = originalRecordNo;
  }

  public SampleReceipt remark(@Nullable String remark) {
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

  public SampleReceipt judgmentBasis(List<String> judgmentBasis) {
    this.judgmentBasis = judgmentBasis;
    return this;
  }

  public SampleReceipt addJudgmentBasisItem(String judgmentBasisItem) {
    if (this.judgmentBasis == null) {
      this.judgmentBasis = new ArrayList<>();
    }
    this.judgmentBasis.add(judgmentBasisItem);
    return this;
  }

  /**
   * Get judgmentBasis
   *
   * @return judgmentBasis
   */
  @Schema(name = "judgmentBasis", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("judgmentBasis")
  public List<String> getJudgmentBasis() {
    return judgmentBasis;
  }

  @JsonProperty("judgmentBasis")
  public void setJudgmentBasis(List<String> judgmentBasis) {
    this.judgmentBasis = judgmentBasis;
  }

  public SampleReceipt testingBasis(List<String> testingBasis) {
    this.testingBasis = testingBasis;
    return this;
  }

  public SampleReceipt addTestingBasisItem(String testingBasisItem) {
    if (this.testingBasis == null) {
      this.testingBasis = new ArrayList<>();
    }
    this.testingBasis.add(testingBasisItem);
    return this;
  }

  /**
   * Get testingBasis
   *
   * @return testingBasis
   */
  @Schema(name = "testingBasis", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("testingBasis")
  public List<String> getTestingBasis() {
    return testingBasis;
  }

  @JsonProperty("testingBasis")
  public void setTestingBasis(List<String> testingBasis) {
    this.testingBasis = testingBasis;
  }

  public SampleReceipt testParameters(List<String> testParameters) {
    this.testParameters = testParameters;
    return this;
  }

  public SampleReceipt addTestParametersItem(String testParametersItem) {
    if (this.testParameters == null) {
      this.testParameters = new ArrayList<>();
    }
    this.testParameters.add(testParametersItem);
    return this;
  }

  /**
   * Get testParameters
   *
   * @return testParameters
   */
  @Schema(name = "testParameters", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("testParameters")
  public List<String> getTestParameters() {
    return testParameters;
  }

  @JsonProperty("testParameters")
  public void setTestParameters(List<String> testParameters) {
    this.testParameters = testParameters;
  }

  public SampleReceipt flowStatus(FlowStatus flowStatus) {
    this.flowStatus = flowStatus;
    return this;
  }

  /**
   * Get flowStatus
   *
   * @return flowStatus
   */
  @NotNull
  @Valid
  @Schema(name = "flowStatus", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("flowStatus")
  public FlowStatus getFlowStatus() {
    return flowStatus;
  }

  @JsonProperty("flowStatus")
  public void setFlowStatus(FlowStatus flowStatus) {
    this.flowStatus = flowStatus;
  }

  public SampleReceipt flowHistory(List<@Valid FlowHistoryEntry> flowHistory) {
    this.flowHistory = flowHistory;
    return this;
  }

  public SampleReceipt addFlowHistoryItem(FlowHistoryEntry flowHistoryItem) {
    if (this.flowHistory == null) {
      this.flowHistory = new ArrayList<>();
    }
    this.flowHistory.add(flowHistoryItem);
    return this;
  }

  /**
   * Get flowHistory
   *
   * @return flowHistory
   */
  @NotNull
  @Valid
  @Schema(name = "flowHistory", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("flowHistory")
  public List<@Valid FlowHistoryEntry> getFlowHistory() {
    return flowHistory;
  }

  @JsonProperty("flowHistory")
  public void setFlowHistory(List<@Valid FlowHistoryEntry> flowHistory) {
    this.flowHistory = flowHistory;
  }

  public SampleReceipt lastSubmittedBy(@Nullable String lastSubmittedBy) {
    this.lastSubmittedBy = lastSubmittedBy;
    return this;
  }

  /**
   * Get lastSubmittedBy
   *
   * @return lastSubmittedBy
   */
  @Schema(name = "lastSubmittedBy", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("lastSubmittedBy")
  public @Nullable String getLastSubmittedBy() {
    return lastSubmittedBy;
  }

  @JsonProperty("lastSubmittedBy")
  public void setLastSubmittedBy(@Nullable String lastSubmittedBy) {
    this.lastSubmittedBy = lastSubmittedBy;
  }

  public SampleReceipt assigneeId(@Nullable String assigneeId) {
    this.assigneeId = assigneeId;
    return this;
  }

  /**
   * Get assigneeId
   *
   * @return assigneeId
   */
  @Schema(name = "assigneeId", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("assigneeId")
  public @Nullable String getAssigneeId() {
    return assigneeId;
  }

  @JsonProperty("assigneeId")
  public void setAssigneeId(@Nullable String assigneeId) {
    this.assigneeId = assigneeId;
  }

  public SampleReceipt assigneeName(@Nullable String assigneeName) {
    this.assigneeName = assigneeName;
    return this;
  }

  /**
   * Get assigneeName
   *
   * @return assigneeName
   */
  @Schema(name = "assigneeName", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("assigneeName")
  public @Nullable String getAssigneeName() {
    return assigneeName;
  }

  @JsonProperty("assigneeName")
  public void setAssigneeName(@Nullable String assigneeName) {
    this.assigneeName = assigneeName;
  }

  public SampleReceipt plannedTestDate(@Nullable String plannedTestDate) {
    this.plannedTestDate = plannedTestDate;
    return this;
  }

  /**
   * Get plannedTestDate
   *
   * @return plannedTestDate
   */
  @Schema(name = "plannedTestDate", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("plannedTestDate")
  public @Nullable String getPlannedTestDate() {
    return plannedTestDate;
  }

  @JsonProperty("plannedTestDate")
  public void setPlannedTestDate(@Nullable String plannedTestDate) {
    this.plannedTestDate = plannedTestDate;
  }

  public SampleReceipt reportCode(@Nullable String reportCode) {
    this.reportCode = reportCode;
    return this;
  }

  /**
   * Get reportCode
   *
   * @return reportCode
   */
  @Schema(name = "reportCode", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("reportCode")
  public @Nullable String getReportCode() {
    return reportCode;
  }

  @JsonProperty("reportCode")
  public void setReportCode(@Nullable String reportCode) {
    this.reportCode = reportCode;
  }

  public SampleReceipt reportDate(@Nullable String reportDate) {
    this.reportDate = reportDate;
    return this;
  }

  /**
   * Get reportDate
   *
   * @return reportDate
   */
  @Schema(name = "reportDate", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("reportDate")
  public @Nullable String getReportDate() {
    return reportDate;
  }

  @JsonProperty("reportDate")
  public void setReportDate(@Nullable String reportDate) {
    this.reportDate = reportDate;
  }

  public SampleReceipt conclusion(@Nullable String conclusion) {
    this.conclusion = conclusion;
    return this;
  }

  /**
   * Get conclusion
   *
   * @return conclusion
   */
  @Schema(name = "conclusion", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("conclusion")
  public @Nullable String getConclusion() {
    return conclusion;
  }

  @JsonProperty("conclusion")
  public void setConclusion(@Nullable String conclusion) {
    this.conclusion = conclusion;
  }

  public SampleReceipt result(@Nullable ReceiptResult result) {
    this.result = result;
    return this;
  }

  /**
   * Get result
   *
   * @return result
   */
  @Valid
  @Schema(name = "result", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("result")
  public @Nullable ReceiptResult getResult() {
    return result;
  }

  @JsonProperty("result")
  public void setResult(@Nullable ReceiptResult result) {
    this.result = result;
  }

  public SampleReceipt issuedAt(@Nullable String issuedAt) {
    this.issuedAt = issuedAt;
    return this;
  }

  /**
   * Get issuedAt
   *
   * @return issuedAt
   */
  @Schema(name = "issuedAt", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("issuedAt")
  public @Nullable String getIssuedAt() {
    return issuedAt;
  }

  @JsonProperty("issuedAt")
  public void setIssuedAt(@Nullable String issuedAt) {
    this.issuedAt = issuedAt;
  }

  public SampleReceipt createdAt(String createdAt) {
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

  public SampleReceipt updatedAt(String updatedAt) {
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
    SampleReceipt sampleReceipt = (SampleReceipt) o;
    return Objects.equals(this.id, sampleReceipt.id)
        && Objects.equals(this.tenantId, sampleReceipt.tenantId)
        && Objects.equals(this.contractId, sampleReceipt.contractId)
        && Objects.equals(this.commissionCode, sampleReceipt.commissionCode)
        && Objects.equals(this.commissionDate, sampleReceipt.commissionDate)
        && Objects.equals(this.commissionRegisterCode, sampleReceipt.commissionRegisterCode)
        && Objects.equals(this.commissionRegisterDate, sampleReceipt.commissionRegisterDate)
        && Objects.equals(this.categoryCode, sampleReceipt.categoryCode)
        && Objects.equals(this.projectName, sampleReceipt.projectName)
        && Objects.equals(this.clientUnit, sampleReceipt.clientUnit)
        && Objects.equals(this.buildingUnit, sampleReceipt.buildingUnit)
        && Objects.equals(this.supervisorUnit, sampleReceipt.supervisorUnit)
        && Objects.equals(this.constructionUnit, sampleReceipt.constructionUnit)
        && Objects.equals(this.witnessUnit, sampleReceipt.witnessUnit)
        && Objects.equals(this.samplingLocation, sampleReceipt.samplingLocation)
        && Objects.equals(this.witness, sampleReceipt.witness)
        && Objects.equals(this.witnessPhone, sampleReceipt.witnessPhone)
        && Objects.equals(this.inspector, sampleReceipt.inspector)
        && Objects.equals(this.inspectorPhone, sampleReceipt.inspectorPhone)
        && Objects.equals(this.receivedBy, sampleReceipt.receivedBy)
        && Objects.equals(this.sampleSource, sampleReceipt.sampleSource)
        && Objects.equals(this.testCategory, sampleReceipt.testCategory)
        && Objects.equals(this.testEnvironment, sampleReceipt.testEnvironment)
        && Objects.equals(this.mainEquipment, sampleReceipt.mainEquipment)
        && Objects.equals(this.testOperator, sampleReceipt.testOperator)
        && Objects.equals(this.testStartDate, sampleReceipt.testStartDate)
        && Objects.equals(this.testEndDate, sampleReceipt.testEndDate)
        && Objects.equals(this.originalRecordNo, sampleReceipt.originalRecordNo)
        && Objects.equals(this.remark, sampleReceipt.remark)
        && Objects.equals(this.judgmentBasis, sampleReceipt.judgmentBasis)
        && Objects.equals(this.testingBasis, sampleReceipt.testingBasis)
        && Objects.equals(this.testParameters, sampleReceipt.testParameters)
        && Objects.equals(this.flowStatus, sampleReceipt.flowStatus)
        && Objects.equals(this.flowHistory, sampleReceipt.flowHistory)
        && Objects.equals(this.lastSubmittedBy, sampleReceipt.lastSubmittedBy)
        && Objects.equals(this.assigneeId, sampleReceipt.assigneeId)
        && Objects.equals(this.assigneeName, sampleReceipt.assigneeName)
        && Objects.equals(this.plannedTestDate, sampleReceipt.plannedTestDate)
        && Objects.equals(this.reportCode, sampleReceipt.reportCode)
        && Objects.equals(this.reportDate, sampleReceipt.reportDate)
        && Objects.equals(this.conclusion, sampleReceipt.conclusion)
        && Objects.equals(this.result, sampleReceipt.result)
        && Objects.equals(this.issuedAt, sampleReceipt.issuedAt)
        && Objects.equals(this.createdAt, sampleReceipt.createdAt)
        && Objects.equals(this.updatedAt, sampleReceipt.updatedAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        id,
        tenantId,
        contractId,
        commissionCode,
        commissionDate,
        commissionRegisterCode,
        commissionRegisterDate,
        categoryCode,
        projectName,
        clientUnit,
        buildingUnit,
        supervisorUnit,
        constructionUnit,
        witnessUnit,
        samplingLocation,
        witness,
        witnessPhone,
        inspector,
        inspectorPhone,
        receivedBy,
        sampleSource,
        testCategory,
        testEnvironment,
        mainEquipment,
        testOperator,
        testStartDate,
        testEndDate,
        originalRecordNo,
        remark,
        judgmentBasis,
        testingBasis,
        testParameters,
        flowStatus,
        flowHistory,
        lastSubmittedBy,
        assigneeId,
        assigneeName,
        plannedTestDate,
        reportCode,
        reportDate,
        conclusion,
        result,
        issuedAt,
        createdAt,
        updatedAt);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SampleReceipt {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    tenantId: ").append(toIndentedString(tenantId)).append("\n");
    sb.append("    contractId: ").append(toIndentedString(contractId)).append("\n");
    sb.append("    commissionCode: ").append(toIndentedString(commissionCode)).append("\n");
    sb.append("    commissionDate: ").append(toIndentedString(commissionDate)).append("\n");
    sb.append("    commissionRegisterCode: ")
        .append(toIndentedString(commissionRegisterCode))
        .append("\n");
    sb.append("    commissionRegisterDate: ")
        .append(toIndentedString(commissionRegisterDate))
        .append("\n");
    sb.append("    categoryCode: ").append(toIndentedString(categoryCode)).append("\n");
    sb.append("    projectName: ").append(toIndentedString(projectName)).append("\n");
    sb.append("    clientUnit: ").append(toIndentedString(clientUnit)).append("\n");
    sb.append("    buildingUnit: ").append(toIndentedString(buildingUnit)).append("\n");
    sb.append("    supervisorUnit: ").append(toIndentedString(supervisorUnit)).append("\n");
    sb.append("    constructionUnit: ").append(toIndentedString(constructionUnit)).append("\n");
    sb.append("    witnessUnit: ").append(toIndentedString(witnessUnit)).append("\n");
    sb.append("    samplingLocation: ").append(toIndentedString(samplingLocation)).append("\n");
    sb.append("    witness: ").append(toIndentedString(witness)).append("\n");
    sb.append("    witnessPhone: ").append(toIndentedString(witnessPhone)).append("\n");
    sb.append("    inspector: ").append(toIndentedString(inspector)).append("\n");
    sb.append("    inspectorPhone: ").append(toIndentedString(inspectorPhone)).append("\n");
    sb.append("    receivedBy: ").append(toIndentedString(receivedBy)).append("\n");
    sb.append("    sampleSource: ").append(toIndentedString(sampleSource)).append("\n");
    sb.append("    testCategory: ").append(toIndentedString(testCategory)).append("\n");
    sb.append("    testEnvironment: ").append(toIndentedString(testEnvironment)).append("\n");
    sb.append("    mainEquipment: ").append(toIndentedString(mainEquipment)).append("\n");
    sb.append("    testOperator: ").append(toIndentedString(testOperator)).append("\n");
    sb.append("    testStartDate: ").append(toIndentedString(testStartDate)).append("\n");
    sb.append("    testEndDate: ").append(toIndentedString(testEndDate)).append("\n");
    sb.append("    originalRecordNo: ").append(toIndentedString(originalRecordNo)).append("\n");
    sb.append("    remark: ").append(toIndentedString(remark)).append("\n");
    sb.append("    judgmentBasis: ").append(toIndentedString(judgmentBasis)).append("\n");
    sb.append("    testingBasis: ").append(toIndentedString(testingBasis)).append("\n");
    sb.append("    testParameters: ").append(toIndentedString(testParameters)).append("\n");
    sb.append("    flowStatus: ").append(toIndentedString(flowStatus)).append("\n");
    sb.append("    flowHistory: ").append(toIndentedString(flowHistory)).append("\n");
    sb.append("    lastSubmittedBy: ").append(toIndentedString(lastSubmittedBy)).append("\n");
    sb.append("    assigneeId: ").append(toIndentedString(assigneeId)).append("\n");
    sb.append("    assigneeName: ").append(toIndentedString(assigneeName)).append("\n");
    sb.append("    plannedTestDate: ").append(toIndentedString(plannedTestDate)).append("\n");
    sb.append("    reportCode: ").append(toIndentedString(reportCode)).append("\n");
    sb.append("    reportDate: ").append(toIndentedString(reportDate)).append("\n");
    sb.append("    conclusion: ").append(toIndentedString(conclusion)).append("\n");
    sb.append("    result: ").append(toIndentedString(result)).append("\n");
    sb.append("    issuedAt: ").append(toIndentedString(issuedAt)).append("\n");
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
