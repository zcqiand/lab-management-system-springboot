package io.xr.lab.platform.entity.enums;

import io.xr.lab.shared.dto.InspectionStandardRole;
import java.io.Serializable;
import java.util.Objects;

/** 复合主键 (object_code, standard_code, role) — inspection_object_standards V008。 */
public class ObjectStandardKey implements Serializable {

  private static final long serialVersionUID = 1L;

  private String inspectionObjectCode;
  private String inspectionStandardCode;
  private InspectionStandardRole role;

  public ObjectStandardKey() {}

  public ObjectStandardKey(
      String inspectionObjectCode, String inspectionStandardCode, InspectionStandardRole role) {
    this.inspectionObjectCode = inspectionObjectCode;
    this.inspectionStandardCode = inspectionStandardCode;
    this.role = role;
  }

  public String getInspectionObjectCode() {
    return inspectionObjectCode;
  }

  public void setInspectionObjectCode(String v) {
    this.inspectionObjectCode = v;
  }

  public String getInspectionStandardCode() {
    return inspectionStandardCode;
  }

  public void setInspectionStandardCode(String v) {
    this.inspectionStandardCode = v;
  }

  public InspectionStandardRole getRole() {
    return role;
  }

  public void setRole(InspectionStandardRole v) {
    this.role = v;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ObjectStandardKey k)) {
      return false;
    }
    return Objects.equals(inspectionObjectCode, k.inspectionObjectCode)
        && Objects.equals(inspectionStandardCode, k.inspectionStandardCode)
        && role == k.role;
  }

  @Override
  public int hashCode() {
    return Objects.hash(inspectionObjectCode, inspectionStandardCode, role);
  }
}
