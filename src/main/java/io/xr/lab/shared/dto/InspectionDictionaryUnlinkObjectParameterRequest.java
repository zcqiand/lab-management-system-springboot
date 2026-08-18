package io.xr.lab.shared.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.constraints.*;
import java.util.*;
import java.util.Objects;
import org.springframework.lang.Nullable;

/** InspectionDictionaryUnlinkObjectParameterRequest */
@JsonTypeName("InspectionDictionary_unlinkObjectParameter_request")
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    date = "2026-08-18T09:31:54.550738400+08:00[Asia/Shanghai]",
    comments = "Generator version: 7.24.0")
public class InspectionDictionaryUnlinkObjectParameterRequest {

  private String inspectionObjectCode;

  private String inspectionParameterCode;

  public InspectionDictionaryUnlinkObjectParameterRequest() {
    super();
  }

  /** Constructor with only required parameters */
  public InspectionDictionaryUnlinkObjectParameterRequest(
      String inspectionObjectCode, String inspectionParameterCode) {
    this.inspectionObjectCode = inspectionObjectCode;
    this.inspectionParameterCode = inspectionParameterCode;
  }

  public InspectionDictionaryUnlinkObjectParameterRequest inspectionObjectCode(
      String inspectionObjectCode) {
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

  public InspectionDictionaryUnlinkObjectParameterRequest inspectionParameterCode(
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InspectionDictionaryUnlinkObjectParameterRequest
        inspectionDictionaryUnlinkObjectParameterRequest =
            (InspectionDictionaryUnlinkObjectParameterRequest) o;
    return Objects.equals(
            this.inspectionObjectCode,
            inspectionDictionaryUnlinkObjectParameterRequest.inspectionObjectCode)
        && Objects.equals(
            this.inspectionParameterCode,
            inspectionDictionaryUnlinkObjectParameterRequest.inspectionParameterCode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(inspectionObjectCode, inspectionParameterCode);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InspectionDictionaryUnlinkObjectParameterRequest {\n");
    sb.append("    inspectionObjectCode: ")
        .append(toIndentedString(inspectionObjectCode))
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
