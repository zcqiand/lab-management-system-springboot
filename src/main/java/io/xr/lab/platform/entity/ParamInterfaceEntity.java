package io.xr.lab.platform.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

/**
 * V010__init_param_interfaces.sql — 参数界面（M06.F08）。PK = code。平台级字典（无 tenant_id，per V012 备注）。 config
 * 是 jsonb（Map<String,Object>），写库时序列化 JSON 字符串。
 */
@Entity
@Table(name = "param_interfaces")
public class ParamInterfaceEntity {

  @Id
  @Column(name = "code", length = 64, nullable = false)
  private String code;

  @Column(name = "name")
  private String name;

  @Column(name = "component_path", nullable = false)
  private String componentPath;

  @Column(name = "description")
  private String description;

  @Column(name = "is_official")
  private Boolean isOfficial;

  @Column(name = "sort_order", nullable = false)
  private Integer sortOrder = 0;

  @JdbcTypeCode(SqlTypes.JSON)
  @Column(name = "config", columnDefinition = "jsonb")
  private String config;

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

  public String getComponentPath() {
    return componentPath;
  }

  public void setComponentPath(String v) {
    this.componentPath = v;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String v) {
    this.description = v;
  }

  public Boolean getIsOfficial() {
    return isOfficial;
  }

  public void setIsOfficial(Boolean v) {
    this.isOfficial = v;
  }

  public Integer getSortOrder() {
    return sortOrder;
  }

  public void setSortOrder(Integer v) {
    this.sortOrder = v;
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
