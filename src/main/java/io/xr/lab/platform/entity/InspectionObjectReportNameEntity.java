package io.xr.lab.platform.entity;

import io.xr.lab.platform.entity.enums.ObjectReportNameKey;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

/**
 * V009__init_report_names.sql — 项目↔报告名称 junction（M06.F02/F07）。PK = (object_code, report_name_code)。
 */
@Entity
@Table(name = "inspection_object_report_names")
@IdClass(ObjectReportNameKey.class)
public class InspectionObjectReportNameEntity {

  @Id
  @Column(name = "inspection_object_code", length = 64, nullable = false)
  private String inspectionObjectCode;

  @Id
  @Column(name = "report_name_code", length = 64, nullable = false)
  private String reportNameCode;

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

  public String getReportNameCode() {
    return reportNameCode;
  }

  public void setReportNameCode(String v) {
    this.reportNameCode = v;
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
