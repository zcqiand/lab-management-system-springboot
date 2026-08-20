package io.xr.lab.shared.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.*;
import java.util.Objects;
import org.springframework.lang.Nullable;

/**
 * AuthContext 数据契约。方法(login/logout/refresh/switchTenant/hasPermission/onChange)由消费方实现,TS 签名见
 * .state/decision-log.md §2.2
 */
@Schema(
    name = "AuthContext",
    description =
        "AuthContext 数据契约。方法(login/logout/refresh/switchTenant/hasPermission/onChange)由消费方实现,TS 签名见 .state/decision-log.md §2.2")
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    date = "2026-08-20T13:31:51.674991500+08:00[Asia/Shanghai]",
    comments = "Generator version: 7.24.0")
public class AuthContext {

  private AuthState state;

  public AuthContext() {
    super();
  }

  /** Constructor with only required parameters */
  public AuthContext(AuthState state) {
    this.state = state;
  }

  public AuthContext state(AuthState state) {
    this.state = state;
    return this;
  }

  /**
   * 当前 auth 状态
   *
   * @return state
   */
  @NotNull
  @Valid
  @Schema(name = "state", description = "当前 auth 状态", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("state")
  public AuthState getState() {
    return state;
  }

  @JsonProperty("state")
  public void setState(AuthState state) {
    this.state = state;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AuthContext authContext = (AuthContext) o;
    return Objects.equals(this.state, authContext.state);
  }

  @Override
  public int hashCode() {
    return Objects.hash(state);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AuthContext {\n");
    sb.append("    state: ").append(toIndentedString(state)).append("\n");
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
