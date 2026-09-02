package io.xr.lab.shared.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.constraints.*;
import java.util.*;
import java.util.Objects;
import org.springframework.lang.Nullable;

/** CurrentUser */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    date = "2026-09-02T22:35:42.457326500+08:00[Asia/Shanghai]",
    comments = "Generator version: 7.24.0")
public class CurrentUser {

  private String id;

  private String username;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String displayName;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String roleCode;

  public CurrentUser() {
    super();
  }

  /** Constructor with only required parameters */
  public CurrentUser(String id, String username) {
    this.id = id;
    this.username = username;
  }

  public CurrentUser id(String id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   *
   * @return id
   */
  @NotNull
  @Schema(name = "id", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("id")
  public String getId() {
    return id;
  }

  @JsonProperty("id")
  public void setId(String id) {
    this.id = id;
  }

  public CurrentUser username(String username) {
    this.username = username;
    return this;
  }

  /**
   * Get username
   *
   * @return username
   */
  @NotNull
  @Schema(name = "username", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("username")
  public String getUsername() {
    return username;
  }

  @JsonProperty("username")
  public void setUsername(String username) {
    this.username = username;
  }

  public CurrentUser displayName(@Nullable String displayName) {
    this.displayName = displayName;
    return this;
  }

  /**
   * Get displayName
   *
   * @return displayName
   */
  @Schema(name = "displayName", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("displayName")
  public @Nullable String getDisplayName() {
    return displayName;
  }

  @JsonProperty("displayName")
  public void setDisplayName(@Nullable String displayName) {
    this.displayName = displayName;
  }

  public CurrentUser roleCode(@Nullable String roleCode) {
    this.roleCode = roleCode;
    return this;
  }

  /**
   * Get roleCode
   *
   * @return roleCode
   */
  @Schema(name = "roleCode", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("roleCode")
  public @Nullable String getRoleCode() {
    return roleCode;
  }

  @JsonProperty("roleCode")
  public void setRoleCode(@Nullable String roleCode) {
    this.roleCode = roleCode;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CurrentUser currentUser = (CurrentUser) o;
    return Objects.equals(this.id, currentUser.id)
        && Objects.equals(this.username, currentUser.username)
        && Objects.equals(this.displayName, currentUser.displayName)
        && Objects.equals(this.roleCode, currentUser.roleCode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, username, displayName, roleCode);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CurrentUser {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    username: ").append(toIndentedString(username)).append("\n");
    sb.append("    displayName: ").append(toIndentedString(displayName)).append("\n");
    sb.append("    roleCode: ").append(toIndentedString(roleCode)).append("\n");
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
