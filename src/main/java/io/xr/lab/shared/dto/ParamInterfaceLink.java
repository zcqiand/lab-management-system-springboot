package io.xr.lab.shared.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.constraints.*;
import java.util.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.springframework.lang.Nullable;

/** ParamInterfaceLink */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    date = "2026-08-26T12:43:04.549030500+08:00[Asia/Shanghai]",
    comments = "Generator version: 7.24.0")
public class ParamInterfaceLink {

  private String inspectionParameterCode;

  private String paramInterfaceCode;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String reportNameCode;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private Map<String, Object> config = new HashMap<>();

  public ParamInterfaceLink() {
    super();
  }

  /** Constructor with only required parameters */
  public ParamInterfaceLink(String inspectionParameterCode, String paramInterfaceCode) {
    this.inspectionParameterCode = inspectionParameterCode;
    this.paramInterfaceCode = paramInterfaceCode;
  }

  public ParamInterfaceLink inspectionParameterCode(String inspectionParameterCode) {
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

  public ParamInterfaceLink paramInterfaceCode(String paramInterfaceCode) {
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

  public ParamInterfaceLink reportNameCode(@Nullable String reportNameCode) {
    this.reportNameCode = reportNameCode;
    return this;
  }

  /**
   * Get reportNameCode
   *
   * @return reportNameCode
   */
  @Schema(name = "reportNameCode", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("reportNameCode")
  public @Nullable String getReportNameCode() {
    return reportNameCode;
  }

  @JsonProperty("reportNameCode")
  public void setReportNameCode(@Nullable String reportNameCode) {
    this.reportNameCode = reportNameCode;
  }

  public ParamInterfaceLink config(Map<String, Object> config) {
    this.config = config;
    return this;
  }

  public ParamInterfaceLink putConfigItem(String key, Object configItem) {
    if (this.config == null) {
      this.config = new HashMap<>();
    }
    this.config.put(key, configItem);
    return this;
  }

  /**
   * Get config
   *
   * @return config
   */
  @Schema(name = "config", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("config")
  public Map<String, Object> getConfig() {
    return config;
  }

  @JsonProperty("config")
  public void setConfig(Map<String, Object> config) {
    this.config = config;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ParamInterfaceLink paramInterfaceLink = (ParamInterfaceLink) o;
    return Objects.equals(this.inspectionParameterCode, paramInterfaceLink.inspectionParameterCode)
        && Objects.equals(this.paramInterfaceCode, paramInterfaceLink.paramInterfaceCode)
        && Objects.equals(this.reportNameCode, paramInterfaceLink.reportNameCode)
        && Objects.equals(this.config, paramInterfaceLink.config);
  }

  @Override
  public int hashCode() {
    return Objects.hash(inspectionParameterCode, paramInterfaceCode, reportNameCode, config);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ParamInterfaceLink {\n");
    sb.append("    inspectionParameterCode: ")
        .append(toIndentedString(inspectionParameterCode))
        .append("\n");
    sb.append("    paramInterfaceCode: ").append(toIndentedString(paramInterfaceCode)).append("\n");
    sb.append("    reportNameCode: ").append(toIndentedString(reportNameCode)).append("\n");
    sb.append("    config: ").append(toIndentedString(config)).append("\n");
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
