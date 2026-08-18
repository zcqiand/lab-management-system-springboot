package io.xr.lab.platform.entity.enums;

import java.io.Serializable;
import java.util.Objects;

/** 复合主键 (report_name_code, parameter_code) — inspection_report_name_parameters V009。 */
public class ReportNameParameterKey implements Serializable {

  private static final long serialVersionUID = 1L;

  private String reportNameCode;
  private String inspectionParameterCode;

  public ReportNameParameterKey() {}

  public ReportNameParameterKey(String reportNameCode, String inspectionParameterCode) {
    this.reportNameCode = reportNameCode;
    this.inspectionParameterCode = inspectionParameterCode;
  }

  public String getReportNameCode() {
    return reportNameCode;
  }

  public void setReportNameCode(String v) {
    this.reportNameCode = v;
  }

  public String getInspectionParameterCode() {
    return inspectionParameterCode;
  }

  public void setInspectionParameterCode(String v) {
    this.inspectionParameterCode = v;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ReportNameParameterKey k)) {
      return false;
    }
    return Objects.equals(reportNameCode, k.reportNameCode)
        && Objects.equals(inspectionParameterCode, k.inspectionParameterCode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(reportNameCode, inspectionParameterCode);
  }
}
