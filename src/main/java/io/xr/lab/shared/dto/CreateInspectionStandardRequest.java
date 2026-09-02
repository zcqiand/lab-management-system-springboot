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

/** CreateInspectionStandardRequest */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    date = "2026-09-02T22:35:42.457326500+08:00[Asia/Shanghai]",
    comments = "Generator version: 7.24.0")
public class CreateInspectionStandardRequest {

  private String code;

  private String name;

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

  public CreateInspectionStandardRequest() {
    super();
  }

  /** Constructor with only required parameters */
  public CreateInspectionStandardRequest(String code, String name) {
    this.code = code;
    this.name = name;
  }

  public CreateInspectionStandardRequest code(String code) {
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

  public CreateInspectionStandardRequest name(String name) {
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

  public CreateInspectionStandardRequest version(@Nullable String version) {
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

  public CreateInspectionStandardRequest status(@Nullable InspectionStandardStatus status) {
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

  public CreateInspectionStandardRequest sourceDocumentId(@Nullable String sourceDocumentId) {
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

  public CreateInspectionStandardRequest sourceHash(@Nullable String sourceHash) {
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

  public CreateInspectionStandardRequest sortOrder(@Nullable Integer sortOrder) {
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
    CreateInspectionStandardRequest createInspectionStandardRequest =
        (CreateInspectionStandardRequest) o;
    return Objects.equals(this.code, createInspectionStandardRequest.code)
        && Objects.equals(this.name, createInspectionStandardRequest.name)
        && Objects.equals(this.version, createInspectionStandardRequest.version)
        && Objects.equals(this.status, createInspectionStandardRequest.status)
        && Objects.equals(this.sourceDocumentId, createInspectionStandardRequest.sourceDocumentId)
        && Objects.equals(this.sourceHash, createInspectionStandardRequest.sourceHash)
        && Objects.equals(this.sortOrder, createInspectionStandardRequest.sortOrder);
  }

  @Override
  public int hashCode() {
    return Objects.hash(code, name, version, status, sourceDocumentId, sourceHash, sortOrder);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreateInspectionStandardRequest {\n");
    sb.append("    code: ").append(toIndentedString(code)).append("\n");
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
