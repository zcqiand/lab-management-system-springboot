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

/** InspectionStandard */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    date = "2026-08-20T13:31:51.674991500+08:00[Asia/Shanghai]",
    comments = "Generator version: 7.24.0")
public class InspectionStandard {

  private String code;

  private String name;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String version;

  private InspectionStandardStatus status;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String sourceDocumentId;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String sourceHash;

  private Integer sortOrder;

  private String createdAt;

  private String updatedAt;

  public InspectionStandard() {
    super();
  }

  /** Constructor with only required parameters */
  public InspectionStandard(
      String code,
      String name,
      InspectionStandardStatus status,
      Integer sortOrder,
      String createdAt,
      String updatedAt) {
    this.code = code;
    this.name = name;
    this.status = status;
    this.sortOrder = sortOrder;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public InspectionStandard code(String code) {
    this.code = code;
    return this;
  }

  /**
   * Get code
   *
   * @return code
   */
  @NotNull
  @Schema(name = "code", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("code")
  public String getCode() {
    return code;
  }

  @JsonProperty("code")
  public void setCode(String code) {
    this.code = code;
  }

  public InspectionStandard name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   *
   * @return name
   */
  @NotNull
  @Schema(name = "name", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("name")
  public String getName() {
    return name;
  }

  @JsonProperty("name")
  public void setName(String name) {
    this.name = name;
  }

  public InspectionStandard version(@Nullable String version) {
    this.version = version;
    return this;
  }

  /**
   * Get version
   *
   * @return version
   */
  @Schema(name = "version", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("version")
  public @Nullable String getVersion() {
    return version;
  }

  @JsonProperty("version")
  public void setVersion(@Nullable String version) {
    this.version = version;
  }

  public InspectionStandard status(InspectionStandardStatus status) {
    this.status = status;
    return this;
  }

  /**
   * Get status
   *
   * @return status
   */
  @NotNull
  @Valid
  @Schema(name = "status", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("status")
  public InspectionStandardStatus getStatus() {
    return status;
  }

  @JsonProperty("status")
  public void setStatus(InspectionStandardStatus status) {
    this.status = status;
  }

  public InspectionStandard sourceDocumentId(@Nullable String sourceDocumentId) {
    this.sourceDocumentId = sourceDocumentId;
    return this;
  }

  /**
   * Get sourceDocumentId
   *
   * @return sourceDocumentId
   */
  @Schema(name = "sourceDocumentId", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("sourceDocumentId")
  public @Nullable String getSourceDocumentId() {
    return sourceDocumentId;
  }

  @JsonProperty("sourceDocumentId")
  public void setSourceDocumentId(@Nullable String sourceDocumentId) {
    this.sourceDocumentId = sourceDocumentId;
  }

  public InspectionStandard sourceHash(@Nullable String sourceHash) {
    this.sourceHash = sourceHash;
    return this;
  }

  /**
   * Get sourceHash
   *
   * @return sourceHash
   */
  @Schema(name = "sourceHash", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("sourceHash")
  public @Nullable String getSourceHash() {
    return sourceHash;
  }

  @JsonProperty("sourceHash")
  public void setSourceHash(@Nullable String sourceHash) {
    this.sourceHash = sourceHash;
  }

  public InspectionStandard sortOrder(Integer sortOrder) {
    this.sortOrder = sortOrder;
    return this;
  }

  /**
   * Get sortOrder
   *
   * @return sortOrder
   */
  @NotNull
  @Schema(name = "sortOrder", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("sortOrder")
  public Integer getSortOrder() {
    return sortOrder;
  }

  @JsonProperty("sortOrder")
  public void setSortOrder(Integer sortOrder) {
    this.sortOrder = sortOrder;
  }

  public InspectionStandard createdAt(String createdAt) {
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

  public InspectionStandard updatedAt(String updatedAt) {
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
    InspectionStandard inspectionStandard = (InspectionStandard) o;
    return Objects.equals(this.code, inspectionStandard.code)
        && Objects.equals(this.name, inspectionStandard.name)
        && Objects.equals(this.version, inspectionStandard.version)
        && Objects.equals(this.status, inspectionStandard.status)
        && Objects.equals(this.sourceDocumentId, inspectionStandard.sourceDocumentId)
        && Objects.equals(this.sourceHash, inspectionStandard.sourceHash)
        && Objects.equals(this.sortOrder, inspectionStandard.sortOrder)
        && Objects.equals(this.createdAt, inspectionStandard.createdAt)
        && Objects.equals(this.updatedAt, inspectionStandard.updatedAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        code, name, version, status, sourceDocumentId, sourceHash, sortOrder, createdAt, updatedAt);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InspectionStandard {\n");
    sb.append("    code: ").append(toIndentedString(code)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    version: ").append(toIndentedString(version)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    sourceDocumentId: ").append(toIndentedString(sourceDocumentId)).append("\n");
    sb.append("    sourceHash: ").append(toIndentedString(sourceHash)).append("\n");
    sb.append("    sortOrder: ").append(toIndentedString(sortOrder)).append("\n");
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
