package io.xr.lab.shared.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.constraints.*;
import java.util.*;
import java.util.Objects;
import org.springframework.lang.Nullable;

/** TestRecord */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    date = "2026-08-20T13:31:51.674991500+08:00[Asia/Shanghai]",
    comments = "Generator version: 7.24.0")
public class TestRecord {

  private String id;

  private String tenantId;

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

  private String createdAt;

  private String updatedAt;

  public TestRecord() {
    super();
  }

  /** Constructor with only required parameters */
  public TestRecord(
      String id,
      String tenantId,
      String sampleId,
      String parameterCode,
      String requirement,
      String result,
      String createdAt,
      String updatedAt) {
    this.id = id;
    this.tenantId = tenantId;
    this.sampleId = sampleId;
    this.parameterCode = parameterCode;
    this.requirement = requirement;
    this.result = result;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public TestRecord id(String id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   *
   * @return id
   */
  @NotNull
  @Schema(name = "id", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("id")
  public String getId() {
    return id;
  }

  @JsonProperty("id")
  public void setId(String id) {
    this.id = id;
  }

  public TestRecord tenantId(String tenantId) {
    this.tenantId = tenantId;
    return this;
  }

  /**
   * Get tenantId
   *
   * @return tenantId
   */
  @NotNull
  @Schema(name = "tenantId", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("tenantId")
  public String getTenantId() {
    return tenantId;
  }

  @JsonProperty("tenantId")
  public void setTenantId(String tenantId) {
    this.tenantId = tenantId;
  }

  public TestRecord sampleId(String sampleId) {
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

  public TestRecord parameterCode(String parameterCode) {
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

  public TestRecord standardCode(@Nullable String standardCode) {
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

  public TestRecord requirementCode(@Nullable String requirementCode) {
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

  public TestRecord requirement(String requirement) {
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

  public TestRecord result(String result) {
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

  public TestRecord verdict(@Nullable String verdict) {
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

  public TestRecord createdAt(String createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  /**
   * Get createdAt
   *
   * @return createdAt
   */
  @NotNull
  @Schema(name = "createdAt", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("createdAt")
  public String getCreatedAt() {
    return createdAt;
  }

  @JsonProperty("createdAt")
  public void setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
  }

  public TestRecord updatedAt(String updatedAt) {
    this.updatedAt = updatedAt;
    return this;
  }

  /**
   * Get updatedAt
   *
   * @return updatedAt
   */
  @NotNull
  @Schema(name = "updatedAt", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("updatedAt")
  public String getUpdatedAt() {
    return updatedAt;
  }

  @JsonProperty("updatedAt")
  public void setUpdatedAt(String updatedAt) {
    this.updatedAt = updatedAt;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TestRecord testRecord = (TestRecord) o;
    return Objects.equals(this.id, testRecord.id)
        && Objects.equals(this.tenantId, testRecord.tenantId)
        && Objects.equals(this.sampleId, testRecord.sampleId)
        && Objects.equals(this.parameterCode, testRecord.parameterCode)
        && Objects.equals(this.standardCode, testRecord.standardCode)
        && Objects.equals(this.requirementCode, testRecord.requirementCode)
        && Objects.equals(this.requirement, testRecord.requirement)
        && Objects.equals(this.result, testRecord.result)
        && Objects.equals(this.verdict, testRecord.verdict)
        && Objects.equals(this.createdAt, testRecord.createdAt)
        && Objects.equals(this.updatedAt, testRecord.updatedAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        id,
        tenantId,
        sampleId,
        parameterCode,
        standardCode,
        requirementCode,
        requirement,
        result,
        verdict,
        createdAt,
        updatedAt);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TestRecord {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    tenantId: ").append(toIndentedString(tenantId)).append("\n");
    sb.append("    sampleId: ").append(toIndentedString(sampleId)).append("\n");
    sb.append("    parameterCode: ").append(toIndentedString(parameterCode)).append("\n");
    sb.append("    standardCode: ").append(toIndentedString(standardCode)).append("\n");
    sb.append("    requirementCode: ").append(toIndentedString(requirementCode)).append("\n");
    sb.append("    requirement: ").append(toIndentedString(requirement)).append("\n");
    sb.append("    result: ").append(toIndentedString(result)).append("\n");
    sb.append("    verdict: ").append(toIndentedString(verdict)).append("\n");
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
    sb.append("    updatedAt: ").append(toIndentedString(updatedAt)).append("\n");
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
