package io.xr.lab.platform.entity;

import java.io.Serializable;
import java.util.Objects;

/**
 * inspection_technical_requirements 复合主键：object + parameter +
 * judgmentStandard（V005）。tenant-scoped（V012 加 tenant_id，但 PK 仍按业务三键保持对象/参数/标准的天然业务唯一性；tenant
 * 通过查询条件过滤）。
 */
public class TechnicalRequirementKey implements Serializable {

  private static final long serialVersionUID = 1L;

  private String tenantId;

  private String inspectionObjectCode;

  private String inspectionParameterCode;

  private String judgmentStandardCode;

  public TechnicalRequirementKey() {}

  public TechnicalRequirementKey(
      String tenantId,
      String inspectionObjectCode,
      String inspectionParameterCode,
      String judgmentStandardCode) {
    this.tenantId = tenantId;
    this.inspectionObjectCode = inspectionObjectCode;
    this.inspectionParameterCode = inspectionParameterCode;
    this.judgmentStandardCode = judgmentStandardCode;
  }

  public String getTenantId() {
    return tenantId;
  }

  public void setTenantId(String v) {
    this.tenantId = v;
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

  public String getJudgmentStandardCode() {
    return judgmentStandardCode;
  }

  public void setJudgmentStandardCode(String v) {
    this.judgmentStandardCode = v;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof TechnicalRequirementKey that)) {
      return false;
    }
    return Objects.equals(tenantId, that.tenantId)
        && Objects.equals(inspectionObjectCode, that.inspectionObjectCode)
        && Objects.equals(inspectionParameterCode, that.inspectionParameterCode)
        && Objects.equals(judgmentStandardCode, that.judgmentStandardCode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        tenantId, inspectionObjectCode, inspectionParameterCode, judgmentStandardCode);
  }
}
