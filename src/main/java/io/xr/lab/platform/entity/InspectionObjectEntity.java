package io.xr.lab.platform.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * V008__init_inspection_dictionary.sql — 检测项目（M06.F02）。PK = code。平台级字典（无 tenant_id，per V012 备注）。FK
 * inspection_specialty_code → inspection_specialties(code) ON DELETE RESTRICT（V008 约束）。
 */
@Entity
@Table(name = "inspection_objects")
public class InspectionObjectEntity {

  @Id
  @Column(name = "code", length = 64, nullable = false)
  private String code;

  @Column(name = "inspection_specialty_code", length = 64, nullable = false)
  private String inspectionSpecialtyCode;

  @Column(name = "source_project_no", nullable = false)
  private String sourceProjectNo;

  @Column(name = "source_project_name", nullable = false)
  private String sourceProjectName;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "is_optional_for_qualification", nullable = false)
  private Boolean isOptionalForQualification = false;

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

  public String getInspectionSpecialtyCode() {
    return inspectionSpecialtyCode;
  }

  public void setInspectionSpecialtyCode(String v) {
    this.inspectionSpecialtyCode = v;
  }

  public String getSourceProjectNo() {
    return sourceProjectNo;
  }

  public void setSourceProjectNo(String v) {
    this.sourceProjectNo = v;
  }

  public String getSourceProjectName() {
    return sourceProjectName;
  }

  public void setSourceProjectName(String v) {
    this.sourceProjectName = v;
  }

  public String getName() {
    return name;
  }

  public void setName(String v) {
    this.name = v;
  }

  public Boolean getIsOptionalForQualification() {
    return isOptionalForQualification;
  }

  public void setIsOptionalForQualification(Boolean v) {
    this.isOptionalForQualification = v;
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
