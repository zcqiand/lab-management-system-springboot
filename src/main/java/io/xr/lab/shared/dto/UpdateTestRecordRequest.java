package io.xr.lab.shared.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.constraints.*;
import java.util.*;
import java.util.Objects;
import org.springframework.lang.Nullable;

/** UpdateTestRecordRequest */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    date = "2026-08-19T17:37:44.319774200+08:00[Asia/Shanghai]",
    comments = "Generator version: 7.24.0")
public class UpdateTestRecordRequest {

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String sampleId;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String parameterCode;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String standardCode;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String requirementCode;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String requirement;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String result;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String verdict;

  public UpdateTestRecordRequest sampleId(@Nullable String sampleId) {
    this.sampleId = sampleId;
    return this;
  }

  /**
   * Get sampleId
   *
   * @return sampleId
   */
  @Schema(name = "sampleId", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("sampleId")
  public @Nullable String getSampleId() {
    return sampleId;
  }

  @JsonProperty("sampleId")
  public void setSampleId(@Nullable String sampleId) {
    this.sampleId = sampleId;
  }

  public UpdateTestRecordRequest parameterCode(@Nullable String parameterCode) {
    this.parameterCode = parameterCode;
    return this;
  }

  /**
   * Get parameterCode
   *
   * @return parameterCode
   */
  @Schema(name = "parameterCode", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("parameterCode")
  public @Nullable String getParameterCode() {
    return parameterCode;
  }

  @JsonProperty("parameterCode")
  public void setParameterCode(@Nullable String parameterCode) {
    this.parameterCode = parameterCode;
  }

  public UpdateTestRecordRequest standardCode(@Nullable String standardCode) {
    this.standardCode = standardCode;
    return this;
  }

  /**
   * Get standardCode
   *
   * @return standardCode
   */
  @Schema(name = "standardCode", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("standardCode")
  public @Nullable String getStandardCode() {
    return standardCode;
  }

  @JsonProperty("standardCode")
  public void setStandardCode(@Nullable String standardCode) {
    this.standardCode = standardCode;
  }

  public UpdateTestRecordRequest requirementCode(@Nullable String requirementCode) {
    this.requirementCode = requirementCode;
    return this;
  }

  /**
   * Get requirementCode
   *
   * @return requirementCode
   */
  @Schema(name = "requirementCode", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("requirementCode")
  public @Nullable String getRequirementCode() {
    return requirementCode;
  }

  @JsonProperty("requirementCode")
  public void setRequirementCode(@Nullable String requirementCode) {
    this.requirementCode = requirementCode;
  }

  public UpdateTestRecordRequest requirement(@Nullable String requirement) {
    this.requirement = requirement;
    return this;
  }

  /**
   * Get requirement
   *
   * @return requirement
   */
  @Schema(name = "requirement", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("requirement")
  public @Nullable String getRequirement() {
    return requirement;
  }

  @JsonProperty("requirement")
  public void setRequirement(@Nullable String requirement) {
    this.requirement = requirement;
  }

  public UpdateTestRecordRequest result(@Nullable String result) {
    this.result = result;
    return this;
  }

  /**
   * Get result
   *
   * @return result
   */
  @Schema(name = "result", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("result")
  public @Nullable String getResult() {
    return result;
  }

  @JsonProperty("result")
  public void setResult(@Nullable String result) {
    this.result = result;
  }

  public UpdateTestRecordRequest verdict(@Nullable String verdict) {
    this.verdict = verdict;
    return this;
  }

  /**
   * Get verdict
   *
   * @return verdict
   */
  @Schema(name = "verdict", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("verdict")
  public @Nullable String getVerdict() {
    return verdict;
  }

  @JsonProperty("verdict")
  public void setVerdict(@Nullable String verdict) {
    this.verdict = verdict;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UpdateTestRecordRequest updateTestRecordRequest = (UpdateTestRecordRequest) o;
    return Objects.equals(this.sampleId, updateTestRecordRequest.sampleId)
        && Objects.equals(this.parameterCode, updateTestRecordRequest.parameterCode)
        && Objects.equals(this.standardCode, updateTestRecordRequest.standardCode)
        && Objects.equals(this.requirementCode, updateTestRecordRequest.requirementCode)
        && Objects.equals(this.requirement, updateTestRecordRequest.requirement)
        && Objects.equals(this.result, updateTestRecordRequest.result)
        && Objects.equals(this.verdict, updateTestRecordRequest.verdict);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        sampleId, parameterCode, standardCode, requirementCode, requirement, result, verdict);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UpdateTestRecordRequest {\n");
    sb.append("    sampleId: ").append(toIndentedString(sampleId)).append("\n");
    sb.append("    parameterCode: ").append(toIndentedString(parameterCode)).append("\n");
    sb.append("    standardCode: ").append(toIndentedString(standardCode)).append("\n");
    sb.append("    requirementCode: ").append(toIndentedString(requirementCode)).append("\n");
    sb.append("    requirement: ").append(toIndentedString(requirement)).append("\n");
    sb.append("    result: ").append(toIndentedString(result)).append("\n");
    sb.append("    verdict: ").append(toIndentedString(verdict)).append("\n");
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
