package io.xr.lab.platform.entity;

import io.xr.lab.platform.entity.enums.InspectionParameterSourceTypeConverter;
import io.xr.lab.shared.dto.InspectionParameterSourceType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

/**
 * V008__init_inspection_dictionary.sql — 检测参数（M06.F03）。PK = code。平台级字典（无 tenant_id，per V012
 * 备注）。aliases 走 jsonb（List<String>）。
 */
@Entity
@Table(name = "inspection_parameters")
public class InspectionParameterEntity {

  @Id
  @Column(name = "code", length = 64, nullable = false)
  private String code;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "raw_name", nullable = false)
  private String rawName;

  @Column(name = "canonical_name", nullable = false)
  private String canonicalName;

  @Column(name = "method_text")
  private String methodText;

  @JdbcTypeCode(SqlTypes.JSON)
  @Column(name = "aliases", columnDefinition = "jsonb", nullable = false)
  private String aliases = "[]";

  @Column(name = "unit")
  private String unit;

  @Convert(converter = InspectionParameterSourceTypeConverter.class)
  @Column(name = "source_type", nullable = false)
  private InspectionParameterSourceType sourceType = InspectionParameterSourceType.OFFICIAL;

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

  public String getRawName() {
    return rawName;
  }

  public void setRawName(String v) {
    this.rawName = v;
  }

  public String getCanonicalName() {
    return canonicalName;
  }

  public void setCanonicalName(String v) {
    this.canonicalName = v;
  }

  public String getMethodText() {
    return methodText;
  }

  public void setMethodText(String v) {
    this.methodText = v;
  }

  public String getAliases() {
    return aliases;
  }

  public void setAliases(String v) {
    this.aliases = v;
  }

  public String getUnit() {
    return unit;
  }

  public void setUnit(String v) {
    this.unit = v;
  }

  public InspectionParameterSourceType getSourceType() {
    return sourceType;
  }

  public void setSourceType(InspectionParameterSourceType v) {
    this.sourceType = v;
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
