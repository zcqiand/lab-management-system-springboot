package io.xr.lab.shared.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.constraints.*;
import java.util.*;
import java.util.Objects;
import org.springframework.lang.Nullable;

/** SsoRedirect */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    date = "2026-09-04T01:01:08.136686300+08:00[Asia/Shanghai]",
    comments = "Generator version: 7.24.0")
public class SsoRedirect {

  private String authorizeUrl;

  private String state;

  public SsoRedirect() {
    super();
  }

  /** Constructor with only required parameters */
  public SsoRedirect(String authorizeUrl, String state) {
    this.authorizeUrl = authorizeUrl;
    this.state = state;
  }

  public SsoRedirect authorizeUrl(String authorizeUrl) {
    this.authorizeUrl = authorizeUrl;
    return this;
  }

  /**
   * Get authorizeUrl
   *
   * @return authorizeUrl
   */
  @NotNull
  @Schema(name = "authorizeUrl", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("authorizeUrl")
  public String getAuthorizeUrl() {
    return authorizeUrl;
  }

  @JsonProperty("authorizeUrl")
  public void setAuthorizeUrl(String authorizeUrl) {
    this.authorizeUrl = authorizeUrl;
  }

  public SsoRedirect state(String state) {
    this.state = state;
    return this;
  }

  /**
   * Get state
   *
   * @return state
   */
  @NotNull
  @Schema(name = "state", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("state")
  public String getState() {
    return state;
  }

  @JsonProperty("state")
  public void setState(String state) {
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
    SsoRedirect ssoRedirect = (SsoRedirect) o;
    return Objects.equals(this.authorizeUrl, ssoRedirect.authorizeUrl)
        && Objects.equals(this.state, ssoRedirect.state);
  }

  @Override
  public int hashCode() {
    return Objects.hash(authorizeUrl, state);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SsoRedirect {\n");
    sb.append("    authorizeUrl: ").append(toIndentedString(authorizeUrl)).append("\n");
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
