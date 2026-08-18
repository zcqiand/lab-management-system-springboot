package io.xr.lab.platform.entity.enums;

import java.io.Serializable;
import java.util.Objects;

/** 复合主键 (parameter_code, interface_code) — param_interface_links V010。 */
public class ParamInterfaceLinkKey implements Serializable {

  private static final long serialVersionUID = 1L;

  private String inspectionParameterCode;
  private String paramInterfaceCode;

  public ParamInterfaceLinkKey() {}

  public ParamInterfaceLinkKey(String inspectionParameterCode, String paramInterfaceCode) {
    this.inspectionParameterCode = inspectionParameterCode;
    this.paramInterfaceCode = paramInterfaceCode;
  }

  public String getInspectionParameterCode() {
    return inspectionParameterCode;
  }

  public void setInspectionParameterCode(String v) {
    this.inspectionParameterCode = v;
  }

  public String getParamInterfaceCode() {
    return paramInterfaceCode;
  }

  public void setParamInterfaceCode(String v) {
    this.paramInterfaceCode = v;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ParamInterfaceLinkKey k)) {
      return false;
    }
    return Objects.equals(inspectionParameterCode, k.inspectionParameterCode)
        && Objects.equals(paramInterfaceCode, k.paramInterfaceCode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(inspectionParameterCode, paramInterfaceCode);
  }
}
