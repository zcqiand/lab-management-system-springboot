package io.xr.lab.platform.entity;

import io.xr.lab.platform.entity.enums.InspectionStandardStatusConverter;
import io.xr.lab.shared.dto.InspectionStandardStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * V008__init_inspection_dictionary.sql — 检测标准（M06.F04）。PK = code。平台级字典（无 tenant_id，per V012
 * 备注）。code 可含 "/"（per SQL 注释）。
 */
@Entity
@Table(name = "inspection_standards")
public class InspectionStandardEntity {

  @Id
  @Column(name = "code", length = 128, nullable = false)
  private String code;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "version")
  private String version;

  @Convert(converter = InspectionStandardStatusConverter.class)
  @Column(name = "status", nullable = false)
  private InspectionStandardStatus status = InspectionStandardStatus.ACTIVE;

  @Column(name = "source_document_id")
  private String sourceDocumentId;

  @Column(name = "source_hash")
  private String sourceHash;

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

  public String getVersion() {
    return version;
  }

  public void setVersion(String v) {
    this.version = v;
  }

  public InspectionStandardStatus getStatus() {
    return status;
  }

  public void setStatus(InspectionStandardStatus v) {
    this.status = v;
  }

  public String getSourceDocumentId() {
    return sourceDocumentId;
  }

  public void setSourceDocumentId(String v) {
    this.sourceDocumentId = v;
  }

  public String getSourceHash() {
    return sourceHash;
  }

  public void setSourceHash(String v) {
    this.sourceHash = v;
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
