package io.xr.lab.platform.entity;

import io.xr.lab.platform.entity.enums.ContractStatusConverter;
import io.xr.lab.shared.dto.ContractStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * V001__init_contracts.sql — 合同/委托（M02.F01）。id 为应用层 uuid 字符串。 status PG enum（contract_status）走
 * AttributeConverter 与 PG enum 字符串互通（B2 已确立模式）。
 */
@Entity
@Table(name = "contracts")
public class ContractEntity {

  @Id
  @Column(name = "id", length = 64, nullable = false)
  private String id;

  @Column(name = "contract_code", length = 64, nullable = false)
  private String contractCode;

  @Column(name = "client_unit", nullable = false)
  private String clientUnit;

  @Column(name = "project_name", nullable = false)
  private String projectName;

  @Column(name = "project_location")
  private String projectLocation;

  @Column(name = "construction_unit", nullable = false)
  private String constructionUnit;

  @Column(name = "inspection_specialty_code", length = 64)
  private String inspectionSpecialtyCode;

  @Column(name = "building_unit")
  private String buildingUnit;

  @Column(name = "supervisor_unit")
  private String supervisorUnit;

  @Column(name = "inspection_person")
  private String inspectionPerson;

  @Column(name = "inspection_phone")
  private String inspectionPhone;

  @Column(name = "witness_unit", nullable = false)
  private String witnessUnit;

  @Column(name = "witness", nullable = false)
  private String witness;

  @Column(name = "witness_phone")
  private String witnessPhone;

  @Column(name = "contact_person")
  private String contactPerson;

  @Column(name = "contact_phone")
  private String contactPhone;

  @Column(name = "entrusted_date")
  private String entrustedDate;

  @Convert(converter = ContractStatusConverter.class)
  @Column(name = "status", nullable = false)
  private ContractStatus status;

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

  public String getContractCode() {
    return contractCode;
  }

  public void setContractCode(String v) {
    this.contractCode = v;
  }

  public String getClientUnit() {
    return clientUnit;
  }

  public void setClientUnit(String v) {
    this.clientUnit = v;
  }

  public String getProjectName() {
    return projectName;
  }

  public void setProjectName(String v) {
    this.projectName = v;
  }

  public String getProjectLocation() {
    return projectLocation;
  }

  public void setProjectLocation(String v) {
    this.projectLocation = v;
  }

  public String getConstructionUnit() {
    return constructionUnit;
  }

  public void setConstructionUnit(String v) {
    this.constructionUnit = v;
  }

  public String getInspectionSpecialtyCode() {
    return inspectionSpecialtyCode;
  }

  public void setInspectionSpecialtyCode(String v) {
    this.inspectionSpecialtyCode = v;
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

  public String getInspectionPerson() {
    return inspectionPerson;
  }

  public void setInspectionPerson(String v) {
    this.inspectionPerson = v;
  }

  public String getInspectionPhone() {
    return inspectionPhone;
  }

  public void setInspectionPhone(String v) {
    this.inspectionPhone = v;
  }

  public String getWitnessUnit() {
    return witnessUnit;
  }

  public void setWitnessUnit(String v) {
    this.witnessUnit = v;
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

  public String getContactPerson() {
    return contactPerson;
  }

  public void setContactPerson(String v) {
    this.contactPerson = v;
  }

  public String getContactPhone() {
    return contactPhone;
  }

  public void setContactPhone(String v) {
    this.contactPhone = v;
  }

  public String getEntrustedDate() {
    return entrustedDate;
  }

  public void setEntrustedDate(String v) {
    this.entrustedDate = v;
  }

  public ContractStatus getStatus() {
    return status;
  }

  public void setStatus(ContractStatus v) {
    this.status = v;
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
