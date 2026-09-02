package io.xr.lab.shared.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.constraints.*;
import java.util.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.lang.Nullable;

/** MyTenant */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    date = "2026-09-02T22:35:42.457326500+08:00[Asia/Shanghai]",
    comments = "Generator version: 7.24.0")
public class MyTenant {

  private String tenantId;

  private String code;

  private String name;

  private List<String> roleIds = new ArrayList<>();

  public MyTenant() {
    super();
  }

  /** Constructor with only required parameters */
  public MyTenant(String tenantId, String code, String name, List<String> roleIds) {
    this.tenantId = tenantId;
    this.code = code;
    this.name = name;
    this.roleIds = roleIds;
  }

  public MyTenant tenantId(String tenantId) {
    this.tenantId = tenantId;
    return this;
  }

  /**
   * Get tenantId
   *
   * @return tenantId
   */
  @NotNull
  @Schema(name = "tenantId", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("tenantId")
  public String getTenantId() {
    return tenantId;
  }

  @JsonProperty("tenantId")
  public void setTenantId(String tenantId) {
    this.tenantId = tenantId;
  }

  public MyTenant code(String code) {
    this.code = code;
    return this;
  }

  /**
   * Get code
   *
   * @return code
   */
  @NotNull
  @Schema(name = "code", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("code")
  public String getCode() {
    return code;
  }

  @JsonProperty("code")
  public void setCode(String code) {
    this.code = code;
  }

  public MyTenant name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   *
   * @return name
   */
  @NotNull
  @Schema(name = "name", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("name")
  public String getName() {
    return name;
  }

  @JsonProperty("name")
  public void setName(String name) {
    this.name = name;
  }

  public MyTenant roleIds(List<String> roleIds) {
    this.roleIds = roleIds;
    return this;
  }

  public MyTenant addRoleIdsItem(String roleIdsItem) {
    if (this.roleIds == null) {
      this.roleIds = new ArrayList<>();
    }
    this.roleIds.add(roleIdsItem);
    return this;
  }

  /**
   * Get roleIds
   *
   * @return roleIds
   */
  @NotNull
  @Schema(name = "roleIds", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("roleIds")
  public List<String> getRoleIds() {
    return roleIds;
  }

  @JsonProperty("roleIds")
  public void setRoleIds(List<String> roleIds) {
    this.roleIds = roleIds;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MyTenant myTenant = (MyTenant) o;
    return Objects.equals(this.tenantId, myTenant.tenantId)
        && Objects.equals(this.code, myTenant.code)
        && Objects.equals(this.name, myTenant.name)
        && Objects.equals(this.roleIds, myTenant.roleIds);
  }

  @Override
  public int hashCode() {
    return Objects.hash(tenantId, code, name, roleIds);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MyTenant {\n");
    sb.append("    tenantId: ").append(toIndentedString(tenantId)).append("\n");
    sb.append("    code: ").append(toIndentedString(code)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    roleIds: ").append(toIndentedString(roleIds)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces (except the first line).
   */
  private String toIndentedString(@Nullable Object o) {
    return o == null ? "null" : o.toString().replace("\n", "\n    ");
  }
}
