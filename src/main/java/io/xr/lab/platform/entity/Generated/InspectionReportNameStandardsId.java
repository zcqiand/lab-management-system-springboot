package io.xr.lab.platform.entity.Generated;

import java.io.Serializable;
import java.util.Objects;

/**
 * DB-First 镜像：inspection_report_name_standards 复合 PK（ADR-0025/ADR-0033）。 由
 * scripts/scaffold-entities.mjs 从 lab_dev 真库反推生成——纯 POJO 镜像， 手写层 @IdClass 见
 * io.xr.lab.platform.entity。
 */
public class InspectionReportNameStandardsId implements Serializable {

  private static final long serialVersionUID = 1L;

  private String reportNameCode; // PK 列 report_name_code
  private String inspectionStandardCode; // PK 列 inspection_standard_code
  private String role; // PK 列 role

  public InspectionReportNameStandardsId() {}

  public InspectionReportNameStandardsId(
      String reportNameCode, String inspectionStandardCode, String role) {
    this.reportNameCode = reportNameCode;
    this.inspectionStandardCode = inspectionStandardCode;
    this.role = role;
  }

  public String getReportNameCode() {
    return reportNameCode;
  }

  public void setReportNameCode(String reportNameCode) {
    this.reportNameCode = reportNameCode;
  }

  public String getInspectionStandardCode() {
    return inspectionStandardCode;
  }

  public void setInspectionStandardCode(String inspectionStandardCode) {
    this.inspectionStandardCode = inspectionStandardCode;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof InspectionReportNameStandardsId)) return false;
    InspectionReportNameStandardsId that = (InspectionReportNameStandardsId) o;
    return Objects.equals(reportNameCode, that.reportNameCode)
        && Objects.equals(inspectionStandardCode, that.inspectionStandardCode)
        && Objects.equals(role, that.role);
  }

  @Override
  public int hashCode() {
    return Objects.hash(reportNameCode, inspectionStandardCode, role);
  }
}
