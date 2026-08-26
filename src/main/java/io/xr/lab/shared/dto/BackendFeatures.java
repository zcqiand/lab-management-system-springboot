package io.xr.lab.shared.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.constraints.*;
import java.util.*;
import java.util.Objects;
import org.springframework.lang.Nullable;

/** 已废弃 (ADR-0014);后端能力矩阵 */
@Schema(name = "BackendFeatures", description = "已废弃 (ADR-0014);后端能力矩阵")
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    date = "2026-08-26T12:43:04.549030500+08:00[Asia/Shanghai]",
    comments = "Generator version: 7.24.0")
public class BackendFeatures {

  private Boolean sso;

  private Boolean realDb;

  public BackendFeatures() {
    super();
  }

  /** Constructor with only required parameters */
  public BackendFeatures(Boolean sso, Boolean realDb) {
    this.sso = sso;
    this.realDb = realDb;
  }

  public BackendFeatures sso(Boolean sso) {
    this.sso = sso;
    return this;
  }

  /**
   * 是否启用 SSO 跳转(msw=false / nextjs=true / springboot/aspnetcore 视实现)
   *
   * @return sso
   */
  @NotNull
  @Schema(
      name = "sso",
      description = "是否启用 SSO 跳转(msw=false / nextjs=true / springboot/aspnetcore 视实现)",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("sso")
  public Boolean getSso() {
    return sso;
  }

  @JsonProperty("sso")
  public void setSso(Boolean sso) {
    this.sso = sso;
  }

  public BackendFeatures realDb(Boolean realDb) {
    this.realDb = realDb;
    return this;
  }

  /**
   * 是否对接真实数据库(vs mock seed)
   *
   * @return realDb
   */
  @NotNull
  @Schema(
      name = "realDb",
      description = "是否对接真实数据库(vs mock seed)",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("realDb")
  public Boolean getRealDb() {
    return realDb;
  }

  @JsonProperty("realDb")
  public void setRealDb(Boolean realDb) {
    this.realDb = realDb;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BackendFeatures backendFeatures = (BackendFeatures) o;
    return Objects.equals(this.sso, backendFeatures.sso)
        && Objects.equals(this.realDb, backendFeatures.realDb);
  }

  @Override
  public int hashCode() {
    return Objects.hash(sso, realDb);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BackendFeatures {\n");
    sb.append("    sso: ").append(toIndentedString(sso)).append("\n");
    sb.append("    realDb: ").append(toIndentedString(realDb)).append("\n");
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
