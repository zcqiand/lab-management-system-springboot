package io.xr.lab.shared.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.constraints.*;
import java.util.*;
import java.util.Objects;
import org.springframework.lang.Nullable;

/** MaterialQualifiedRate */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    date = "2026-09-04T01:01:08.136686300+08:00[Asia/Shanghai]",
    comments = "Generator version: 7.24.0")
public class MaterialQualifiedRate {

  private Integer total;

  private Integer pass;

  private Double rate;

  public MaterialQualifiedRate() {
    super();
  }

  /** Constructor with only required parameters */
  public MaterialQualifiedRate(Integer total, Integer pass, Double rate) {
    this.total = total;
    this.pass = pass;
    this.rate = rate;
  }

  public MaterialQualifiedRate total(Integer total) {
    this.total = total;
    return this;
  }

  /**
   * Get total
   *
   * @return total
   */
  @NotNull
  @Schema(name = "total", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("total")
  public Integer getTotal() {
    return total;
  }

  @JsonProperty("total")
  public void setTotal(Integer total) {
    this.total = total;
  }

  public MaterialQualifiedRate pass(Integer pass) {
    this.pass = pass;
    return this;
  }

  /**
   * Get pass
   *
   * @return pass
   */
  @NotNull
  @Schema(name = "pass", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("pass")
  public Integer getPass() {
    return pass;
  }

  @JsonProperty("pass")
  public void setPass(Integer pass) {
    this.pass = pass;
  }

  public MaterialQualifiedRate rate(Double rate) {
    this.rate = rate;
    return this;
  }

  /**
   * Get rate
   *
   * @return rate
   */
  @NotNull
  @Schema(name = "rate", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("rate")
  public Double getRate() {
    return rate;
  }

  @JsonProperty("rate")
  public void setRate(Double rate) {
    this.rate = rate;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MaterialQualifiedRate materialQualifiedRate = (MaterialQualifiedRate) o;
    return Objects.equals(this.total, materialQualifiedRate.total)
        && Objects.equals(this.pass, materialQualifiedRate.pass)
        && Objects.equals(this.rate, materialQualifiedRate.rate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(total, pass, rate);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MaterialQualifiedRate {\n");
    sb.append("    total: ").append(toIndentedString(total)).append("\n");
    sb.append("    pass: ").append(toIndentedString(pass)).append("\n");
    sb.append("    rate: ").append(toIndentedString(rate)).append("\n");
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
