package io.xr.lab.platform.entity;

import io.xr.lab.platform.entity.enums.ObjectParameterKey;
import io.xr.lab.shared.dto.QualificationLevel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

/**
 * V008__init_inspection_dictionary.sql — 项目↔参数 junction（M06.F02/F03）。PK = (object_code,
 * parameter_code)。qualification_level 是 PG enum（QUALIFIED/RESTRICTED），用 {@link Enumerated#STRING}
 * 写常量名（与 PG enum 标签同款大写）。
 */
@Entity
@Table(name = "inspection_object_parameters")
@IdClass(ObjectParameterKey.class)
public class InspectionObjectParameterEntity {

  @Id
  @Column(name = "inspection_object_code", length = 64, nullable = false)
  private String inspectionObjectCode;

  @Id
  @Column(name = "inspection_parameter_code", length = 64, nullable = false)
  private String inspectionParameterCode;

  @Enumerated(EnumType.STRING)
  @Column(name = "qualification_level", nullable = false)
  private QualificationLevel qualificationLevel = QualificationLevel.QUALIFIED;

  @Column(name = "source_page")
  private Integer sourcePage;

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

  public String getInspectionParameterCode() {
    return inspectionParameterCode;
  }

  public void setInspectionParameterCode(String v) {
    this.inspectionParameterCode = v;
  }

  public QualificationLevel getQualificationLevel() {
    return qualificationLevel;
  }

  public void setQualificationLevel(QualificationLevel v) {
    this.qualificationLevel = v;
  }

  public Integer getSourcePage() {
    return sourcePage;
  }

  public void setSourcePage(Integer v) {
    this.sourcePage = v;
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
