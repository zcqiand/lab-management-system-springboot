package io.xr.lab.platform.entity.Generated;

import java.io.Serializable;
import java.util.Objects;

/**
 * DB-First 镜像：inspection_object_standards 复合 PK（ADR-0025/ADR-0033）。 由 scripts/scaffold-entities.mjs
 * 从 lab_dev 真库反推生成——纯 POJO 镜像， 手写层 @IdClass 见 io.xr.lab.platform.entity。
 */
public class InspectionObjectStandardsId implements Serializable {

  private static final long serialVersionUID = 1L;

  private String inspectionObjectCode; // PK 列 inspection_object_code
  private String inspectionStandardCode; // PK 列 inspection_standard_code
  private String role; // PK 列 role

  public InspectionObjectStandardsId() {}

  public InspectionObjectStandardsId(
      String inspectionObjectCode, String inspectionStandardCode, String role) {
    this.inspectionObjectCode = inspectionObjectCode;
    this.inspectionStandardCode = inspectionStandardCode;
    this.role = role;
  }

  public String getInspectionObjectCode() {
    return inspectionObjectCode;
  }

  public void setInspectionObjectCode(String inspectionObjectCode) {
    this.inspectionObjectCode = inspectionObjectCode;
  }

  public String getInspectionStandardCode() {
    return inspectionStandardCode;
  }

  public void setInspectionStandardCode(String inspectionStandardCode) {
    this.inspectionStandardCode = inspectionStandardCode;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof InspectionObjectStandardsId)) return false;
    InspectionObjectStandardsId that = (InspectionObjectStandardsId) o;
    return Objects.equals(inspectionObjectCode, that.inspectionObjectCode)
        && Objects.equals(inspectionStandardCode, that.inspectionStandardCode)
        && Objects.equals(role, that.role);
  }

  @Override
  public int hashCode() {
    return Objects.hash(inspectionObjectCode, inspectionStandardCode, role);
  }
}
