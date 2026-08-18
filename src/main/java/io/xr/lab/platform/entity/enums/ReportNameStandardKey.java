package io.xr.lab.platform.entity.enums;

import io.xr.lab.shared.dto.InspectionStandardRole;
import java.io.Serializable;
import java.util.Objects;

/** 复合主键 (report_name_code, standard_code, role) — inspection_report_name_standards V009。 */
public class ReportNameStandardKey implements Serializable {

  private static final long serialVersionUID = 1L;

  private String reportNameCode;
  private String inspectionStandardCode;
  private InspectionStandardRole role;

  public ReportNameStandardKey() {}

  public ReportNameStandardKey(
      String reportNameCode, String inspectionStandardCode, InspectionStandardRole role) {
    this.reportNameCode = reportNameCode;
    this.inspectionStandardCode = inspectionStandardCode;
    this.role = role;
  }

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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ReportNameStandardKey k)) {
      return false;
    }
    return Objects.equals(reportNameCode, k.reportNameCode)
        && Objects.equals(inspectionStandardCode, k.inspectionStandardCode)
        && role == k.role;
  }

  @Override
  public int hashCode() {
    return Objects.hash(reportNameCode, inspectionStandardCode, role);
  }
}
