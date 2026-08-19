package io.xr.lab.shared.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.constraints.*;
import java.util.*;
import java.util.Objects;
import org.springframework.lang.Nullable;

/** SpecialtyObjectLink */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    date = "2026-08-19T17:37:44.319774200+08:00[Asia/Shanghai]",
    comments = "Generator version: 7.24.0")
public class SpecialtyObjectLink {

  private String inspectionSpecialtyCode;

  private String inspectionObjectCode;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String remark;

  public SpecialtyObjectLink() {
    super();
  }

  /** Constructor with only required parameters */
  public SpecialtyObjectLink(String inspectionSpecialtyCode, String inspectionObjectCode) {
    this.inspectionSpecialtyCode = inspectionSpecialtyCode;
    this.inspectionObjectCode = inspectionObjectCode;
  }

  public SpecialtyObjectLink inspectionSpecialtyCode(String inspectionSpecialtyCode) {
    this.inspectionSpecialtyCode = inspectionSpecialtyCode;
    return this;
  }

  /**
   * Get inspectionSpecialtyCode
   *
   * @return inspectionSpecialtyCode
   */
  @NotNull
  @Schema(name = "inspectionSpecialtyCode", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("inspectionSpecialtyCode")
  public String getInspectionSpecialtyCode() {
    return inspectionSpecialtyCode;
  }

  @JsonProperty("inspectionSpecialtyCode")
  public void setInspectionSpecialtyCode(String inspectionSpecialtyCode) {
    this.inspectionSpecialtyCode = inspectionSpecialtyCode;
  }

  public SpecialtyObjectLink inspectionObjectCode(String inspectionObjectCode) {
    this.inspectionObjectCode = inspectionObjectCode;
    return this;
  }

  /**
   * Get inspectionObjectCode
   *
   * @return inspectionObjectCode
   */
  @NotNull
  @Schema(name = "inspectionObjectCode", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("inspectionObjectCode")
  public String getInspectionObjectCode() {
    return inspectionObjectCode;
  }

  @JsonProperty("inspectionObjectCode")
  public void setInspectionObjectCode(String inspectionObjectCode) {
    this.inspectionObjectCode = inspectionObjectCode;
  }

  public SpecialtyObjectLink remark(@Nullable String remark) {
    this.remark = remark;
    return this;
  }

  /**
   * Get remark
   *
   * @return remark
   */
  @Schema(name = "remark", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("remark")
  public @Nullable String getRemark() {
    return remark;
  }

  @JsonProperty("remark")
  public void setRemark(@Nullable String remark) {
    this.remark = remark;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SpecialtyObjectLink specialtyObjectLink = (SpecialtyObjectLink) o;
    return Objects.equals(this.inspectionSpecialtyCode, specialtyObjectLink.inspectionSpecialtyCode)
        && Objects.equals(this.inspectionObjectCode, specialtyObjectLink.inspectionObjectCode)
        && Objects.equals(this.remark, specialtyObjectLink.remark);
  }

  @Override
  public int hashCode() {
    return Objects.hash(inspectionSpecialtyCode, inspectionObjectCode, remark);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SpecialtyObjectLink {\n");
    sb.append("    inspectionSpecialtyCode: ")
        .append(toIndentedString(inspectionSpecialtyCode))
        .append("\n");
    sb.append("    inspectionObjectCode: ")
        .append(toIndentedString(inspectionObjectCode))
        .append("\n");
    sb.append("    remark: ").append(toIndentedString(remark)).append("\n");
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
