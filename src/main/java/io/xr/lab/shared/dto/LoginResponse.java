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

/** LoginResponse */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    date = "2026-09-04T01:01:08.136686300+08:00[Asia/Shanghai]",
    comments = "Generator version: 7.24.0")
public class LoginResponse {

  private String token;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String refreshToken;

  private CurrentUser user;

  private List<@Valid MyTenant> tenants = new ArrayList<>();

  public LoginResponse() {
    super();
  }

  /** Constructor with only required parameters */
  public LoginResponse(String token, CurrentUser user, List<@Valid MyTenant> tenants) {
    this.token = token;
    this.user = user;
    this.tenants = tenants;
  }

  public LoginResponse token(String token) {
    this.token = token;
    return this;
  }

  /**
   * Get token
   *
   * @return token
   */
  @NotNull
  @Schema(name = "token", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("token")
  public String getToken() {
    return token;
  }

  @JsonProperty("token")
  public void setToken(String token) {
    this.token = token;
  }

  public LoginResponse refreshToken(@Nullable String refreshToken) {
    this.refreshToken = refreshToken;
    return this;
  }

  /**
   * Get refreshToken
   *
   * @return refreshToken
   */
  @Schema(name = "refreshToken", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("refreshToken")
  public @Nullable String getRefreshToken() {
    return refreshToken;
  }

  @JsonProperty("refreshToken")
  public void setRefreshToken(@Nullable String refreshToken) {
    this.refreshToken = refreshToken;
  }

  public LoginResponse user(CurrentUser user) {
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

  public LoginResponse tenants(List<@Valid MyTenant> tenants) {
    this.tenants = tenants;
    return this;
  }

  public LoginResponse addTenantsItem(MyTenant tenantsItem) {
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
    LoginResponse loginResponse = (LoginResponse) o;
    return Objects.equals(this.token, loginResponse.token)
        && Objects.equals(this.refreshToken, loginResponse.refreshToken)
        && Objects.equals(this.user, loginResponse.user)
        && Objects.equals(this.tenants, loginResponse.tenants);
  }

  @Override
  public int hashCode() {
    return Objects.hash(token, refreshToken, user, tenants);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class LoginResponse {\n");
    sb.append("    token: ").append(toIndentedString(token)).append("\n");
    sb.append("    refreshToken: ").append(toIndentedString(refreshToken)).append("\n");
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
