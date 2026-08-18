package io.xr.lab.platform.entity.enums;

import java.io.Serializable;
import java.util.Objects;

/** 复合主键 (standard_code, parameter_code) — inspection_standard_parameters V008。 */
public class StandardParameterKey implements Serializable {

  private static final long serialVersionUID = 1L;

  private String inspectionStandardCode;
  private String inspectionParameterCode;

  public StandardParameterKey() {}

  public StandardParameterKey(String inspectionStandardCode, String inspectionParameterCode) {
    this.inspectionStandardCode = inspectionStandardCode;
    this.inspectionParameterCode = inspectionParameterCode;
  }

  public String getInspectionStandardCode() {
    return inspectionStandardCode;
  }

  public void setInspectionStandardCode(String v) {
    this.inspectionStandardCode = v;
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
    if (!(o instanceof StandardParameterKey k)) {
      return false;
    }
    return Objects.equals(inspectionStandardCode, k.inspectionStandardCode)
        && Objects.equals(inspectionParameterCode, k.inspectionParameterCode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(inspectionStandardCode, inspectionParameterCode);
  }
}
