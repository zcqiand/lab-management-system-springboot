package io.xr.lab.shared.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.constraints.*;
import java.util.*;
import java.util.Objects;
import org.springframework.lang.Nullable;

/** StandardParameterLink */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    date = "2026-08-26T12:43:04.549030500+08:00[Asia/Shanghai]",
    comments = "Generator version: 7.24.0")
public class StandardParameterLink {

  private String inspectionStandardCode;

  private String inspectionParameterCode;

  public StandardParameterLink() {
    super();
  }

  /** Constructor with only required parameters */
  public StandardParameterLink(String inspectionStandardCode, String inspectionParameterCode) {
    this.inspectionStandardCode = inspectionStandardCode;
    this.inspectionParameterCode = inspectionParameterCode;
  }

  public StandardParameterLink inspectionStandardCode(String inspectionStandardCode) {
    this.inspectionStandardCode = inspectionStandardCode;
    return this;
  }

  /**
   * Get inspectionStandardCode
   *
   * @return inspectionStandardCode
   */
  @NotNull
  @Schema(name = "inspectionStandardCode", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("inspectionStandardCode")
  public String getInspectionStandardCode() {
    return inspectionStandardCode;
  }

  @JsonProperty("inspectionStandardCode")
  public void setInspectionStandardCode(String inspectionStandardCode) {
    this.inspectionStandardCode = inspectionStandardCode;
  }

  public StandardParameterLink inspectionParameterCode(String inspectionParameterCode) {
    this.inspectionParameterCode = inspectionParameterCode;
    return this;
  }

  /**
   * Get inspectionParameterCode
   *
   * @return inspectionParameterCode
   */
  @NotNull
  @Schema(name = "inspectionParameterCode", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("inspectionParameterCode")
  public String getInspectionParameterCode() {
    return inspectionParameterCode;
  }

  @JsonProperty("inspectionParameterCode")
  public void setInspectionParameterCode(String inspectionParameterCode) {
    this.inspectionParameterCode = inspectionParameterCode;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    StandardParameterLink standardParameterLink = (StandardParameterLink) o;
    return Objects.equals(this.inspectionStandardCode, standardParameterLink.inspectionStandardCode)
        && Objects.equals(
            this.inspectionParameterCode, standardParameterLink.inspectionParameterCode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(inspectionStandardCode, inspectionParameterCode);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class StandardParameterLink {\n");
    sb.append("    inspectionStandardCode: ")
        .append(toIndentedString(inspectionStandardCode))
        .append("\n");
    sb.append("    inspectionParameterCode: ")
        .append(toIndentedString(inspectionParameterCode))
        .append("\n");
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
