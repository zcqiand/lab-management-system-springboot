package io.xr.lab.platform.entity.enums;

import java.io.Serializable;
import java.util.Objects;

/** 复合主键 (specialty_code, object_code) — inspection_specialty_objects V008。 */
public class SpecialtyObjectKey implements Serializable {

  private static final long serialVersionUID = 1L;

  private String inspectionSpecialtyCode;
  private String inspectionObjectCode;

  public SpecialtyObjectKey() {}

  public SpecialtyObjectKey(String inspectionSpecialtyCode, String inspectionObjectCode) {
    this.inspectionSpecialtyCode = inspectionSpecialtyCode;
    this.inspectionObjectCode = inspectionObjectCode;
  }

  public String getInspectionSpecialtyCode() {
    return inspectionSpecialtyCode;
  }

  public void setInspectionSpecialtyCode(String v) {
    this.inspectionSpecialtyCode = v;
  }

  public String getInspectionObjectCode() {
    return inspectionObjectCode;
  }

  public void setInspectionObjectCode(String v) {
    this.inspectionObjectCode = v;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof SpecialtyObjectKey k)) {
      return false;
    }
    return Objects.equals(inspectionSpecialtyCode, k.inspectionSpecialtyCode)
        && Objects.equals(inspectionObjectCode, k.inspectionObjectCode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(inspectionSpecialtyCode, inspectionObjectCode);
  }
}
