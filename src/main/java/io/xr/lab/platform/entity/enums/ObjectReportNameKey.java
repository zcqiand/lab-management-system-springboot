package io.xr.lab.platform.entity.enums;

import java.io.Serializable;
import java.util.Objects;

/** 复合主键 (object_code, report_name_code) — inspection_object_report_names V009。 */
public class ObjectReportNameKey implements Serializable {

  private static final long serialVersionUID = 1L;

  private String inspectionObjectCode;
  private String reportNameCode;

  public ObjectReportNameKey() {}

  public ObjectReportNameKey(String inspectionObjectCode, String reportNameCode) {
    this.inspectionObjectCode = inspectionObjectCode;
    this.reportNameCode = reportNameCode;
  }

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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ObjectReportNameKey k)) {
      return false;
    }
    return Objects.equals(inspectionObjectCode, k.inspectionObjectCode)
        && Objects.equals(reportNameCode, k.reportNameCode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(inspectionObjectCode, reportNameCode);
  }
}
