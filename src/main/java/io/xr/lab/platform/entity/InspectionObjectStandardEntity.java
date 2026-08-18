package io.xr.lab.platform.entity;

import io.xr.lab.platform.entity.enums.ObjectStandardKey;
import io.xr.lab.shared.dto.InspectionStandardRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

/**
 * V008__init_inspection_dictionary.sql — 项目↔标准 junction（role）。PK = (object_code, standard_code,
 * role)。role 是 PG enum（TESTING/JUDGMENT）大写，{@link Enumerated#STRING} 写常量名。
 */
@Entity
@Table(name = "inspection_object_standards")
@IdClass(ObjectStandardKey.class)
public class InspectionObjectStandardEntity {

  @Id
  @Column(name = "inspection_object_code", length = 64, nullable = false)
  private String inspectionObjectCode;

  @Id
  @Column(name = "inspection_standard_code", length = 128, nullable = false)
  private String inspectionStandardCode;

  @Id
  @Enumerated(EnumType.STRING)
  @Column(name = "role", nullable = false)
  private InspectionStandardRole role;

  @Column(name = "remark")
  private String remark;

  @Column(name = "created_at", nullable = false)
  private String createdAt = "";

  @Column(name = "updated_at", nullable = false)
  private String updatedAt = "";

  public String getInspectionObjectCode() {
    return inspectionObjectCode;
  }

  public void setInspectionObjectCode(String v) {
    this.inspectionObjectCode = v;
  }

  public String getInspectionStandardCode() {
    return inspectionStandardCode;
  }

  public void setInspectionStandardCode(String v) {
    this.inspectionStandardCode = v;
  }

  public InspectionStandardRole getRole() {
    return role;
  }

  public void setRole(InspectionStandardRole v) {
    this.role = v;
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
