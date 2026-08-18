package io.xr.lab.platform.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

/**
 * V009__init_report_names.sql — 报告名称（M06.F07）。PK = code。平台级字典（无 tenant_id，per V012 备注）。 ext_fields
 * 是 jsonb（List<ExtFieldDef>），写库时序列化 JSON 字符串。
 */
@Entity
@Table(name = "inspection_report_names")
public class InspectionReportNameEntity {

  @Id
  @Column(name = "code", length = 64, nullable = false)
  private String code;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "full_name")
  private String fullName;

  @Column(name = "template_path")
  private String templatePath;

  @Column(name = "summary_name")
  private String summaryName;

  @JdbcTypeCode(SqlTypes.JSON)
  @Column(name = "ext_fields", columnDefinition = "jsonb")
  private String extFields;

  @Column(name = "description")
  private String description;

  @Column(name = "sort_order", nullable = false)
  private Integer sortOrder = 0;

  @Column(name = "created_at", nullable = false)
  private String createdAt = "";

  @Column(name = "updated_at", nullable = false)
  private String updatedAt = "";

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(String v) {
    this.name = v;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String v) {
    this.fullName = v;
  }

  public String getTemplatePath() {
    return templatePath;
  }

  public void setTemplatePath(String v) {
    this.templatePath = v;
  }

  public String getSummaryName() {
    return summaryName;
  }

  public void setSummaryName(String v) {
    this.summaryName = v;
  }

  public String getExtFields() {
    return extFields;
  }

  public void setExtFields(String v) {
    this.extFields = v;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String v) {
    this.description = v;
  }

  public Integer getSortOrder() {
    return sortOrder;
  }

  public void setSortOrder(Integer v) {
    this.sortOrder = v;
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
