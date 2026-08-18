package io.xr.lab.platform.entity.enums;

import java.io.Serializable;
import java.util.Objects;

/** 复合主键 (object_code, parameter_code) — inspection_object_parameters V008。 */
public class ObjectParameterKey implements Serializable {

  private static final long serialVersionUID = 1L;

  private String inspectionObjectCode;
  private String inspectionParameterCode;

  public ObjectParameterKey() {}

  public ObjectParameterKey(String inspectionObjectCode, String inspectionParameterCode) {
    this.inspectionObjectCode = inspectionObjectCode;
    this.inspectionParameterCode = inspectionParameterCode;
  }

  public String getInspectionObjectCode() {
    return inspectionObjectCode;
  }

  public void setInspectionObjectCode(String v) {
    this.inspectionObjectCode = v;
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
    if (!(o instanceof ObjectParameterKey k)) {
      return false;
    }
    return Objects.equals(inspectionObjectCode, k.inspectionObjectCode)
        && Objects.equals(inspectionParameterCode, k.inspectionParameterCode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(inspectionObjectCode, inspectionParameterCode);
  }
}
