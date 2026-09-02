package io.xr.lab.shared.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.constraints.*;
import java.util.*;
import java.util.Objects;
import org.springframework.lang.Nullable;

/** CreateTestRecordRequest */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    date = "2026-09-02T21:47:39.355598900+08:00[Asia/Shanghai]",
    comments = "Generator version: 7.24.0")
public class CreateTestRecordRequest {

  private String sampleId;

  private String parameterCode;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String standardCode;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String requirementCode;

  private String requirement;

  private String result;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String verdict;

  public CreateTestRecordRequest() {
    super();
  }

  /** Constructor with only required parameters */
  public CreateTestRecordRequest(
      String sampleId, String parameterCode, String requirement, String result) {
    this.sampleId = sampleId;
    this.parameterCode = parameterCode;
    this.requirement = requirement;
    this.result = result;
  }

  public CreateTestRecordRequest sampleId(String sampleId) {
    this.sampleId = sampleId;
    return this;
  }

  /**
   * Get sampleId
   *
   * @return sampleId
   */
  @NotNull
  @Schema(name = "sampleId", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("sampleId")
  public String getSampleId() {
    return sampleId;
  }

  @JsonProperty("sampleId")
  public void setSampleId(String sampleId) {
    this.sampleId = sampleId;
  }

  public CreateTestRecordRequest parameterCode(String parameterCode) {
    this.parameterCode = parameterCode;
    return this;
  }

  /**
   * Get parameterCode
   *
   * @return parameterCode
   */
  @NotNull
  @Schema(name = "parameterCode", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("parameterCode")
  public String getParameterCode() {
    return parameterCode;
  }

  @JsonProperty("parameterCode")
  public void setParameterCode(String parameterCode) {
    this.parameterCode = parameterCode;
  }

  public CreateTestRecordRequest standardCode(@Nullable String standardCode) {
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

  public CreateTestRecordRequest requirementCode(@Nullable String requirementCode) {
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

  public CreateTestRecordRequest requirement(String requirement) {
    this.requirement = requirement;
    return this;
  }

  /**
   * Get requirement
   *
   * @return requirement
   */
  @NotNull
  @Schema(name = "requirement", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("requirement")
  public String getRequirement() {
    return requirement;
  }

  @JsonProperty("requirement")
  public void setRequirement(String requirement) {
    this.requirement = requirement;
  }

  public CreateTestRecordRequest result(String result) {
    this.result = result;
    return this;
  }

  /**
   * Get result
   *
   * @return result
   */
  @NotNull
  @Schema(name = "result", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("result")
  public String getResult() {
    return result;
  }

  @JsonProperty("result")
  public void setResult(String result) {
    this.result = result;
  }

  public CreateTestRecordRequest verdict(@Nullable String verdict) {
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
    CreateTestRecordRequest createTestRecordRequest = (CreateTestRecordRequest) o;
    return Objects.equals(this.sampleId, createTestRecordRequest.sampleId)
        && Objects.equals(this.parameterCode, createTestRecordRequest.parameterCode)
        && Objects.equals(this.standardCode, createTestRecordRequest.standardCode)
        && Objects.equals(this.requirementCode, createTestRecordRequest.requirementCode)
        && Objects.equals(this.requirement, createTestRecordRequest.requirement)
        && Objects.equals(this.result, createTestRecordRequest.result)
        && Objects.equals(this.verdict, createTestRecordRequest.verdict);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        sampleId, parameterCode, standardCode, requirementCode, requirement, result, verdict);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreateTestRecordRequest {\n");
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
