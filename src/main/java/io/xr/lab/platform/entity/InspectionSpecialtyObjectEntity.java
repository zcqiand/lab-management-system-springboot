package io.xr.lab.platform.entity;

import io.xr.lab.platform.entity.enums.SpecialtyObjectKey;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

/**
 * V008__init_inspection_dictionary.sql — 专项↔项目 junction（M06.F01/F02）。PK = (specialty_code,
 * object_code)，CASCADE on both FKs。平台级（无 tenant_id）。
 */
@Entity
@Table(name = "inspection_specialty_objects")
@IdClass(SpecialtyObjectKey.class)
public class InspectionSpecialtyObjectEntity {

  @Id
  @Column(name = "inspection_specialty_code", length = 64, nullable = false)
  private String inspectionSpecialtyCode;

  @Id
  @Column(name = "inspection_object_code", length = 64, nullable = false)
  private String inspectionObjectCode;

  @Column(name = "remark")
  private String remark;

  @Column(name = "created_at", nullable = false)
  private String createdAt = "";

  @Column(name = "updated_at", nullable = false)
  private String updatedAt = "";

  public String getInspectionSpecialtyCode() {
    return inspectionSpecialtyCode;
  }

  public void setInspectionSpecialtyCode(String v) {
    this.inspectionSpecialtyCode = v;
  }

  public String getInspectionObjectCode() {
    return inspectionObjectCode;
  }

  public void setInspectionObjectCode(String v) {
    this.inspectionObjectCode = v;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String v) {
    this.remark = v;
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
