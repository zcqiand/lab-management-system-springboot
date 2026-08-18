package io.xr.lab.platform.entity;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Map;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

/** V002 — 样品（M03.F02/F03）。model/spec/grade/brand 为逻辑字符串引用（M04 码表）。 */
@Entity
@Table(name = "samples")
public class SampleEntity {

  @Id
  @Column(name = "id", length = 64, nullable = false)
  private String id;

  @Column(name = "receipt_id", length = 64, nullable = false)
  private String receiptId;

  @Column(name = "sample_code", length = 64, nullable = false)
  private String sampleCode;

  @Column(name = "sample_name")
  private String sampleName;

  @Column(name = "model")
  private String model;

  @Column(name = "specification")
  private String specification;

  @Column(name = "grade")
  private String grade;

  @Column(name = "brand")
  private String brand;

  @Column(name = "manufacturer")
  private String manufacturer;

  @Column(name = "structural_part")
  private String structuralPart;

  @Column(name = "represent_quantity")
  private String representQuantity;

  @Column(name = "sample_quantity")
  private String sampleQuantity;

  @Column(name = "batch_number")
  private String batchNumber;

  @Column(name = "supply_unit")
  private String supplyUnit;

  @Column(name = "arrival_date")
  private String arrivalDate;

  @Column(name = "sampling_date")
  private String samplingDate;

  @Column(name = "curing_condition")
  private String curingCondition;

  @Column(name = "age")
  private String age;

  @JdbcTypeCode(SqlTypes.JSON)
  @Column(name = "ext", columnDefinition = "jsonb", nullable = false)
  private Map<String, String> ext = new java.util.HashMap<>();

  @Column(name = "remark")
  private String remark;

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

  public String getReceiptId() {
    return receiptId;
  }

  public void setReceiptId(String v) {
    this.receiptId = v;
  }

  public String getSampleCode() {
    return sampleCode;
  }

  public void setSampleCode(String v) {
    this.sampleCode = v;
  }

  public String getSampleName() {
    return sampleName;
  }

  public void setSampleName(String v) {
    this.sampleName = v;
  }

  public String getModel() {
    return model;
  }

  public void setModel(String v) {
    this.model = v;
  }

  public String getSpecification() {
    return specification;
  }

  public void setSpecification(String v) {
    this.specification = v;
  }

  public String getGrade() {
    return grade;
  }

  public void setGrade(String v) {
    this.grade = v;
  }

  public String getBrand() {
    return brand;
  }

  public void setBrand(String v) {
    this.brand = v;
  }

  public String getManufacturer() {
    return manufacturer;
  }

  public void setManufacturer(String v) {
    this.manufacturer = v;
  }

  public String getStructuralPart() {
    return structuralPart;
  }

  public void setStructuralPart(String v) {
    this.structuralPart = v;
  }

  public String getRepresentQuantity() {
    return representQuantity;
  }

  public void setRepresentQuantity(String v) {
    this.representQuantity = v;
  }

  public String getSampleQuantity() {
    return sampleQuantity;
  }

  public void setSampleQuantity(String v) {
    this.sampleQuantity = v;
  }

  public String getBatchNumber() {
    return batchNumber;
  }

  public void setBatchNumber(String v) {
    this.batchNumber = v;
  }

  public String getSupplyUnit() {
    return supplyUnit;
  }

  public void setSupplyUnit(String v) {
    this.supplyUnit = v;
  }

  public String getArrivalDate() {
    return arrivalDate;
  }

  public void setArrivalDate(String v) {
    this.arrivalDate = v;
  }

  public String getSamplingDate() {
    return samplingDate;
  }

  public void setSamplingDate(String v) {
    this.samplingDate = v;
  }

  public String getCuringCondition() {
    return curingCondition;
  }

  public void setCuringCondition(String v) {
    this.curingCondition = v;
  }

  public String getAge() {
    return age;
  }

  public void setAge(String v) {
    this.age = v;
  }

  @SuppressFBWarnings({"EI_EXPOSE_REP", "EI_EXPOSE_REP2"})
  public Map<String, String> getExt() {
    return ext;
  }

  @SuppressFBWarnings("EI_EXPOSE_REP2")
  public void setExt(Map<String, String> v) {
    this.ext = v;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String v) {
    this.remark = v;
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
