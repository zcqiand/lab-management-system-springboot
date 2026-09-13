package io.xr.lab.platform.entity.Generated;

import java.io.Serializable;
import java.util.Objects;

/**
 * DB-First 镜像：inspection_specialty_objects 复合 PK（ADR-0025/ADR-0033）。 由
 * scripts/scaffold-entities.mjs 从 lab_dev 真库反推生成——纯 POJO 镜像， 手写层 @IdClass 见
 * io.xr.lab.platform.entity。
 */
public class InspectionSpecialtyObjectsId implements Serializable {

  private static final long serialVersionUID = 1L;

  private String inspectionSpecialtyCode; // PK 列 inspection_specialty_code
  private String inspectionObjectCode; // PK 列 inspection_object_code

  public InspectionSpecialtyObjectsId() {}

  public InspectionSpecialtyObjectsId(String inspectionSpecialtyCode, String inspectionObjectCode) {
    this.inspectionSpecialtyCode = inspectionSpecialtyCode;
    this.inspectionObjectCode = inspectionObjectCode;
  }

  public String getInspectionSpecialtyCode() {
    return inspectionSpecialtyCode;
  }

  public void setInspectionSpecialtyCode(String inspectionSpecialtyCode) {
    this.inspectionSpecialtyCode = inspectionSpecialtyCode;
  }

  public String getInspectionObjectCode() {
    return inspectionObjectCode;
  }

  public void setInspectionObjectCode(String inspectionObjectCode) {
    this.inspectionObjectCode = inspectionObjectCode;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof InspectionSpecialtyObjectsId)) return false;
    InspectionSpecialtyObjectsId that = (InspectionSpecialtyObjectsId) o;
    return Objects.equals(inspectionSpecialtyCode, that.inspectionSpecialtyCode)
        && Objects.equals(inspectionObjectCode, that.inspectionObjectCode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(inspectionSpecialtyCode, inspectionObjectCode);
  }
}
