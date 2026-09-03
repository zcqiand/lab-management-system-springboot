package io.xr.lab.shared.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.*;
import java.util.Objects;
import org.springframework.lang.Nullable;

/** SsoCallbackRequest */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    date = "2026-09-04T01:01:08.136686300+08:00[Asia/Shanghai]",
    comments = "Generator version: 7.24.0")
public class SsoCallbackRequest {

  private OAuthGrantType grantType;

  private String code;

  private String redirectUri;

  private String state;

  public SsoCallbackRequest() {
    super();
  }

  /** Constructor with only required parameters */
  public SsoCallbackRequest(
      OAuthGrantType grantType, String code, String redirectUri, String state) {
    this.grantType = grantType;
    this.code = code;
    this.redirectUri = redirectUri;
    this.state = state;
  }

  public SsoCallbackRequest grantType(OAuthGrantType grantType) {
    this.grantType = grantType;
    return this;
  }

  /**
   * Get grantType
   *
   * @return grantType
   */
  @NotNull
  @Valid
  @Schema(name = "grant_type", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("grant_type")
  public OAuthGrantType getGrantType() {
    return grantType;
  }

  @JsonProperty("grant_type")
  public void setGrantType(OAuthGrantType grantType) {
    this.grantType = grantType;
  }

  public SsoCallbackRequest code(String code) {
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

  public SsoCallbackRequest redirectUri(String redirectUri) {
    this.redirectUri = redirectUri;
    return this;
  }

  /**
   * Get redirectUri
   *
   * @return redirectUri
   */
  @NotNull
  @Schema(name = "redirect_uri", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("redirect_uri")
  public String getRedirectUri() {
    return redirectUri;
  }

  @JsonProperty("redirect_uri")
  public void setRedirectUri(String redirectUri) {
    this.redirectUri = redirectUri;
  }

  public SsoCallbackRequest state(String state) {
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
    SsoCallbackRequest ssoCallbackRequest = (SsoCallbackRequest) o;
    return Objects.equals(this.grantType, ssoCallbackRequest.grantType)
        && Objects.equals(this.code, ssoCallbackRequest.code)
        && Objects.equals(this.redirectUri, ssoCallbackRequest.redirectUri)
        && Objects.equals(this.state, ssoCallbackRequest.state);
  }

  @Override
  public int hashCode() {
    return Objects.hash(grantType, code, redirectUri, state);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SsoCallbackRequest {\n");
    sb.append("    grantType: ").append(toIndentedString(grantType)).append("\n");
    sb.append("    code: ").append(toIndentedString(code)).append("\n");
    sb.append("    redirectUri: ").append(toIndentedString(redirectUri)).append("\n");
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
