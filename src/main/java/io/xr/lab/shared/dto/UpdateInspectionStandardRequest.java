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

/** UpdateInspectionStandardRequest */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    date = "2026-09-02T22:35:42.457326500+08:00[Asia/Shanghai]",
    comments = "Generator version: 7.24.0")
public class UpdateInspectionStandardRequest {

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String name;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String version;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable InspectionStandardStatus status;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String sourceDocumentId;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String sourceHash;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable Integer sortOrder;

  public UpdateInspectionStandardRequest name(@Nullable String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   *
   * @return name
   */
  @Schema(name = "name", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("name")
  public @Nullable String getName() {
    return name;
  }

  @JsonProperty("name")
  public void setName(@Nullable String name) {
    this.name = name;
  }

  public UpdateInspectionStandardRequest version(@Nullable String version) {
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

  public UpdateInspectionStandardRequest status(@Nullable InspectionStandardStatus status) {
    this.status = status;
    return this;
  }

  /**
   * Get status
   *
   * @return status
   */
  @Valid
  @Schema(name = "status", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("status")
  public @Nullable InspectionStandardStatus getStatus() {
    return status;
  }

  @JsonProperty("status")
  public void setStatus(@Nullable InspectionStandardStatus status) {
    this.status = status;
  }

  public UpdateInspectionStandardRequest sourceDocumentId(@Nullable String sourceDocumentId) {
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

  public UpdateInspectionStandardRequest sourceHash(@Nullable String sourceHash) {
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

  public UpdateInspectionStandardRequest sortOrder(@Nullable Integer sortOrder) {
    this.sortOrder = sortOrder;
    return this;
  }

  /**
   * Get sortOrder
   *
   * @return sortOrder
   */
  @Schema(name = "sortOrder", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("sortOrder")
  public @Nullable Integer getSortOrder() {
    return sortOrder;
  }

  @JsonProperty("sortOrder")
  public void setSortOrder(@Nullable Integer sortOrder) {
    this.sortOrder = sortOrder;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UpdateInspectionStandardRequest updateInspectionStandardRequest =
        (UpdateInspectionStandardRequest) o;
    return Objects.equals(this.name, updateInspectionStandardRequest.name)
        && Objects.equals(this.version, updateInspectionStandardRequest.version)
        && Objects.equals(this.status, updateInspectionStandardRequest.status)
        && Objects.equals(this.sourceDocumentId, updateInspectionStandardRequest.sourceDocumentId)
        && Objects.equals(this.sourceHash, updateInspectionStandardRequest.sourceHash)
        && Objects.equals(this.sortOrder, updateInspectionStandardRequest.sortOrder);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, version, status, sourceDocumentId, sourceHash, sortOrder);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UpdateInspectionStandardRequest {\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    version: ").append(toIndentedString(version)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    sourceDocumentId: ").append(toIndentedString(sourceDocumentId)).append("\n");
    sb.append("    sourceHash: ").append(toIndentedString(sourceHash)).append("\n");
    sb.append("    sortOrder: ").append(toIndentedString(sortOrder)).append("\n");
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
