package io.xr.lab.platform.entity;

import io.xr.lab.platform.entity.enums.StandardParameterKey;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

/**
 * V008__init_inspection_dictionary.sql — 标准↔参数 junction（M06.F03/F04）。PK = (standard_code,
 * parameter_code)。
 */
@Entity
@Table(name = "inspection_standard_parameters")
@IdClass(StandardParameterKey.class)
public class InspectionStandardParameterEntity {

  @Id
  @Column(name = "inspection_standard_code", length = 128, nullable = false)
  private String inspectionStandardCode;

  @Id
  @Column(name = "inspection_parameter_code", length = 64, nullable = false)
  private String inspectionParameterCode;

  @Column(name = "created_at", nullable = false)
  private String createdAt = "";

  @Column(name = "updated_at", nullable = false)
  private String updatedAt = "";

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

  public String getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(String v) {
    this.createdAt = v;
  }

  public String getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(String v) {
    this.updatedAt = v;
  }
}
