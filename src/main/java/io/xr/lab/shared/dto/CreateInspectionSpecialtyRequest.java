package io.xr.lab.shared.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.constraints.*;
import java.util.*;
import java.util.Objects;
import org.springframework.lang.Nullable;

/** CreateInspectionSpecialtyRequest */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    date = "2026-08-18T09:31:54.550738400+08:00[Asia/Shanghai]",
    comments = "Generator version: 7.24.0")
public class CreateInspectionSpecialtyRequest {

  private String code;

  private String officialNo;

  private String name;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable Boolean isOfficial;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable Boolean enabled;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable Integer sortOrder;

  public CreateInspectionSpecialtyRequest() {
    super();
  }

  /** Constructor with only required parameters */
  public CreateInspectionSpecialtyRequest(String code, String officialNo, String name) {
    this.code = code;
    this.officialNo = officialNo;
    this.name = name;
  }

  public CreateInspectionSpecialtyRequest code(String code) {
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

  public CreateInspectionSpecialtyRequest officialNo(String officialNo) {
    this.officialNo = officialNo;
    return this;
  }

  /**
   * Get officialNo
   *
   * @return officialNo
   */
  @NotNull
  @Schema(name = "officialNo", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("officialNo")
  public String getOfficialNo() {
    return officialNo;
  }

  @JsonProperty("officialNo")
  public void setOfficialNo(String officialNo) {
    this.officialNo = officialNo;
  }

  public CreateInspectionSpecialtyRequest name(String name) {
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

  public CreateInspectionSpecialtyRequest isOfficial(@Nullable Boolean isOfficial) {
    this.isOfficial = isOfficial;
    return this;
  }

  /**
   * Get isOfficial
   *
   * @return isOfficial
   */
  @Schema(name = "isOfficial", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("isOfficial")
  public @Nullable Boolean getIsOfficial() {
    return isOfficial;
  }

  @JsonProperty("isOfficial")
  public void setIsOfficial(@Nullable Boolean isOfficial) {
    this.isOfficial = isOfficial;
  }

  public CreateInspectionSpecialtyRequest enabled(@Nullable Boolean enabled) {
    this.enabled = enabled;
    return this;
  }

  /**
   * Get enabled
   *
   * @return enabled
   */
  @Schema(name = "enabled", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("enabled")
  public @Nullable Boolean getEnabled() {
    return enabled;
  }

  @JsonProperty("enabled")
  public void setEnabled(@Nullable Boolean enabled) {
    this.enabled = enabled;
  }

  public CreateInspectionSpecialtyRequest sortOrder(@Nullable Integer sortOrder) {
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
    CreateInspectionSpecialtyRequest createInspectionSpecialtyRequest =
        (CreateInspectionSpecialtyRequest) o;
    return Objects.equals(this.code, createInspectionSpecialtyRequest.code)
        && Objects.equals(this.officialNo, createInspectionSpecialtyRequest.officialNo)
        && Objects.equals(this.name, createInspectionSpecialtyRequest.name)
        && Objects.equals(this.isOfficial, createInspectionSpecialtyRequest.isOfficial)
        && Objects.equals(this.enabled, createInspectionSpecialtyRequest.enabled)
        && Objects.equals(this.sortOrder, createInspectionSpecialtyRequest.sortOrder);
  }

  @Override
  public int hashCode() {
    return Objects.hash(code, officialNo, name, isOfficial, enabled, sortOrder);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreateInspectionSpecialtyRequest {\n");
    sb.append("    code: ").append(toIndentedString(code)).append("\n");
    sb.append("    officialNo: ").append(toIndentedString(officialNo)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    isOfficial: ").append(toIndentedString(isOfficial)).append("\n");
    sb.append("    enabled: ").append(toIndentedString(enabled)).append("\n");
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
