package io.xr.lab.platform.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * V008__init_inspection_dictionary.sql — 检测专项（M06.F01）。PK = code。平台级字典（无 tenant_id，per V012 备注）。
 */
@Entity
@Table(name = "inspection_specialties")
public class InspectionSpecialtyEntity {

  @Id
  @Column(name = "code", length = 64, nullable = false)
  private String code;

  @Column(name = "official_no", nullable = false)
  private String officialNo;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "is_official", nullable = false)
  private Boolean isOfficial = true;

  @Column(name = "enabled", nullable = false)
  private Boolean enabled = true;

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

  public String getOfficialNo() {
    return officialNo;
  }

  public void setOfficialNo(String v) {
    this.officialNo = v;
  }

  public String getName() {
    return name;
  }

  public void setName(String v) {
    this.name = v;
  }

  public Boolean getIsOfficial() {
    return isOfficial;
  }

  public void setIsOfficial(Boolean v) {
    this.isOfficial = v;
  }

  public Boolean getEnabled() {
    return enabled;
  }

  public void setEnabled(Boolean v) {
    this.enabled = v;
  }

  public Integer getSortOrder() {
    return sortOrder;
  }

  public void setSortOrder(Integer v) {
    this.sortOrder = v;
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
