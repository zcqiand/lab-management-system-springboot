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

/** CreateContractRequest */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    date = "2026-08-18T09:31:54.550738400+08:00[Asia/Shanghai]",
    comments = "Generator version: 7.24.0")
public class CreateContractRequest {

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

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable ContractStatus status;

  public CreateContractRequest() {
    super();
  }

  /** Constructor with only required parameters */
  public CreateContractRequest(
      String contractCode,
      String clientUnit,
      String projectName,
      String constructionUnit,
      String witnessUnit,
      String witness) {
    this.contractCode = contractCode;
    this.clientUnit = clientUnit;
    this.projectName = projectName;
    this.constructionUnit = constructionUnit;
    this.witnessUnit = witnessUnit;
    this.witness = witness;
  }

  public CreateContractRequest contractCode(String contractCode) {
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

  public CreateContractRequest clientUnit(String clientUnit) {
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

  public CreateContractRequest projectName(String projectName) {
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

  public CreateContractRequest projectLocation(@Nullable String projectLocation) {
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

  public CreateContractRequest constructionUnit(String constructionUnit) {
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

  public CreateContractRequest inspectionSpecialtyCode(@Nullable String inspectionSpecialtyCode) {
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

  public CreateContractRequest buildingUnit(@Nullable String buildingUnit) {
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

  public CreateContractRequest supervisorUnit(@Nullable String supervisorUnit) {
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

  public CreateContractRequest inspectionPerson(@Nullable String inspectionPerson) {
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

  public CreateContractRequest inspectionPhone(@Nullable String inspectionPhone) {
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

  public CreateContractRequest witnessUnit(String witnessUnit) {
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

  public CreateContractRequest witness(String witness) {
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

  public CreateContractRequest witnessPhone(@Nullable String witnessPhone) {
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

  public CreateContractRequest contactPerson(@Nullable String contactPerson) {
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

  public CreateContractRequest contactPhone(@Nullable String contactPhone) {
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

  public CreateContractRequest entrustedDate(@Nullable String entrustedDate) {
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

  public CreateContractRequest status(@Nullable ContractStatus status) {
    this.status = status;
    return this;
  }

  /**
   * Get status
   *
   * @return status
   */
  @Valid
  @Schema(name = "status", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("status")
  public @Nullable ContractStatus getStatus() {
    return status;
  }

  @JsonProperty("status")
  public void setStatus(@Nullable ContractStatus status) {
    this.status = status;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CreateContractRequest createContractRequest = (CreateContractRequest) o;
    return Objects.equals(this.contractCode, createContractRequest.contractCode)
        && Objects.equals(this.clientUnit, createContractRequest.clientUnit)
        && Objects.equals(this.projectName, createContractRequest.projectName)
        && Objects.equals(this.projectLocation, createContractRequest.projectLocation)
        && Objects.equals(this.constructionUnit, createContractRequest.constructionUnit)
        && Objects.equals(
            this.inspectionSpecialtyCode, createContractRequest.inspectionSpecialtyCode)
        && Objects.equals(this.buildingUnit, createContractRequest.buildingUnit)
        && Objects.equals(this.supervisorUnit, createContractRequest.supervisorUnit)
        && Objects.equals(this.inspectionPerson, createContractRequest.inspectionPerson)
        && Objects.equals(this.inspectionPhone, createContractRequest.inspectionPhone)
        && Objects.equals(this.witnessUnit, createContractRequest.witnessUnit)
        && Objects.equals(this.witness, createContractRequest.witness)
        && Objects.equals(this.witnessPhone, createContractRequest.witnessPhone)
        && Objects.equals(this.contactPerson, createContractRequest.contactPerson)
        && Objects.equals(this.contactPhone, createContractRequest.contactPhone)
        && Objects.equals(this.entrustedDate, createContractRequest.entrustedDate)
        && Objects.equals(this.status, createContractRequest.status);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
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
        status);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreateContractRequest {\n");
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
