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

/** DashboardStatsQualifiedRateByMaterial */
@JsonTypeName("DashboardStats_qualifiedRateByMaterial")
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    date = "2026-09-04T01:01:08.136686300+08:00[Asia/Shanghai]",
    comments = "Generator version: 7.24.0")
public class DashboardStatsQualifiedRateByMaterial {

  private MaterialQualifiedRate concrete;

  private MaterialQualifiedRate rebar;

  private MaterialQualifiedRate sand;

  public DashboardStatsQualifiedRateByMaterial() {
    super();
  }

  /** Constructor with only required parameters */
  public DashboardStatsQualifiedRateByMaterial(
      MaterialQualifiedRate concrete, MaterialQualifiedRate rebar, MaterialQualifiedRate sand) {
    this.concrete = concrete;
    this.rebar = rebar;
    this.sand = sand;
  }

  public DashboardStatsQualifiedRateByMaterial concrete(MaterialQualifiedRate concrete) {
    this.concrete = concrete;
    return this;
  }

  /**
   * Get concrete
   *
   * @return concrete
   */
  @NotNull
  @Valid
  @Schema(name = "concrete", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("concrete")
  public MaterialQualifiedRate getConcrete() {
    return concrete;
  }

  @JsonProperty("concrete")
  public void setConcrete(MaterialQualifiedRate concrete) {
    this.concrete = concrete;
  }

  public DashboardStatsQualifiedRateByMaterial rebar(MaterialQualifiedRate rebar) {
    this.rebar = rebar;
    return this;
  }

  /**
   * Get rebar
   *
   * @return rebar
   */
  @NotNull
  @Valid
  @Schema(name = "rebar", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("rebar")
  public MaterialQualifiedRate getRebar() {
    return rebar;
  }

  @JsonProperty("rebar")
  public void setRebar(MaterialQualifiedRate rebar) {
    this.rebar = rebar;
  }

  public DashboardStatsQualifiedRateByMaterial sand(MaterialQualifiedRate sand) {
    this.sand = sand;
    return this;
  }

  /**
   * Get sand
   *
   * @return sand
   */
  @NotNull
  @Valid
  @Schema(name = "sand", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("sand")
  public MaterialQualifiedRate getSand() {
    return sand;
  }

  @JsonProperty("sand")
  public void setSand(MaterialQualifiedRate sand) {
    this.sand = sand;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DashboardStatsQualifiedRateByMaterial dashboardStatsQualifiedRateByMaterial =
        (DashboardStatsQualifiedRateByMaterial) o;
    return Objects.equals(this.concrete, dashboardStatsQualifiedRateByMaterial.concrete)
        && Objects.equals(this.rebar, dashboardStatsQualifiedRateByMaterial.rebar)
        && Objects.equals(this.sand, dashboardStatsQualifiedRateByMaterial.sand);
  }

  @Override
  public int hashCode() {
    return Objects.hash(concrete, rebar, sand);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DashboardStatsQualifiedRateByMaterial {\n");
    sb.append("    concrete: ").append(toIndentedString(concrete)).append("\n");
    sb.append("    rebar: ").append(toIndentedString(rebar)).append("\n");
    sb.append("    sand: ").append(toIndentedString(sand)).append("\n");
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
