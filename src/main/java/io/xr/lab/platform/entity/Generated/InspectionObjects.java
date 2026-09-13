package io.xr.lab.platform.entity.Generated;

/**
 * DB-First 镜像：inspection_objects（ADR-0025/ADR-0033）。 由 scripts/scaffold-entities.mjs 从 lab_dev
 * 真库反推生成——<b>纯 POJO 镜像， 无 &#64;Entity，不参与运行时</b>；手写运行时 entity 见上级 io.xr.lab.platform.entity（带
 * &#64;Convert 业务枚举，Hibernate ddl-auto=validate 对真库持续校验）。
 *
 * <p>漂移防线：bash scripts/scaffold-entities.sh 对 git diff 检测本目录， DB 真演进时产物变化 → 显式 commit（DB-First
 * 标准工作流）。 字段含义见 lab-management-system-shared/src/db/schema.ts inspectionObjects。
 */
public class InspectionObjects {

  private String code; // PK, NOT NULL
  private String inspectionSpecialtyCode; // NOT NULL
  private String sourceProjectNo; // NOT NULL
  private String sourceProjectName; // NOT NULL
  private String name; // NOT NULL
  private Boolean isOptionalForQualification; // NOT NULL, boolean
  private Boolean isOfficial; // NOT NULL, boolean
  private Boolean enabled; // NOT NULL, boolean
  private Integer sortOrder; // NOT NULL, integer
  private String createdAt; // NOT NULL
  private String updatedAt; // NOT NULL

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getInspectionSpecialtyCode() {
    return inspectionSpecialtyCode;
  }

  public void setInspectionSpecialtyCode(String inspectionSpecialtyCode) {
    this.inspectionSpecialtyCode = inspectionSpecialtyCode;
  }

  public String getSourceProjectNo() {
    return sourceProjectNo;
  }

  public void setSourceProjectNo(String sourceProjectNo) {
    this.sourceProjectNo = sourceProjectNo;
  }

  public String getSourceProjectName() {
    return sourceProjectName;
  }

  public void setSourceProjectName(String sourceProjectName) {
    this.sourceProjectName = sourceProjectName;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Boolean getIsOptionalForQualification() {
    return isOptionalForQualification;
  }

  public void setIsOptionalForQualification(Boolean isOptionalForQualification) {
    this.isOptionalForQualification = isOptionalForQualification;
  }

  public Boolean getIsOfficial() {
    return isOfficial;
  }

  public void setIsOfficial(Boolean isOfficial) {
    this.isOfficial = isOfficial;
  }

  public Boolean getEnabled() {
    return enabled;
  }

  public void setEnabled(Boolean enabled) {
    this.enabled = enabled;
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
