package io.xr.lab.platform.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

/** V004 — 等级码表（M04.F08），结构同 Brand。 */
@Entity
@Table(name = "inspection_grades")
@IdClass(CatalogEntryKey.class)
public class InspectionGradeEntity {

  @Id
  @Column(name = "tenant_id", length = 64, nullable = false)
  private String tenantId = "";

  @Id
  @Column(name = "code", length = 64, nullable = false)
  private String code;

  @Column(name = "inspection_object_code", length = 64)
  private String inspectionObjectCode;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "remark")
  private String remark;

  @Column(name = "sort_order", nullable = false)
  private Integer sortOrder = 0;

  @Column(name = "created_at", nullable = false)
  private String createdAt = "";

  @Column(name = "updated_at", nullable = false)
  private String updatedAt = "";

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getTenantId() {
    return tenantId;
  }

  public void setTenantId(String tenantId) {
    this.tenantId = tenantId;
  }

  public String getInspectionObjectCode() {
    return inspectionObjectCode;
  }

  public void setInspectionObjectCode(String v) {
    this.inspectionObjectCode = v;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public Integer getSortOrder() {
    return sortOrder;
  }

  public void setSortOrder(Integer sortOrder) {
    this.sortOrder = sortOrder;
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
