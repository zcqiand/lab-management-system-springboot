package io.xr.lab.shared.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.constraints.*;
import java.util.*;
import java.util.Objects;
import org.springframework.lang.Nullable;

/** ParamInterfacesUnlinkParamInterfaceRequest */
@JsonTypeName("ParamInterfaces_unlinkParamInterface_request")
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    date = "2026-09-04T01:01:08.136686300+08:00[Asia/Shanghai]",
    comments = "Generator version: 7.24.0")
public class ParamInterfacesUnlinkParamInterfaceRequest {

  private String inspectionParameterCode;

  private String paramInterfaceCode;

  public ParamInterfacesUnlinkParamInterfaceRequest() {
    super();
  }

  /** Constructor with only required parameters */
  public ParamInterfacesUnlinkParamInterfaceRequest(
      String inspectionParameterCode, String paramInterfaceCode) {
    this.inspectionParameterCode = inspectionParameterCode;
    this.paramInterfaceCode = paramInterfaceCode;
  }

  public ParamInterfacesUnlinkParamInterfaceRequest inspectionParameterCode(
      String inspectionParameterCode) {
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

  public ParamInterfacesUnlinkParamInterfaceRequest paramInterfaceCode(String paramInterfaceCode) {
    this.paramInterfaceCode = paramInterfaceCode;
    return this;
  }

  /**
   * Get paramInterfaceCode
   *
   * @return paramInterfaceCode
   */
  @NotNull
  @Schema(name = "paramInterfaceCode", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("paramInterfaceCode")
  public String getParamInterfaceCode() {
    return paramInterfaceCode;
  }

  @JsonProperty("paramInterfaceCode")
  public void setParamInterfaceCode(String paramInterfaceCode) {
    this.paramInterfaceCode = paramInterfaceCode;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ParamInterfacesUnlinkParamInterfaceRequest paramInterfacesUnlinkParamInterfaceRequest =
        (ParamInterfacesUnlinkParamInterfaceRequest) o;
    return Objects.equals(
            this.inspectionParameterCode,
            paramInterfacesUnlinkParamInterfaceRequest.inspectionParameterCode)
        && Objects.equals(
            this.paramInterfaceCode, paramInterfacesUnlinkParamInterfaceRequest.paramInterfaceCode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(inspectionParameterCode, paramInterfaceCode);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ParamInterfacesUnlinkParamInterfaceRequest {\n");
    sb.append("    inspectionParameterCode: ")
        .append(toIndentedString(inspectionParameterCode))
        .append("\n");
    sb.append("    paramInterfaceCode: ").append(toIndentedString(paramInterfaceCode)).append("\n");
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
