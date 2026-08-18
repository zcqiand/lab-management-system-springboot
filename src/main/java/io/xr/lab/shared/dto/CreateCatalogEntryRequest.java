package io.xr.lab.shared.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.constraints.*;
import java.util.*;
import java.util.Objects;
import org.springframework.lang.Nullable;

/** CreateCatalogEntryRequest */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    date = "2026-08-18T09:31:54.550738400+08:00[Asia/Shanghai]",
    comments = "Generator version: 7.24.0")
public class CreateCatalogEntryRequest {

  private String code;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String inspectionObjectCode;

  private String name;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String remark;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable Integer sortOrder;

  public CreateCatalogEntryRequest() {
    super();
  }

  /** Constructor with only required parameters */
  public CreateCatalogEntryRequest(String code, String name) {
    this.code = code;
    this.name = name;
  }

  public CreateCatalogEntryRequest code(String code) {
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

  public CreateCatalogEntryRequest inspectionObjectCode(@Nullable String inspectionObjectCode) {
    this.inspectionObjectCode = inspectionObjectCode;
    return this;
  }

  /**
   * Get inspectionObjectCode
   *
   * @return inspectionObjectCode
   */
  @Schema(name = "inspectionObjectCode", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("inspectionObjectCode")
  public @Nullable String getInspectionObjectCode() {
    return inspectionObjectCode;
  }

  @JsonProperty("inspectionObjectCode")
  public void setInspectionObjectCode(@Nullable String inspectionObjectCode) {
    this.inspectionObjectCode = inspectionObjectCode;
  }

  public CreateCatalogEntryRequest name(String name) {
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

  public CreateCatalogEntryRequest remark(@Nullable String remark) {
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

  public CreateCatalogEntryRequest sortOrder(@Nullable Integer sortOrder) {
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
    CreateCatalogEntryRequest createCatalogEntryRequest = (CreateCatalogEntryRequest) o;
    return Objects.equals(this.code, createCatalogEntryRequest.code)
        && Objects.equals(this.inspectionObjectCode, createCatalogEntryRequest.inspectionObjectCode)
        && Objects.equals(this.name, createCatalogEntryRequest.name)
        && Objects.equals(this.remark, createCatalogEntryRequest.remark)
        && Objects.equals(this.sortOrder, createCatalogEntryRequest.sortOrder);
  }

  @Override
  public int hashCode() {
    return Objects.hash(code, inspectionObjectCode, name, remark, sortOrder);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreateCatalogEntryRequest {\n");
    sb.append("    code: ").append(toIndentedString(code)).append("\n");
    sb.append("    inspectionObjectCode: ")
        .append(toIndentedString(inspectionObjectCode))
        .append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    remark: ").append(toIndentedString(remark)).append("\n");
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
