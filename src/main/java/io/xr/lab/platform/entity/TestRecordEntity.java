package io.xr.lab.platform.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * V003__init_test_records.sql — 检测记录（M03.F03）。PK = id text。tenant-scoped（V012 加 tenant_id）。FK
 * sample_id → samples(id) ON DELETE CASCADE（V003 真实约束）。 parameter/standard/requirement 是逻辑 FK 到
 * inspection_parameters / inspection_standards / inspection_technical_requirements（V011
 * 真实约束已加），不强制。
 */
@Entity
@Table(name = "test_records")
public class TestRecordEntity {

  @Id
  @Column(name = "id", length = 64, nullable = false)
  private String id;

  @Column(name = "tenant_id", length = 64, nullable = false)
  private String tenantId = "";

  @Column(name = "sample_id", length = 64, nullable = false)
  private String sampleId;

  @Column(name = "parameter_code", length = 64, nullable = false)
  private String parameterCode;

  @Column(name = "standard_code", length = 128)
  private String standardCode;

  @Column(name = "requirement_code", length = 128)
  private String requirementCode;

  @Column(name = "requirement", nullable = false)
  private String requirement;

  @Column(name = "result", nullable = false)
  private String result;

  @Column(name = "verdict")
  private String verdict;

  @Column(name = "created_at", nullable = false)
  private String createdAt = "";

  @Column(name = "updated_at", nullable = false)
  private String updatedAt = "";

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getTenantId() {
    return tenantId;
  }

  public void setTenantId(String v) {
    this.tenantId = v;
  }

  public String getSampleId() {
    return sampleId;
  }

  public void setSampleId(String v) {
    this.sampleId = v;
  }

  public String getParameterCode() {
    return parameterCode;
  }

  public void setParameterCode(String v) {
    this.parameterCode = v;
  }

  public String getStandardCode() {
    return standardCode;
  }

  public void setStandardCode(String v) {
    this.standardCode = v;
  }

  public String getRequirementCode() {
    return requirementCode;
  }

  public void setRequirementCode(String v) {
    this.requirementCode = v;
  }

  public String getRequirement() {
    return requirement;
  }

  public void setRequirement(String v) {
    this.requirement = v;
  }

  public String getResult() {
    return result;
  }

  public void setResult(String v) {
    this.result = v;
  }

  public String getVerdict() {
    return verdict;
  }

  public void setVerdict(String v) {
    this.verdict = v;
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
