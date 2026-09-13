package io.xr.lab.platform.entity.Generated;

import java.io.Serializable;
import java.util.Objects;

/**
 * DB-First 镜像：inspection_report_name_parameters 复合 PK（ADR-0025/ADR-0033）。 由
 * scripts/scaffold-entities.mjs 从 lab_dev 真库反推生成——纯 POJO 镜像， 手写层 @IdClass 见
 * io.xr.lab.platform.entity。
 */
public class InspectionReportNameParametersId implements Serializable {

  private static final long serialVersionUID = 1L;

  private String reportNameCode; // PK 列 report_name_code
  private String inspectionParameterCode; // PK 列 inspection_parameter_code

  public InspectionReportNameParametersId() {}

  public InspectionReportNameParametersId(String reportNameCode, String inspectionParameterCode) {
    this.reportNameCode = reportNameCode;
    this.inspectionParameterCode = inspectionParameterCode;
  }

  public String getReportNameCode() {
    return reportNameCode;
  }

  public void setReportNameCode(String reportNameCode) {
    this.reportNameCode = reportNameCode;
  }

  public String getInspectionParameterCode() {
    return inspectionParameterCode;
  }

  public void setInspectionParameterCode(String inspectionParameterCode) {
    this.inspectionParameterCode = inspectionParameterCode;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof InspectionReportNameParametersId)) return false;
    InspectionReportNameParametersId that = (InspectionReportNameParametersId) o;
    return Objects.equals(reportNameCode, that.reportNameCode)
        && Objects.equals(inspectionParameterCode, that.inspectionParameterCode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(reportNameCode, inspectionParameterCode);
  }
}
