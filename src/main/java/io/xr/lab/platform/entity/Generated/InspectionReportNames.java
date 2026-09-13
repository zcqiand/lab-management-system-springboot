package io.xr.lab.platform.entity.Generated;

/**
 * DB-First 镜像：inspection_report_names（ADR-0025/ADR-0033）。 由 scripts/scaffold-entities.mjs 从 lab_dev
 * 真库反推生成——<b>纯 POJO 镜像， 无 &#64;Entity，不参与运行时</b>；手写运行时 entity 见上级 io.xr.lab.platform.entity（带
 * &#64;Convert 业务枚举，Hibernate ddl-auto=validate 对真库持续校验）。
 *
 * <p>漂移防线：bash scripts/scaffold-entities.sh 对 git diff 检测本目录， DB 真演进时产物变化 → 显式 commit（DB-First
 * 标准工作流）。 字段含义见 lab-management-system-shared/src/db/schema.ts inspectionReportNames。
 */
public class InspectionReportNames {

  private String code; // PK, NOT NULL
  private String name; // NOT NULL
  private String fullName;
  private String templatePath;
  private String summaryName;
  private String extFields; // jsonb
  private String description;
  private Integer sortOrder; // NOT NULL, integer
  private String createdAt; // NOT NULL
  private String updatedAt; // NOT NULL

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public String getTemplatePath() {
    return templatePath;
  }

  public void setTemplatePath(String templatePath) {
    this.templatePath = templatePath;
  }

  public String getSummaryName() {
    return summaryName;
  }

  public void setSummaryName(String summaryName) {
    this.summaryName = summaryName;
  }

  public String getExtFields() {
    return extFields;
  }

  public void setExtFields(String extFields) {
    this.extFields = extFields;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Integer getSortOrder() {
    return sortOrder;
  }

  public void setSortOrder(Integer sortOrder) {
    this.sortOrder = sortOrder;
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
