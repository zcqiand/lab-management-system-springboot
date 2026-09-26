package io.xr.lab.platform.entity.Generated;

/**
 * DB-First 镜像：samples（ADR-0025/ADR-0033）。 由 scripts/scaffold-entities.mjs 从 lab_dev 真库反推生成——<b>纯
 * POJO 镜像， 无 &#64;Entity，不参与运行时</b>；手写运行时 entity 见上级 io.xr.lab.platform.entity（带 &#64;Convert
 * 业务枚举，Hibernate ddl-auto=validate 对真库持续校验）。
 *
 * <p>漂移防线：bash scripts/scaffold-entities.sh 对 git diff 检测本目录， DB 真演进时产物变化 → 显式 commit（DB-First
 * 标准工作流）。 字段含义见 lab-management-system-shared/src/db/schema.ts samples。
 */
public class Samples {

  private String id; // PK, NOT NULL
  private String receiptId; // NOT NULL
  private String sampleCode; // NOT NULL
  private String sampleName;
  private String model;
  private String specification;
  private String grade;
  private String brand;
  private String manufacturer;
  private String structuralPart;
  private String representQuantity;
  private String sampleQuantity;
  private String batchNumber;
  private String supplyUnit;
  private String arrivalDate;
  private String samplingDate;
  private String curingCondition;
  private String age;
  private String ext; // NOT NULL, jsonb
  private String remark;
  private String createdAt; // NOT NULL
  private String updatedAt; // NOT NULL
  private String tenantId; // NOT NULL

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getReceiptId() {
    return receiptId;
  }

  public void setReceiptId(String receiptId) {
    this.receiptId = receiptId;
  }

  public String getSampleCode() {
    return sampleCode;
  }

  public void setSampleCode(String sampleCode) {
    this.sampleCode = sampleCode;
  }

  public String getSampleName() {
    return sampleName;
  }

  public void setSampleName(String sampleName) {
    this.sampleName = sampleName;
  }

  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  public String getSpecification() {
    return specification;
  }

  public void setSpecification(String specification) {
    this.specification = specification;
  }

  public String getGrade() {
    return grade;
  }

  public void setGrade(String grade) {
    this.grade = grade;
  }

  public String getBrand() {
    return brand;
  }

  public void setBrand(String brand) {
    this.brand = brand;
  }

  public String getManufacturer() {
    return manufacturer;
  }

  public void setManufacturer(String manufacturer) {
    this.manufacturer = manufacturer;
  }

  public String getStructuralPart() {
    return structuralPart;
  }

  public void setStructuralPart(String structuralPart) {
    this.structuralPart = structuralPart;
  }

  public String getRepresentQuantity() {
    return representQuantity;
  }

  public void setRepresentQuantity(String representQuantity) {
    this.representQuantity = representQuantity;
  }

  public String getSampleQuantity() {
    return sampleQuantity;
  }

  public void setSampleQuantity(String sampleQuantity) {
    this.sampleQuantity = sampleQuantity;
  }

  public String getBatchNumber() {
    return batchNumber;
  }

  public void setBatchNumber(String batchNumber) {
    this.batchNumber = batchNumber;
  }

  public String getSupplyUnit() {
    return supplyUnit;
  }

  public void setSupplyUnit(String supplyUnit) {
    this.supplyUnit = supplyUnit;
  }

  public String getArrivalDate() {
    return arrivalDate;
  }

  public void setArrivalDate(String arrivalDate) {
    this.arrivalDate = arrivalDate;
  }

  public String getSamplingDate() {
    return samplingDate;
  }

  public void setSamplingDate(String samplingDate) {
    this.samplingDate = samplingDate;
  }

  public String getCuringCondition() {
    return curingCondition;
  }

  public void setCuringCondition(String curingCondition) {
    this.curingCondition = curingCondition;
  }

  public String getAge() {
    return age;
  }

  public void setAge(String age) {
    this.age = age;
  }

  public String getExt() {
    return ext;
  }

  public void setExt(String ext) {
    this.ext = ext;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
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
