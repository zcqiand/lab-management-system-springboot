package io.xr.lab.shared.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.constraints.*;
import java.util.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.lang.Nullable;

/** UpdateSampleReceiptRequest */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    date = "2026-08-26T12:43:04.549030500+08:00[Asia/Shanghai]",
    comments = "Generator version: 7.24.0")
public class UpdateSampleReceiptRequest {

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String contractId;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String commissionCode;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String commissionDate;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String commissionRegisterCode;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String commissionRegisterDate;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String categoryCode;

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

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String receivedBy;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String sampleSource;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String testCategory;

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

  public UpdateSampleReceiptRequest contractId(@Nullable String contractId) {
    this.contractId = contractId;
    return this;
  }

  /**
   * Get contractId
   *
   * @return contractId
   */
  @Schema(name = "contractId", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("contractId")
  public @Nullable String getContractId() {
    return contractId;
  }

  @JsonProperty("contractId")
  public void setContractId(@Nullable String contractId) {
    this.contractId = contractId;
  }

  public UpdateSampleReceiptRequest commissionCode(@Nullable String commissionCode) {
    this.commissionCode = commissionCode;
    return this;
  }

  /**
   * Get commissionCode
   *
   * @return commissionCode
   */
  @Schema(name = "commissionCode", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("commissionCode")
  public @Nullable String getCommissionCode() {
    return commissionCode;
  }

  @JsonProperty("commissionCode")
  public void setCommissionCode(@Nullable String commissionCode) {
    this.commissionCode = commissionCode;
  }

  public UpdateSampleReceiptRequest commissionDate(@Nullable String commissionDate) {
    this.commissionDate = commissionDate;
    return this;
  }

  /**
   * Get commissionDate
   *
   * @return commissionDate
   */
  @Schema(name = "commissionDate", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("commissionDate")
  public @Nullable String getCommissionDate() {
    return commissionDate;
  }

  @JsonProperty("commissionDate")
  public void setCommissionDate(@Nullable String commissionDate) {
    this.commissionDate = commissionDate;
  }

  public UpdateSampleReceiptRequest commissionRegisterCode(
      @Nullable String commissionRegisterCode) {
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

  public UpdateSampleReceiptRequest commissionRegisterDate(
      @Nullable String commissionRegisterDate) {
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

  public UpdateSampleReceiptRequest categoryCode(@Nullable String categoryCode) {
    this.categoryCode = categoryCode;
    return this;
  }

  /**
   * Get categoryCode
   *
   * @return categoryCode
   */
  @Schema(name = "categoryCode", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("categoryCode")
  public @Nullable String getCategoryCode() {
    return categoryCode;
  }

  @JsonProperty("categoryCode")
  public void setCategoryCode(@Nullable String categoryCode) {
    this.categoryCode = categoryCode;
  }

  public UpdateSampleReceiptRequest projectName(@Nullable String projectName) {
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

  public UpdateSampleReceiptRequest clientUnit(@Nullable String clientUnit) {
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

  public UpdateSampleReceiptRequest buildingUnit(@Nullable String buildingUnit) {
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

  public UpdateSampleReceiptRequest supervisorUnit(@Nullable String supervisorUnit) {
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

  public UpdateSampleReceiptRequest constructionUnit(@Nullable String constructionUnit) {
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

  public UpdateSampleReceiptRequest witnessUnit(@Nullable String witnessUnit) {
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

  public UpdateSampleReceiptRequest samplingLocation(@Nullable String samplingLocation) {
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

  public UpdateSampleReceiptRequest witness(@Nullable String witness) {
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

  public UpdateSampleReceiptRequest witnessPhone(@Nullable String witnessPhone) {
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

  public UpdateSampleReceiptRequest inspector(@Nullable String inspector) {
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

  public UpdateSampleReceiptRequest inspectorPhone(@Nullable String inspectorPhone) {
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

  public UpdateSampleReceiptRequest receivedBy(@Nullable String receivedBy) {
    this.receivedBy = receivedBy;
    return this;
  }

  /**
   * Get receivedBy
   *
   * @return receivedBy
   */
  @Schema(name = "receivedBy", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("receivedBy")
  public @Nullable String getReceivedBy() {
    return receivedBy;
  }

  @JsonProperty("receivedBy")
  public void setReceivedBy(@Nullable String receivedBy) {
    this.receivedBy = receivedBy;
  }

  public UpdateSampleReceiptRequest sampleSource(@Nullable String sampleSource) {
    this.sampleSource = sampleSource;
    return this;
  }

  /**
   * Get sampleSource
   *
   * @return sampleSource
   */
  @Schema(name = "sampleSource", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("sampleSource")
  public @Nullable String getSampleSource() {
    return sampleSource;
  }

  @JsonProperty("sampleSource")
  public void setSampleSource(@Nullable String sampleSource) {
    this.sampleSource = sampleSource;
  }

  public UpdateSampleReceiptRequest testCategory(@Nullable String testCategory) {
    this.testCategory = testCategory;
    return this;
  }

  /**
   * Get testCategory
   *
   * @return testCategory
   */
  @Schema(name = "testCategory", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("testCategory")
  public @Nullable String getTestCategory() {
    return testCategory;
  }

  @JsonProperty("testCategory")
  public void setTestCategory(@Nullable String testCategory) {
    this.testCategory = testCategory;
  }

  public UpdateSampleReceiptRequest testEnvironment(@Nullable String testEnvironment) {
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

  public UpdateSampleReceiptRequest mainEquipment(@Nullable String mainEquipment) {
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

  public UpdateSampleReceiptRequest testOperator(@Nullable String testOperator) {
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

  public UpdateSampleReceiptRequest testStartDate(@Nullable String testStartDate) {
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

  public UpdateSampleReceiptRequest testEndDate(@Nullable String testEndDate) {
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

  public UpdateSampleReceiptRequest originalRecordNo(@Nullable String originalRecordNo) {
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

  public UpdateSampleReceiptRequest remark(@Nullable String remark) {
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

  public UpdateSampleReceiptRequest judgmentBasis(List<String> judgmentBasis) {
    this.judgmentBasis = judgmentBasis;
    return this;
  }

  public UpdateSampleReceiptRequest addJudgmentBasisItem(String judgmentBasisItem) {
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

  public UpdateSampleReceiptRequest testingBasis(List<String> testingBasis) {
    this.testingBasis = testingBasis;
    return this;
  }

  public UpdateSampleReceiptRequest addTestingBasisItem(String testingBasisItem) {
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

  public UpdateSampleReceiptRequest testParameters(List<String> testParameters) {
    this.testParameters = testParameters;
    return this;
  }

  public UpdateSampleReceiptRequest addTestParametersItem(String testParametersItem) {
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UpdateSampleReceiptRequest updateSampleReceiptRequest = (UpdateSampleReceiptRequest) o;
    return Objects.equals(this.contractId, updateSampleReceiptRequest.contractId)
        && Objects.equals(this.commissionCode, updateSampleReceiptRequest.commissionCode)
        && Objects.equals(this.commissionDate, updateSampleReceiptRequest.commissionDate)
        && Objects.equals(
            this.commissionRegisterCode, updateSampleReceiptRequest.commissionRegisterCode)
        && Objects.equals(
            this.commissionRegisterDate, updateSampleReceiptRequest.commissionRegisterDate)
        && Objects.equals(this.categoryCode, updateSampleReceiptRequest.categoryCode)
        && Objects.equals(this.projectName, updateSampleReceiptRequest.projectName)
        && Objects.equals(this.clientUnit, updateSampleReceiptRequest.clientUnit)
        && Objects.equals(this.buildingUnit, updateSampleReceiptRequest.buildingUnit)
        && Objects.equals(this.supervisorUnit, updateSampleReceiptRequest.supervisorUnit)
        && Objects.equals(this.constructionUnit, updateSampleReceiptRequest.constructionUnit)
        && Objects.equals(this.witnessUnit, updateSampleReceiptRequest.witnessUnit)
        && Objects.equals(this.samplingLocation, updateSampleReceiptRequest.samplingLocation)
        && Objects.equals(this.witness, updateSampleReceiptRequest.witness)
        && Objects.equals(this.witnessPhone, updateSampleReceiptRequest.witnessPhone)
        && Objects.equals(this.inspector, updateSampleReceiptRequest.inspector)
        && Objects.equals(this.inspectorPhone, updateSampleReceiptRequest.inspectorPhone)
        && Objects.equals(this.receivedBy, updateSampleReceiptRequest.receivedBy)
        && Objects.equals(this.sampleSource, updateSampleReceiptRequest.sampleSource)
        && Objects.equals(this.testCategory, updateSampleReceiptRequest.testCategory)
        && Objects.equals(this.testEnvironment, updateSampleReceiptRequest.testEnvironment)
        && Objects.equals(this.mainEquipment, updateSampleReceiptRequest.mainEquipment)
        && Objects.equals(this.testOperator, updateSampleReceiptRequest.testOperator)
        && Objects.equals(this.testStartDate, updateSampleReceiptRequest.testStartDate)
        && Objects.equals(this.testEndDate, updateSampleReceiptRequest.testEndDate)
        && Objects.equals(this.originalRecordNo, updateSampleReceiptRequest.originalRecordNo)
        && Objects.equals(this.remark, updateSampleReceiptRequest.remark)
        && Objects.equals(this.judgmentBasis, updateSampleReceiptRequest.judgmentBasis)
        && Objects.equals(this.testingBasis, updateSampleReceiptRequest.testingBasis)
        && Objects.equals(this.testParameters, updateSampleReceiptRequest.testParameters);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
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
        testParameters);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UpdateSampleReceiptRequest {\n");
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
