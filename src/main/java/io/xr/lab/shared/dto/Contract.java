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

/** Contract */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    date = "2026-09-04T01:01:08.136686300+08:00[Asia/Shanghai]",
    comments = "Generator version: 7.24.0")
public class Contract {

  private String id;

  private String tenantId;

  private String contractCode;

  private String clientUnit;

  private String projectName;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String projectLocation;

  private String constructionUnit;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String inspectionSpecialtyCode;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String buildingUnit;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String supervisorUnit;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String inspectionPerson;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String inspectionPhone;

  private String witnessUnit;

  private String witness;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String witnessPhone;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String contactPerson;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String contactPhone;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String entrustedDate;

  private ContractStatus status;

  private String createdAt;

  private String updatedAt;

  public Contract() {
    super();
  }

  /** Constructor with only required parameters */
  public Contract(
      String id,
      String tenantId,
      String contractCode,
      String clientUnit,
      String projectName,
      String constructionUnit,
      String witnessUnit,
      String witness,
      ContractStatus status,
      String createdAt,
      String updatedAt) {
    this.id = id;
    this.tenantId = tenantId;
    this.contractCode = contractCode;
    this.clientUnit = clientUnit;
    this.projectName = projectName;
    this.constructionUnit = constructionUnit;
    this.witnessUnit = witnessUnit;
    this.witness = witness;
    this.status = status;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public Contract id(String id) {
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

  public Contract tenantId(String tenantId) {
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

  public Contract contractCode(String contractCode) {
    this.contractCode = contractCode;
    return this;
  }

  /**
   * Get contractCode
   *
   * @return contractCode
   */
  @NotNull
  @Schema(name = "contractCode", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("contractCode")
  public String getContractCode() {
    return contractCode;
  }

  @JsonProperty("contractCode")
  public void setContractCode(String contractCode) {
    this.contractCode = contractCode;
  }

  public Contract clientUnit(String clientUnit) {
    this.clientUnit = clientUnit;
    return this;
  }

  /**
   * Get clientUnit
   *
   * @return clientUnit
   */
  @NotNull
  @Schema(name = "clientUnit", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("clientUnit")
  public String getClientUnit() {
    return clientUnit;
  }

  @JsonProperty("clientUnit")
  public void setClientUnit(String clientUnit) {
    this.clientUnit = clientUnit;
  }

  public Contract projectName(String projectName) {
    this.projectName = projectName;
    return this;
  }

  /**
   * Get projectName
   *
   * @return projectName
   */
  @NotNull
  @Schema(name = "projectName", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("projectName")
  public String getProjectName() {
    return projectName;
  }

  @JsonProperty("projectName")
  public void setProjectName(String projectName) {
    this.projectName = projectName;
  }

  public Contract projectLocation(@Nullable String projectLocation) {
    this.projectLocation = projectLocation;
    return this;
  }

  /**
   * Get projectLocation
   *
   * @return projectLocation
   */
  @Schema(name = "projectLocation", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("projectLocation")
  public @Nullable String getProjectLocation() {
    return projectLocation;
  }

  @JsonProperty("projectLocation")
  public void setProjectLocation(@Nullable String projectLocation) {
    this.projectLocation = projectLocation;
  }

  public Contract constructionUnit(String constructionUnit) {
    this.constructionUnit = constructionUnit;
    return this;
  }

  /**
   * Get constructionUnit
   *
   * @return constructionUnit
   */
  @NotNull
  @Schema(name = "constructionUnit", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("constructionUnit")
  public String getConstructionUnit() {
    return constructionUnit;
  }

  @JsonProperty("constructionUnit")
  public void setConstructionUnit(String constructionUnit) {
    this.constructionUnit = constructionUnit;
  }

  public Contract inspectionSpecialtyCode(@Nullable String inspectionSpecialtyCode) {
    this.inspectionSpecialtyCode = inspectionSpecialtyCode;
    return this;
  }

  /**
   * Get inspectionSpecialtyCode
   *
   * @return inspectionSpecialtyCode
   */
  @Schema(name = "inspectionSpecialtyCode", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("inspectionSpecialtyCode")
  public @Nullable String getInspectionSpecialtyCode() {
    return inspectionSpecialtyCode;
  }

  @JsonProperty("inspectionSpecialtyCode")
  public void setInspectionSpecialtyCode(@Nullable String inspectionSpecialtyCode) {
    this.inspectionSpecialtyCode = inspectionSpecialtyCode;
  }

  public Contract buildingUnit(@Nullable String buildingUnit) {
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

  public Contract supervisorUnit(@Nullable String supervisorUnit) {
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

  public Contract inspectionPerson(@Nullable String inspectionPerson) {
    this.inspectionPerson = inspectionPerson;
    return this;
  }

  /**
   * Get inspectionPerson
   *
   * @return inspectionPerson
   */
  @Schema(name = "inspectionPerson", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("inspectionPerson")
  public @Nullable String getInspectionPerson() {
    return inspectionPerson;
  }

  @JsonProperty("inspectionPerson")
  public void setInspectionPerson(@Nullable String inspectionPerson) {
    this.inspectionPerson = inspectionPerson;
  }

  public Contract inspectionPhone(@Nullable String inspectionPhone) {
    this.inspectionPhone = inspectionPhone;
    return this;
  }

  /**
   * Get inspectionPhone
   *
   * @return inspectionPhone
   */
  @Schema(name = "inspectionPhone", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("inspectionPhone")
  public @Nullable String getInspectionPhone() {
    return inspectionPhone;
  }

  @JsonProperty("inspectionPhone")
  public void setInspectionPhone(@Nullable String inspectionPhone) {
    this.inspectionPhone = inspectionPhone;
  }

  public Contract witnessUnit(String witnessUnit) {
    this.witnessUnit = witnessUnit;
    return this;
  }

  /**
   * Get witnessUnit
   *
   * @return witnessUnit
   */
  @NotNull
  @Schema(name = "witnessUnit", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("witnessUnit")
  public String getWitnessUnit() {
    return witnessUnit;
  }

  @JsonProperty("witnessUnit")
  public void setWitnessUnit(String witnessUnit) {
    this.witnessUnit = witnessUnit;
  }

  public Contract witness(String witness) {
    this.witness = witness;
    return this;
  }

  /**
   * Get witness
   *
   * @return witness
   */
  @NotNull
  @Schema(name = "witness", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("witness")
  public String getWitness() {
    return witness;
  }

  @JsonProperty("witness")
  public void setWitness(String witness) {
    this.witness = witness;
  }

  public Contract witnessPhone(@Nullable String witnessPhone) {
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

  public Contract contactPerson(@Nullable String contactPerson) {
    this.contactPerson = contactPerson;
    return this;
  }

  /**
   * Get contactPerson
   *
   * @return contactPerson
   */
  @Schema(name = "contactPerson", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("contactPerson")
  public @Nullable String getContactPerson() {
    return contactPerson;
  }

  @JsonProperty("contactPerson")
  public void setContactPerson(@Nullable String contactPerson) {
    this.contactPerson = contactPerson;
  }

  public Contract contactPhone(@Nullable String contactPhone) {
    this.contactPhone = contactPhone;
    return this;
  }

  /**
   * Get contactPhone
   *
   * @return contactPhone
   */
  @Schema(name = "contactPhone", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("contactPhone")
  public @Nullable String getContactPhone() {
    return contactPhone;
  }

  @JsonProperty("contactPhone")
  public void setContactPhone(@Nullable String contactPhone) {
    this.contactPhone = contactPhone;
  }

  public Contract entrustedDate(@Nullable String entrustedDate) {
    this.entrustedDate = entrustedDate;
    return this;
  }

  /**
   * Get entrustedDate
   *
   * @return entrustedDate
   */
  @Schema(name = "entrustedDate", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("entrustedDate")
  public @Nullable String getEntrustedDate() {
    return entrustedDate;
  }

  @JsonProperty("entrustedDate")
  public void setEntrustedDate(@Nullable String entrustedDate) {
    this.entrustedDate = entrustedDate;
  }

  public Contract status(ContractStatus status) {
    this.status = status;
    return this;
  }

  /**
   * Get status
   *
   * @return status
   */
  @NotNull
  @Valid
  @Schema(name = "status", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("status")
  public ContractStatus getStatus() {
    return status;
  }

  @JsonProperty("status")
  public void setStatus(ContractStatus status) {
    this.status = status;
  }

  public Contract createdAt(String createdAt) {
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

  public Contract updatedAt(String updatedAt) {
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
    Contract contract = (Contract) o;
    return Objects.equals(this.id, contract.id)
        && Objects.equals(this.tenantId, contract.tenantId)
        && Objects.equals(this.contractCode, contract.contractCode)
        && Objects.equals(this.clientUnit, contract.clientUnit)
        && Objects.equals(this.projectName, contract.projectName)
        && Objects.equals(this.projectLocation, contract.projectLocation)
        && Objects.equals(this.constructionUnit, contract.constructionUnit)
        && Objects.equals(this.inspectionSpecialtyCode, contract.inspectionSpecialtyCode)
        && Objects.equals(this.buildingUnit, contract.buildingUnit)
        && Objects.equals(this.supervisorUnit, contract.supervisorUnit)
        && Objects.equals(this.inspectionPerson, contract.inspectionPerson)
        && Objects.equals(this.inspectionPhone, contract.inspectionPhone)
        && Objects.equals(this.witnessUnit, contract.witnessUnit)
        && Objects.equals(this.witness, contract.witness)
        && Objects.equals(this.witnessPhone, contract.witnessPhone)
        && Objects.equals(this.contactPerson, contract.contactPerson)
        && Objects.equals(this.contactPhone, contract.contactPhone)
        && Objects.equals(this.entrustedDate, contract.entrustedDate)
        && Objects.equals(this.status, contract.status)
        && Objects.equals(this.createdAt, contract.createdAt)
        && Objects.equals(this.updatedAt, contract.updatedAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        id,
        tenantId,
        contractCode,
        clientUnit,
        projectName,
        projectLocation,
        constructionUnit,
        inspectionSpecialtyCode,
        buildingUnit,
        supervisorUnit,
        inspectionPerson,
        inspectionPhone,
        witnessUnit,
        witness,
        witnessPhone,
        contactPerson,
        contactPhone,
        entrustedDate,
        status,
        createdAt,
        updatedAt);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Contract {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    tenantId: ").append(toIndentedString(tenantId)).append("\n");
    sb.append("    contractCode: ").append(toIndentedString(contractCode)).append("\n");
    sb.append("    clientUnit: ").append(toIndentedString(clientUnit)).append("\n");
    sb.append("    projectName: ").append(toIndentedString(projectName)).append("\n");
    sb.append("    projectLocation: ").append(toIndentedString(projectLocation)).append("\n");
    sb.append("    constructionUnit: ").append(toIndentedString(constructionUnit)).append("\n");
    sb.append("    inspectionSpecialtyCode: ")
        .append(toIndentedString(inspectionSpecialtyCode))
        .append("\n");
    sb.append("    buildingUnit: ").append(toIndentedString(buildingUnit)).append("\n");
    sb.append("    supervisorUnit: ").append(toIndentedString(supervisorUnit)).append("\n");
    sb.append("    inspectionPerson: ").append(toIndentedString(inspectionPerson)).append("\n");
    sb.append("    inspectionPhone: ").append(toIndentedString(inspectionPhone)).append("\n");
    sb.append("    witnessUnit: ").append(toIndentedString(witnessUnit)).append("\n");
    sb.append("    witness: ").append(toIndentedString(witness)).append("\n");
    sb.append("    witnessPhone: ").append(toIndentedString(witnessPhone)).append("\n");
    sb.append("    contactPerson: ").append(toIndentedString(contactPerson)).append("\n");
    sb.append("    contactPhone: ").append(toIndentedString(contactPhone)).append("\n");
    sb.append("    entrustedDate: ").append(toIndentedString(entrustedDate)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
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
