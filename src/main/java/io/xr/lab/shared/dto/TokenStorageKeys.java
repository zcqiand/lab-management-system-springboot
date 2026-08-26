package io.xr.lab.shared.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.constraints.*;
import java.util.*;
import java.util.Objects;
import org.springframework.lang.Nullable;

/** 前端持久化 key 命名约定;后端契约不感知,但前端实现必须遵守 */
@Schema(name = "TokenStorageKeys", description = "前端持久化 key 命名约定;后端契约不感知,但前端实现必须遵守")
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    date = "2026-08-26T12:43:04.549030500+08:00[Asia/Shanghai]",
    comments = "Generator version: 7.24.0")
public class TokenStorageKeys {

  /** Bearer token */
  public enum AccessTokenEnum {
    LAB_ACCESS_TOKEN("lab.accessToken");

    private final String value;

    AccessTokenEnum(String value) {
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
    public static AccessTokenEnum fromValue(String value) {
      for (AccessTokenEnum b : AccessTokenEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  private AccessTokenEnum accessToken;

  /** refresh token(与 accessToken 分存,便于隔离 XSS 影响面) */
  public enum RefreshTokenEnum {
    LAB_REFRESH_TOKEN("lab.refreshToken");

    private final String value;

    RefreshTokenEnum(String value) {
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
    public static RefreshTokenEnum fromValue(String value) {
      for (RefreshTokenEnum b : RefreshTokenEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  private RefreshTokenEnum refreshToken;

  /** 当前选中租户 ID(authenticated 态缓存) */
  public enum ActiveTenantIdEnum {
    LAB_ACTIVE_TENANT_ID("lab.activeTenantId");

    private final String value;

    ActiveTenantIdEnum(String value) {
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
    public static ActiveTenantIdEnum fromValue(String value) {
      for (ActiveTenantIdEnum b : ActiveTenantIdEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  private ActiveTenantIdEnum activeTenantId;

  /** permissions 缓存(避免每次路由跳转都打 /auth/permissions) */
  public enum PermissionsCacheEnum {
    LAB_PERMISSIONS("lab.permissions");

    private final String value;

    PermissionsCacheEnum(String value) {
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
    public static PermissionsCacheEnum fromValue(String value) {
      for (PermissionsCacheEnum b : PermissionsCacheEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  private PermissionsCacheEnum permissionsCache;

  public TokenStorageKeys() {
    super();
  }

  /** Constructor with only required parameters */
  public TokenStorageKeys(
      AccessTokenEnum accessToken,
      RefreshTokenEnum refreshToken,
      ActiveTenantIdEnum activeTenantId,
      PermissionsCacheEnum permissionsCache) {
    this.accessToken = accessToken;
    this.refreshToken = refreshToken;
    this.activeTenantId = activeTenantId;
    this.permissionsCache = permissionsCache;
  }

  public TokenStorageKeys accessToken(AccessTokenEnum accessToken) {
    this.accessToken = accessToken;
    return this;
  }

  /**
   * Bearer token
   *
   * @return accessToken
   */
  @NotNull
  @Schema(
      name = "accessToken",
      description = "Bearer token",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("accessToken")
  public AccessTokenEnum getAccessToken() {
    return accessToken;
  }

  @JsonProperty("accessToken")
  public void setAccessToken(AccessTokenEnum accessToken) {
    this.accessToken = accessToken;
  }

  public TokenStorageKeys refreshToken(RefreshTokenEnum refreshToken) {
    this.refreshToken = refreshToken;
    return this;
  }

  /**
   * refresh token(与 accessToken 分存,便于隔离 XSS 影响面)
   *
   * @return refreshToken
   */
  @NotNull
  @Schema(
      name = "refreshToken",
      description = "refresh token(与 accessToken 分存,便于隔离 XSS 影响面)",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("refreshToken")
  public RefreshTokenEnum getRefreshToken() {
    return refreshToken;
  }

  @JsonProperty("refreshToken")
  public void setRefreshToken(RefreshTokenEnum refreshToken) {
    this.refreshToken = refreshToken;
  }

  public TokenStorageKeys activeTenantId(ActiveTenantIdEnum activeTenantId) {
    this.activeTenantId = activeTenantId;
    return this;
  }

  /**
   * 当前选中租户 ID(authenticated 态缓存)
   *
   * @return activeTenantId
   */
  @NotNull
  @Schema(
      name = "activeTenantId",
      description = "当前选中租户 ID(authenticated 态缓存)",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("activeTenantId")
  public ActiveTenantIdEnum getActiveTenantId() {
    return activeTenantId;
  }

  @JsonProperty("activeTenantId")
  public void setActiveTenantId(ActiveTenantIdEnum activeTenantId) {
    this.activeTenantId = activeTenantId;
  }

  public TokenStorageKeys permissionsCache(PermissionsCacheEnum permissionsCache) {
    this.permissionsCache = permissionsCache;
    return this;
  }

  /**
   * permissions 缓存(避免每次路由跳转都打 /auth/permissions)
   *
   * @return permissionsCache
   */
  @NotNull
  @Schema(
      name = "permissionsCache",
      description = "permissions 缓存(避免每次路由跳转都打 /auth/permissions)",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("permissionsCache")
  public PermissionsCacheEnum getPermissionsCache() {
    return permissionsCache;
  }

  @JsonProperty("permissionsCache")
  public void setPermissionsCache(PermissionsCacheEnum permissionsCache) {
    this.permissionsCache = permissionsCache;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TokenStorageKeys tokenStorageKeys = (TokenStorageKeys) o;
    return Objects.equals(this.accessToken, tokenStorageKeys.accessToken)
        && Objects.equals(this.refreshToken, tokenStorageKeys.refreshToken)
        && Objects.equals(this.activeTenantId, tokenStorageKeys.activeTenantId)
        && Objects.equals(this.permissionsCache, tokenStorageKeys.permissionsCache);
  }

  @Override
  public int hashCode() {
    return Objects.hash(accessToken, refreshToken, activeTenantId, permissionsCache);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TokenStorageKeys {\n");
    sb.append("    accessToken: ").append(toIndentedString(accessToken)).append("\n");
    sb.append("    refreshToken: ").append(toIndentedString(refreshToken)).append("\n");
    sb.append("    activeTenantId: ").append(toIndentedString(activeTenantId)).append("\n");
    sb.append("    permissionsCache: ").append(toIndentedString(permissionsCache)).append("\n");
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
