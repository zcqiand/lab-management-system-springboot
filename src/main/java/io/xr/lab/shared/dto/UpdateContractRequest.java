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

/** UpdateContractRequest */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    date = "2026-09-02T21:47:39.355598900+08:00[Asia/Shanghai]",
    comments = "Generator version: 7.24.0")
public class UpdateContractRequest {

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String contractCode;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String clientUnit;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String projectName;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String projectLocation;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String constructionUnit;

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

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String witnessUnit;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String witness;

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

  public UpdateContractRequest contractCode(@Nullable String contractCode) {
    this.contractCode = contractCode;
    return this;
  }

  /**
   * Get contractCode
   *
   * @return contractCode
   */
  @Schema(name = "contractCode", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("contractCode")
  public @Nullable String getContractCode() {
    return contractCode;
  }

  @JsonProperty("contractCode")
  public void setContractCode(@Nullable String contractCode) {
    this.contractCode = contractCode;
  }

  public UpdateContractRequest clientUnit(@Nullable String clientUnit) {
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

  public UpdateContractRequest projectName(@Nullable String projectName) {
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

  public UpdateContractRequest projectLocation(@Nullable String projectLocation) {
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

  public UpdateContractRequest constructionUnit(@Nullable String constructionUnit) {
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

  public UpdateContractRequest inspectionSpecialtyCode(@Nullable String inspectionSpecialtyCode) {
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

  public UpdateContractRequest buildingUnit(@Nullable String buildingUnit) {
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

  public UpdateContractRequest supervisorUnit(@Nullable String supervisorUnit) {
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

  public UpdateContractRequest inspectionPerson(@Nullable String inspectionPerson) {
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

  public UpdateContractRequest inspectionPhone(@Nullable String inspectionPhone) {
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

  public UpdateContractRequest witnessUnit(@Nullable String witnessUnit) {
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

  public UpdateContractRequest witness(@Nullable String witness) {
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

  public UpdateContractRequest witnessPhone(@Nullable String witnessPhone) {
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

  public UpdateContractRequest contactPerson(@Nullable String contactPerson) {
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

  public UpdateContractRequest contactPhone(@Nullable String contactPhone) {
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

  public UpdateContractRequest entrustedDate(@Nullable String entrustedDate) {
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

  public UpdateContractRequest status(@Nullable ContractStatus status) {
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
    UpdateContractRequest updateContractRequest = (UpdateContractRequest) o;
    return Objects.equals(this.contractCode, updateContractRequest.contractCode)
        && Objects.equals(this.clientUnit, updateContractRequest.clientUnit)
        && Objects.equals(this.projectName, updateContractRequest.projectName)
        && Objects.equals(this.projectLocation, updateContractRequest.projectLocation)
        && Objects.equals(this.constructionUnit, updateContractRequest.constructionUnit)
        && Objects.equals(
            this.inspectionSpecialtyCode, updateContractRequest.inspectionSpecialtyCode)
        && Objects.equals(this.buildingUnit, updateContractRequest.buildingUnit)
        && Objects.equals(this.supervisorUnit, updateContractRequest.supervisorUnit)
        && Objects.equals(this.inspectionPerson, updateContractRequest.inspectionPerson)
        && Objects.equals(this.inspectionPhone, updateContractRequest.inspectionPhone)
        && Objects.equals(this.witnessUnit, updateContractRequest.witnessUnit)
        && Objects.equals(this.witness, updateContractRequest.witness)
        && Objects.equals(this.witnessPhone, updateContractRequest.witnessPhone)
        && Objects.equals(this.contactPerson, updateContractRequest.contactPerson)
        && Objects.equals(this.contactPhone, updateContractRequest.contactPhone)
        && Objects.equals(this.entrustedDate, updateContractRequest.entrustedDate)
        && Objects.equals(this.status, updateContractRequest.status);
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
    sb.append("class UpdateContractRequest {\n");
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
