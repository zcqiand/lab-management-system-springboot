package io.xr.lab.platform.entity.Generated;

import java.io.Serializable;
import java.util.Objects;

/**
 * DB-First 镜像：inspection_param_interface_links 复合 PK（ADR-0025/ADR-0033）。 由
 * scripts/scaffold-entities.mjs 从 lab_dev 真库反推生成——纯 POJO 镜像， 手写层 @IdClass 见
 * io.xr.lab.platform.entity。
 */
public class InspectionParamInterfaceLinksId implements Serializable {

  private static final long serialVersionUID = 1L;

  private String inspectionParameterCode; // PK 列 inspection_parameter_code
  private String paramInterfaceCode; // PK 列 param_interface_code

  public InspectionParamInterfaceLinksId() {}

  public InspectionParamInterfaceLinksId(
      String inspectionParameterCode, String paramInterfaceCode) {
    this.inspectionParameterCode = inspectionParameterCode;
    this.paramInterfaceCode = paramInterfaceCode;
  }

  public String getInspectionParameterCode() {
    return inspectionParameterCode;
  }

  public void setInspectionParameterCode(String inspectionParameterCode) {
    this.inspectionParameterCode = inspectionParameterCode;
  }

  public String getParamInterfaceCode() {
    return paramInterfaceCode;
  }

  public void setParamInterfaceCode(String paramInterfaceCode) {
    this.paramInterfaceCode = paramInterfaceCode;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof InspectionParamInterfaceLinksId)) return false;
    InspectionParamInterfaceLinksId that = (InspectionParamInterfaceLinksId) o;
    return Objects.equals(inspectionParameterCode, that.inspectionParameterCode)
        && Objects.equals(paramInterfaceCode, that.paramInterfaceCode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(inspectionParameterCode, paramInterfaceCode);
  }
}
