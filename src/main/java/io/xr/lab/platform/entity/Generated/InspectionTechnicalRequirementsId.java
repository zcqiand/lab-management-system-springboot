package io.xr.lab.platform.entity.Generated;

import java.io.Serializable;
import java.util.Objects;

/**
 * DB-First 镜像：inspection_technical_requirements 复合 PK（ADR-0025/ADR-0033）。 由
 * scripts/scaffold-entities.mjs 从 lab_dev 真库反推生成——纯 POJO 镜像， 手写层 @IdClass 见
 * io.xr.lab.platform.entity。
 */
public class InspectionTechnicalRequirementsId implements Serializable {

  private static final long serialVersionUID = 1L;

  private String inspectionObjectCode; // PK 列 inspection_object_code
  private String inspectionParameterCode; // PK 列 inspection_parameter_code
  private String judgmentStandardCode; // PK 列 judgment_standard_code

  public InspectionTechnicalRequirementsId() {}

  public InspectionTechnicalRequirementsId(
      String inspectionObjectCode, String inspectionParameterCode, String judgmentStandardCode) {
    this.inspectionObjectCode = inspectionObjectCode;
    this.inspectionParameterCode = inspectionParameterCode;
    this.judgmentStandardCode = judgmentStandardCode;
  }

  public String getInspectionObjectCode() {
    return inspectionObjectCode;
  }

  public void setInspectionObjectCode(String inspectionObjectCode) {
    this.inspectionObjectCode = inspectionObjectCode;
  }

  public String getInspectionParameterCode() {
    return inspectionParameterCode;
  }

  public void setInspectionParameterCode(String inspectionParameterCode) {
    this.inspectionParameterCode = inspectionParameterCode;
  }

  public String getJudgmentStandardCode() {
    return judgmentStandardCode;
  }

  public void setJudgmentStandardCode(String judgmentStandardCode) {
    this.judgmentStandardCode = judgmentStandardCode;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof InspectionTechnicalRequirementsId)) return false;
    InspectionTechnicalRequirementsId that = (InspectionTechnicalRequirementsId) o;
    return Objects.equals(inspectionObjectCode, that.inspectionObjectCode)
        && Objects.equals(inspectionParameterCode, that.inspectionParameterCode)
        && Objects.equals(judgmentStandardCode, that.judgmentStandardCode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(inspectionObjectCode, inspectionParameterCode, judgmentStandardCode);
  }
}
