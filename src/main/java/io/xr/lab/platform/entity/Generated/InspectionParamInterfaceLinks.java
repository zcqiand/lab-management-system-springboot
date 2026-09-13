package io.xr.lab.platform.entity.Generated;

/**
 * DB-First 镜像：inspection_param_interface_links（ADR-0025/ADR-0033）。 由 scripts/scaffold-entities.mjs
 * 从 lab_dev 真库反推生成——<b>纯 POJO 镜像， 无 &#64;Entity，不参与运行时</b>；手写运行时 entity 见上级
 * io.xr.lab.platform.entity（带 &#64;Convert 业务枚举，Hibernate ddl-auto=validate 对真库持续校验）。
 *
 * <p>漂移防线：bash scripts/scaffold-entities.sh 对 git diff 检测本目录， DB 真演进时产物变化 → 显式 commit（DB-First
 * 标准工作流）。 字段含义见 lab-management-system-shared/src/db/schema.ts inspectionParamInterfaceLinks。 真库复合
 * PK：(inspection_parameter_code, param_interface_code)——手写层对应 @IdClass。
 */
public class InspectionParamInterfaceLinks {

  private String inspectionParameterCode; // PK, NOT NULL
  private String paramInterfaceCode; // PK, NOT NULL
  private String reportNameCode;
  private String config; // jsonb
  private String createdAt; // NOT NULL
  private String updatedAt; // NOT NULL

  public String getInspectionParameterCode() {
    return inspectionParameterCode;
  }

  public void setInspectionParameterCode(String inspectionParameterCode) {
    this.inspectionParameterCode = inspectionParameterCode;
  }

  public String getParamInterfaceCode() {
    return paramInterfaceCode;
  }

  public void setParamInterfaceCode(String paramInterfaceCode) {
    this.paramInterfaceCode = paramInterfaceCode;
  }

  public String getReportNameCode() {
    return reportNameCode;
  }

  public void setReportNameCode(String reportNameCode) {
    this.reportNameCode = reportNameCode;
  }

  public String getConfig() {
    return config;
  }

  public void setConfig(String config) {
    this.config = config;
  }

  public String getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
  }

  public String getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(String updatedAt) {
    this.updatedAt = updatedAt;
  }
}
