package io.xr.lab.platform.entity.Generated;

import java.io.Serializable;
import java.util.Objects;

/**
 * DB-First 镜像：inspection_standard_parameters 复合 PK（ADR-0025/ADR-0033）。 由
 * scripts/scaffold-entities.mjs 从 lab_dev 真库反推生成——纯 POJO 镜像， 手写层 @IdClass 见
 * io.xr.lab.platform.entity。
 */
public class InspectionStandardParametersId implements Serializable {

  private static final long serialVersionUID = 1L;

  private String inspectionStandardCode; // PK 列 inspection_standard_code
  private String inspectionParameterCode; // PK 列 inspection_parameter_code

  public InspectionStandardParametersId() {}

  public InspectionStandardParametersId(
      String inspectionStandardCode, String inspectionParameterCode) {
    this.inspectionStandardCode = inspectionStandardCode;
    this.inspectionParameterCode = inspectionParameterCode;
  }

  public String getInspectionStandardCode() {
    return inspectionStandardCode;
  }

  public void setInspectionStandardCode(String inspectionStandardCode) {
    this.inspectionStandardCode = inspectionStandardCode;
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
    if (!(o instanceof InspectionStandardParametersId)) return false;
    InspectionStandardParametersId that = (InspectionStandardParametersId) o;
    return Objects.equals(inspectionStandardCode, that.inspectionStandardCode)
        && Objects.equals(inspectionParameterCode, that.inspectionParameterCode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(inspectionStandardCode, inspectionParameterCode);
  }
}
