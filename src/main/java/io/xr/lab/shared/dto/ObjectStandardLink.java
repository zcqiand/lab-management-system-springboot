package io.xr.lab.shared.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.*;
import java.util.Objects;
import org.springframework.lang.Nullable;

/** ObjectStandardLink */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    date = "2026-08-26T12:43:04.549030500+08:00[Asia/Shanghai]",
    comments = "Generator version: 7.24.0")
public class ObjectStandardLink {

  private String inspectionObjectCode;

  private String inspectionStandardCode;

  private InspectionStandardRole role;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String remark;

  public ObjectStandardLink() {
    super();
  }

  /** Constructor with only required parameters */
  public ObjectStandardLink(
      String inspectionObjectCode, String inspectionStandardCode, InspectionStandardRole role) {
    this.inspectionObjectCode = inspectionObjectCode;
    this.inspectionStandardCode = inspectionStandardCode;
    this.role = role;
  }

  public ObjectStandardLink inspectionObjectCode(String inspectionObjectCode) {
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

  public ObjectStandardLink inspectionStandardCode(String inspectionStandardCode) {
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

  public ObjectStandardLink role(InspectionStandardRole role) {
    this.role = role;
    return this;
  }

  /**
   * Get role
   *
   * @return role
   */
  @NotNull
  @Valid
  @Schema(name = "role", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("role")
  public InspectionStandardRole getRole() {
    return role;
  }

  @JsonProperty("role")
  public void setRole(InspectionStandardRole role) {
    this.role = role;
  }

  public ObjectStandardLink remark(@Nullable String remark) {
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
    ObjectStandardLink objectStandardLink = (ObjectStandardLink) o;
    return Objects.equals(this.inspectionObjectCode, objectStandardLink.inspectionObjectCode)
        && Objects.equals(this.inspectionStandardCode, objectStandardLink.inspectionStandardCode)
        && Objects.equals(this.role, objectStandardLink.role)
        && Objects.equals(this.remark, objectStandardLink.remark);
  }

  @Override
  public int hashCode() {
    return Objects.hash(inspectionObjectCode, inspectionStandardCode, role, remark);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ObjectStandardLink {\n");
    sb.append("    inspectionObjectCode: ")
        .append(toIndentedString(inspectionObjectCode))
        .append("\n");
    sb.append("    inspectionStandardCode: ")
        .append(toIndentedString(inspectionStandardCode))
        .append("\n");
    sb.append("    role: ").append(toIndentedString(role)).append("\n");
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
