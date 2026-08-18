package io.xr.lab.platform.entity;

import java.io.Serializable;
import java.util.Objects;

/** inspection_calculation_rules 复合主键：object + parameter（V009）。平台级（无 tenant_id）。 */
public class CalculationRuleKey implements Serializable {

  private static final long serialVersionUID = 1L;

  private String inspectionObjectCode;

  private String inspectionParameterCode;

  public CalculationRuleKey() {}

  public CalculationRuleKey(String inspectionObjectCode, String inspectionParameterCode) {
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
    if (!(o instanceof CalculationRuleKey that)) {
      return false;
    }
    return Objects.equals(inspectionObjectCode, that.inspectionObjectCode)
        && Objects.equals(inspectionParameterCode, that.inspectionParameterCode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(inspectionObjectCode, inspectionParameterCode);
  }
}
