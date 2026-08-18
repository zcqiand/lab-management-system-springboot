package io.xr.lab.platform.entity;

import java.io.Serializable;
import java.util.Objects;

/**
 * 码表 4 表（M04.F06-F09）的复合主键（tenant_id, code）。用于 {@code @IdClass}。
 *
 * <p>见 V012__add_tenant_isolation.sql：品牌/型号/规格/等级按 tenant_id + code 唯一。复合键是 SSOT（shared 仓）。
 */
public class CatalogEntryKey implements Serializable {

  private static final long serialVersionUID = 1L;

  private String tenantId;

  private String code;

  public CatalogEntryKey() {}

  public CatalogEntryKey(String tenantId, String code) {
    this.tenantId = tenantId;
    this.code = code;
  }

  public String getTenantId() {
    return tenantId;
  }

  public void setTenantId(String tenantId) {
    this.tenantId = tenantId;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof CatalogEntryKey that)) {
      return false;
    }
    return Objects.equals(tenantId, that.tenantId) && Objects.equals(code, that.code);
  }

  @Override
  public int hashCode() {
    return Objects.hash(tenantId, code);
  }
}
