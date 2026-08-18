package io.xr.lab.platform.entity;

import io.xr.lab.platform.entity.enums.ParamInterfaceLinkKey;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

/** V010 — 参数↔界面 junction（M06.F08）。PK = (parameter_code, interface_code)。config 走 jsonb。 */
@Entity
@Table(name = "param_interface_links")
@IdClass(ParamInterfaceLinkKey.class)
public class ParamInterfaceLinkEntity {

  @Id
  @Column(name = "inspection_parameter_code", length = 64, nullable = false)
  private String inspectionParameterCode;

  @Id
  @Column(name = "param_interface_code", length = 64, nullable = false)
  private String paramInterfaceCode;

  @Column(name = "report_name_code", length = 64)
  private String reportNameCode;

  @JdbcTypeCode(SqlTypes.JSON)
  @Column(name = "config", columnDefinition = "jsonb")
  private String config;

  @Column(name = "created_at", nullable = false)
  private String createdAt = "";

  @Column(name = "updated_at", nullable = false)
  private String updatedAt = "";

  public String getInspectionParameterCode() {
    return inspectionParameterCode;
  }

  public void setInspectionParameterCode(String v) {
    this.inspectionParameterCode = v;
  }

  public String getParamInterfaceCode() {
    return paramInterfaceCode;
  }

  public void setParamInterfaceCode(String v) {
    this.paramInterfaceCode = v;
  }

  public String getReportNameCode() {
    return reportNameCode;
  }

  public void setReportNameCode(String v) {
    this.reportNameCode = v;
  }

  public String getConfig() {
    return config;
  }

  public void setConfig(String v) {
    this.config = v;
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
