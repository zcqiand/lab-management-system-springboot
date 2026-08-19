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

/** AuthStateAuthenticatedValue */
@JsonTypeName("AuthStateAuthenticated_value")
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    date = "2026-08-19T17:37:44.319774200+08:00[Asia/Shanghai]",
    comments = "Generator version: 7.24.0")
public class AuthStateAuthenticatedValue {

  /** Gets or Sets kind */
  public enum KindEnum {
    AUTHENTICATED("authenticated");

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

  private MyTenant tenant;

  private List<String> permissions = new ArrayList<>();

  private Long tokenExpiresAt;

  public AuthStateAuthenticatedValue() {
    super();
  }

  /** Constructor with only required parameters */
  public AuthStateAuthenticatedValue(
      KindEnum kind,
      CurrentUser user,
      MyTenant tenant,
      List<String> permissions,
      Long tokenExpiresAt) {
    this.kind = kind;
    this.user = user;
    this.tenant = tenant;
    this.permissions = permissions;
    this.tokenExpiresAt = tokenExpiresAt;
  }

  public AuthStateAuthenticatedValue kind(KindEnum kind) {
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

  public AuthStateAuthenticatedValue user(CurrentUser user) {
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

  public AuthStateAuthenticatedValue tenant(MyTenant tenant) {
    this.tenant = tenant;
    return this;
  }

  /**
   * Get tenant
   *
   * @return tenant
   */
  @NotNull
  @Valid
  @Schema(name = "tenant", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("tenant")
  public MyTenant getTenant() {
    return tenant;
  }

  @JsonProperty("tenant")
  public void setTenant(MyTenant tenant) {
    this.tenant = tenant;
  }

  public AuthStateAuthenticatedValue permissions(List<String> permissions) {
    this.permissions = permissions;
    return this;
  }

  public AuthStateAuthenticatedValue addPermissionsItem(String permissionsItem) {
    if (this.permissions == null) {
      this.permissions = new ArrayList<>();
    }
    this.permissions.add(permissionsItem);
    return this;
  }

  /**
   * Get permissions
   *
   * @return permissions
   */
  @NotNull
  @Schema(name = "permissions", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("permissions")
  public List<String> getPermissions() {
    return permissions;
  }

  @JsonProperty("permissions")
  public void setPermissions(List<String> permissions) {
    this.permissions = permissions;
  }

  public AuthStateAuthenticatedValue tokenExpiresAt(Long tokenExpiresAt) {
    this.tokenExpiresAt = tokenExpiresAt;
    return this;
  }

  /**
   * unix ms
   *
   * @return tokenExpiresAt
   */
  @NotNull
  @Schema(
      name = "tokenExpiresAt",
      description = "unix ms",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("tokenExpiresAt")
  public Long getTokenExpiresAt() {
    return tokenExpiresAt;
  }

  @JsonProperty("tokenExpiresAt")
  public void setTokenExpiresAt(Long tokenExpiresAt) {
    this.tokenExpiresAt = tokenExpiresAt;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AuthStateAuthenticatedValue authStateAuthenticatedValue = (AuthStateAuthenticatedValue) o;
    return Objects.equals(this.kind, authStateAuthenticatedValue.kind)
        && Objects.equals(this.user, authStateAuthenticatedValue.user)
        && Objects.equals(this.tenant, authStateAuthenticatedValue.tenant)
        && Objects.equals(this.permissions, authStateAuthenticatedValue.permissions)
        && Objects.equals(this.tokenExpiresAt, authStateAuthenticatedValue.tokenExpiresAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(kind, user, tenant, permissions, tokenExpiresAt);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AuthStateAuthenticatedValue {\n");
    sb.append("    kind: ").append(toIndentedString(kind)).append("\n");
    sb.append("    user: ").append(toIndentedString(user)).append("\n");
    sb.append("    tenant: ").append(toIndentedString(tenant)).append("\n");
    sb.append("    permissions: ").append(toIndentedString(permissions)).append("\n");
    sb.append("    tokenExpiresAt: ").append(toIndentedString(tokenExpiresAt)).append("\n");
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
