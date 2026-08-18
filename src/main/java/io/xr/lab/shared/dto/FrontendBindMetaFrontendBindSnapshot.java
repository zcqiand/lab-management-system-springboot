package io.xr.lab.shared.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.*;
import java.util.Objects;
import org.springframework.lang.Nullable;

/** FrontendBindMetaFrontendBindSnapshot */
@JsonTypeName("FrontendBindMeta.FrontendBindSnapshot")
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    date = "2026-08-18T09:31:54.550738400+08:00[Asia/Shanghai]",
    comments = "Generator version: 7.24.0")
public class FrontendBindMetaFrontendBindSnapshot {

  private BackendRegistry registry;

  private AuthContext authContext;

  private TokenStorageKeys tokenKeys;

  public FrontendBindMetaFrontendBindSnapshot() {
    super();
  }

  /** Constructor with only required parameters */
  public FrontendBindMetaFrontendBindSnapshot(
      BackendRegistry registry, AuthContext authContext, TokenStorageKeys tokenKeys) {
    this.registry = registry;
    this.authContext = authContext;
    this.tokenKeys = tokenKeys;
  }

  public FrontendBindMetaFrontendBindSnapshot registry(BackendRegistry registry) {
    this.registry = registry;
    return this;
  }

  /**
   * Get registry
   *
   * @return registry
   */
  @NotNull
  @Valid
  @Schema(name = "registry", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("registry")
  public BackendRegistry getRegistry() {
    return registry;
  }

  @JsonProperty("registry")
  public void setRegistry(BackendRegistry registry) {
    this.registry = registry;
  }

  public FrontendBindMetaFrontendBindSnapshot authContext(AuthContext authContext) {
    this.authContext = authContext;
    return this;
  }

  /**
   * Get authContext
   *
   * @return authContext
   */
  @NotNull
  @Valid
  @Schema(name = "authContext", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("authContext")
  public AuthContext getAuthContext() {
    return authContext;
  }

  @JsonProperty("authContext")
  public void setAuthContext(AuthContext authContext) {
    this.authContext = authContext;
  }

  public FrontendBindMetaFrontendBindSnapshot tokenKeys(TokenStorageKeys tokenKeys) {
    this.tokenKeys = tokenKeys;
    return this;
  }

  /**
   * Get tokenKeys
   *
   * @return tokenKeys
   */
  @NotNull
  @Valid
  @Schema(name = "tokenKeys", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("tokenKeys")
  public TokenStorageKeys getTokenKeys() {
    return tokenKeys;
  }

  @JsonProperty("tokenKeys")
  public void setTokenKeys(TokenStorageKeys tokenKeys) {
    this.tokenKeys = tokenKeys;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FrontendBindMetaFrontendBindSnapshot frontendBindMetaFrontendBindSnapshot =
        (FrontendBindMetaFrontendBindSnapshot) o;
    return Objects.equals(this.registry, frontendBindMetaFrontendBindSnapshot.registry)
        && Objects.equals(this.authContext, frontendBindMetaFrontendBindSnapshot.authContext)
        && Objects.equals(this.tokenKeys, frontendBindMetaFrontendBindSnapshot.tokenKeys);
  }

  @Override
  public int hashCode() {
    return Objects.hash(registry, authContext, tokenKeys);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class FrontendBindMetaFrontendBindSnapshot {\n");
    sb.append("    registry: ").append(toIndentedString(registry)).append("\n");
    sb.append("    authContext: ").append(toIndentedString(authContext)).append("\n");
    sb.append("    tokenKeys: ").append(toIndentedString(tokenKeys)).append("\n");
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
