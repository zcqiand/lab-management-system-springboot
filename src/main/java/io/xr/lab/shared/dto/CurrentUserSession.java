package io.xr.lab.shared.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.lang.Nullable;

/** CurrentUserSession */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    date = "2026-09-02T22:35:42.457326500+08:00[Asia/Shanghai]",
    comments = "Generator version: 7.24.0")
public class CurrentUserSession {

  private CurrentUser user;

  private List<@Valid MyTenant> tenants = new ArrayList<>();

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String currentTenantId;

  public CurrentUserSession() {
    super();
  }

  /** Constructor with only required parameters */
  public CurrentUserSession(CurrentUser user, List<@Valid MyTenant> tenants) {
    this.user = user;
    this.tenants = tenants;
  }

  public CurrentUserSession user(CurrentUser user) {
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

  public CurrentUserSession tenants(List<@Valid MyTenant> tenants) {
    this.tenants = tenants;
    return this;
  }

  public CurrentUserSession addTenantsItem(MyTenant tenantsItem) {
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

  public CurrentUserSession currentTenantId(@Nullable String currentTenantId) {
    this.currentTenantId = currentTenantId;
    return this;
  }

  /**
   * Get currentTenantId
   *
   * @return currentTenantId
   */
  @Schema(name = "currentTenantId", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("currentTenantId")
  public @Nullable String getCurrentTenantId() {
    return currentTenantId;
  }

  @JsonProperty("currentTenantId")
  public void setCurrentTenantId(@Nullable String currentTenantId) {
    this.currentTenantId = currentTenantId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CurrentUserSession currentUserSession = (CurrentUserSession) o;
    return Objects.equals(this.user, currentUserSession.user)
        && Objects.equals(this.tenants, currentUserSession.tenants)
        && Objects.equals(this.currentTenantId, currentUserSession.currentTenantId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(user, tenants, currentTenantId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CurrentUserSession {\n");
    sb.append("    user: ").append(toIndentedString(user)).append("\n");
    sb.append("    tenants: ").append(toIndentedString(tenants)).append("\n");
    sb.append("    currentTenantId: ").append(toIndentedString(currentTenantId)).append("\n");
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
