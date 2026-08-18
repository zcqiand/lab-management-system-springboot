package io.xr.lab.shared.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.lang.Nullable;

/** AuthStateAwaitingTenantValue */
@JsonTypeName("AuthStateAwaiting_tenant_value")
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    date = "2026-08-18T09:31:54.550738400+08:00[Asia/Shanghai]",
    comments = "Generator version: 7.24.0")
public class AuthStateAwaitingTenantValue {

  /** Gets or Sets kind */
  public enum KindEnum {
    AWAITING_TENANT("awaiting_tenant");

    private final String value;

    KindEnum(String value) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static KindEnum fromValue(String value) {
      for (KindEnum b : KindEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  private KindEnum kind;

  private CurrentUser user;

  private List<@Valid MyTenant> tenants = new ArrayList<>();

  public AuthStateAwaitingTenantValue() {
    super();
  }

  /** Constructor with only required parameters */
  public AuthStateAwaitingTenantValue(
      KindEnum kind, CurrentUser user, List<@Valid MyTenant> tenants) {
    this.kind = kind;
    this.user = user;
    this.tenants = tenants;
  }

  public AuthStateAwaitingTenantValue kind(KindEnum kind) {
    this.kind = kind;
    return this;
  }

  /**
   * Get kind
   *
   * @return kind
   */
  @NotNull
  @Schema(name = "kind", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("kind")
  public KindEnum getKind() {
    return kind;
  }

  @JsonProperty("kind")
  public void setKind(KindEnum kind) {
    this.kind = kind;
  }

  public AuthStateAwaitingTenantValue user(CurrentUser user) {
    this.user = user;
    return this;
  }

  /**
   * Get user
   *
   * @return user
   */
  @NotNull
  @Valid
  @Schema(name = "user", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("user")
  public CurrentUser getUser() {
    return user;
  }

  @JsonProperty("user")
  public void setUser(CurrentUser user) {
    this.user = user;
  }

  public AuthStateAwaitingTenantValue tenants(List<@Valid MyTenant> tenants) {
    this.tenants = tenants;
    return this;
  }

  public AuthStateAwaitingTenantValue addTenantsItem(MyTenant tenantsItem) {
    if (this.tenants == null) {
      this.tenants = new ArrayList<>();
    }
    this.tenants.add(tenantsItem);
    return this;
  }

  /**
   * Get tenants
   *
   * @return tenants
   */
  @NotNull
  @Valid
  @Schema(name = "tenants", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("tenants")
  public List<@Valid MyTenant> getTenants() {
    return tenants;
  }

  @JsonProperty("tenants")
  public void setTenants(List<@Valid MyTenant> tenants) {
    this.tenants = tenants;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AuthStateAwaitingTenantValue authStateAwaitingTenantValue = (AuthStateAwaitingTenantValue) o;
    return Objects.equals(this.kind, authStateAwaitingTenantValue.kind)
        && Objects.equals(this.user, authStateAwaitingTenantValue.user)
        && Objects.equals(this.tenants, authStateAwaitingTenantValue.tenants);
  }

  @Override
  public int hashCode() {
    return Objects.hash(kind, user, tenants);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AuthStateAwaitingTenantValue {\n");
    sb.append("    kind: ").append(toIndentedString(kind)).append("\n");
    sb.append("    user: ").append(toIndentedString(user)).append("\n");
    sb.append("    tenants: ").append(toIndentedString(tenants)).append("\n");
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
