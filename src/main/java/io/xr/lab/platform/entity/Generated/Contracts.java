package io.xr.lab.platform.entity.Generated;

/**
 * DB-First 镜像：contracts（ADR-0025/ADR-0033）。 由 scripts/scaffold-entities.mjs 从 lab_dev 真库反推生成——<b>纯
 * POJO 镜像， 无 &#64;Entity，不参与运行时</b>；手写运行时 entity 见上级 io.xr.lab.platform.entity（带 &#64;Convert
 * 业务枚举，Hibernate ddl-auto=validate 对真库持续校验）。
 *
 * <p>漂移防线：bash scripts/scaffold-entities.sh 对 git diff 检测本目录， DB 真演进时产物变化 → 显式 commit（DB-First
 * 标准工作流）。 字段含义见 lab-management-system-shared/src/db/schema.ts contracts。
 */
public class Contracts {

  private String id; // PK, NOT NULL
  private String contractCode; // NOT NULL
  private String clientUnit; // NOT NULL
  private String projectName; // NOT NULL
  private String projectLocation;
  private String constructionUnit; // NOT NULL
  private String inspectionSpecialtyCode;
  private String buildingUnit;
  private String supervisorUnit;
  private String inspectionPerson;
  private String inspectionPhone;
  private String witnessUnit; // NOT NULL
  private String witness; // NOT NULL
  private String witnessPhone;
  private String contactPerson;
  private String contactPhone;
  private String entrustedDate;
  private String status; // NOT NULL
  private String createdAt; // NOT NULL
  private String updatedAt; // NOT NULL
  private String tenantId; // NOT NULL

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getContractCode() {
    return contractCode;
  }

  public void setContractCode(String contractCode) {
    this.contractCode = contractCode;
  }

  public String getClientUnit() {
    return clientUnit;
  }

  public void setClientUnit(String clientUnit) {
    this.clientUnit = clientUnit;
  }

  public String getProjectName() {
    return projectName;
  }

  public void setProjectName(String projectName) {
    this.projectName = projectName;
  }

  public String getProjectLocation() {
    return projectLocation;
  }

  public void setProjectLocation(String projectLocation) {
    this.projectLocation = projectLocation;
  }

  public String getConstructionUnit() {
    return constructionUnit;
  }

  public void setConstructionUnit(String constructionUnit) {
    this.constructionUnit = constructionUnit;
  }

  public String getInspectionSpecialtyCode() {
    return inspectionSpecialtyCode;
  }

  public void setInspectionSpecialtyCode(String inspectionSpecialtyCode) {
    this.inspectionSpecialtyCode = inspectionSpecialtyCode;
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

  public String getInspectionPerson() {
    return inspectionPerson;
  }

  public void setInspectionPerson(String inspectionPerson) {
    this.inspectionPerson = inspectionPerson;
  }

  public String getInspectionPhone() {
    return inspectionPhone;
  }

  public void setInspectionPhone(String inspectionPhone) {
    this.inspectionPhone = inspectionPhone;
  }

  public String getWitnessUnit() {
    return witnessUnit;
  }

  public void setWitnessUnit(String witnessUnit) {
    this.witnessUnit = witnessUnit;
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

  public String getContactPerson() {
    return contactPerson;
  }

  public void setContactPerson(String contactPerson) {
    this.contactPerson = contactPerson;
  }

  public String getContactPhone() {
    return contactPhone;
  }

  public void setContactPhone(String contactPhone) {
    this.contactPhone = contactPhone;
  }

  public String getEntrustedDate() {
    return entrustedDate;
  }

  public void setEntrustedDate(String entrustedDate) {
    this.entrustedDate = entrustedDate;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
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
