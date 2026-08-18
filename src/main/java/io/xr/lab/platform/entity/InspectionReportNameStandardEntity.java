package io.xr.lab.platform.entity;

import io.xr.lab.platform.entity.enums.ReportNameStandardKey;
import io.xr.lab.shared.dto.InspectionStandardRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

/** V009 — 报告名称↔标准 junction（role）。PK = (report_name_code, standard_code, role)。 */
@Entity
@Table(name = "inspection_report_name_standards")
@IdClass(ReportNameStandardKey.class)
public class InspectionReportNameStandardEntity {

  @Id
  @Column(name = "report_name_code", length = 64, nullable = false)
  private String reportNameCode;

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

  public String getReportNameCode() {
    return reportNameCode;
  }

  public void setReportNameCode(String v) {
    this.reportNameCode = v;
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
