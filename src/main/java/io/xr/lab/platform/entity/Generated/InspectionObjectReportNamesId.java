package io.xr.lab.platform.entity.Generated;

import java.io.Serializable;
import java.util.Objects;

/**
 * DB-First 镜像：inspection_object_report_names 复合 PK（ADR-0025/ADR-0033）。 由
 * scripts/scaffold-entities.mjs 从 lab_dev 真库反推生成——纯 POJO 镜像， 手写层 @IdClass 见
 * io.xr.lab.platform.entity。
 */
public class InspectionObjectReportNamesId implements Serializable {

  private static final long serialVersionUID = 1L;

  private String inspectionObjectCode; // PK 列 inspection_object_code
  private String reportNameCode; // PK 列 report_name_code

  public InspectionObjectReportNamesId() {}

  public InspectionObjectReportNamesId(String inspectionObjectCode, String reportNameCode) {
    this.inspectionObjectCode = inspectionObjectCode;
    this.reportNameCode = reportNameCode;
  }

  public String getInspectionObjectCode() {
    return inspectionObjectCode;
  }

  public void setInspectionObjectCode(String inspectionObjectCode) {
    this.inspectionObjectCode = inspectionObjectCode;
  }

  public String getReportNameCode() {
    return reportNameCode;
  }

  public void setReportNameCode(String reportNameCode) {
    this.reportNameCode = reportNameCode;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof InspectionObjectReportNamesId)) return false;
    InspectionObjectReportNamesId that = (InspectionObjectReportNamesId) o;
    return Objects.equals(inspectionObjectCode, that.inspectionObjectCode)
        && Objects.equals(reportNameCode, that.reportNameCode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(inspectionObjectCode, reportNameCode);
  }
}
