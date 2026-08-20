package io.xr.lab.shared.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.constraints.*;
import java.util.*;
import java.util.Objects;
import org.springframework.lang.Nullable;

/** InspectionSpecialty */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    date = "2026-08-20T13:31:51.674991500+08:00[Asia/Shanghai]",
    comments = "Generator version: 7.24.0")
public class InspectionSpecialty {

  private String code;

  private String officialNo;

  private String name;

  private Boolean isOfficial;

  private Boolean enabled;

  private Integer sortOrder;

  private String createdAt;

  private String updatedAt;

  public InspectionSpecialty() {
    super();
  }

  /** Constructor with only required parameters */
  public InspectionSpecialty(
      String code,
      String officialNo,
      String name,
      Boolean isOfficial,
      Boolean enabled,
      Integer sortOrder,
      String createdAt,
      String updatedAt) {
    this.code = code;
    this.officialNo = officialNo;
    this.name = name;
    this.isOfficial = isOfficial;
    this.enabled = enabled;
    this.sortOrder = sortOrder;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public InspectionSpecialty code(String code) {
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

  public InspectionSpecialty officialNo(String officialNo) {
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

  public InspectionSpecialty name(String name) {
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

  public InspectionSpecialty isOfficial(Boolean isOfficial) {
    this.isOfficial = isOfficial;
    return this;
  }

  /**
   * Get isOfficial
   *
   * @return isOfficial
   */
  @NotNull
  @Schema(name = "isOfficial", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("isOfficial")
  public Boolean getIsOfficial() {
    return isOfficial;
  }

  @JsonProperty("isOfficial")
  public void setIsOfficial(Boolean isOfficial) {
    this.isOfficial = isOfficial;
  }

  public InspectionSpecialty enabled(Boolean enabled) {
    this.enabled = enabled;
    return this;
  }

  /**
   * Get enabled
   *
   * @return enabled
   */
  @NotNull
  @Schema(name = "enabled", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("enabled")
  public Boolean getEnabled() {
    return enabled;
  }

  @JsonProperty("enabled")
  public void setEnabled(Boolean enabled) {
    this.enabled = enabled;
  }

  public InspectionSpecialty sortOrder(Integer sortOrder) {
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

  public InspectionSpecialty createdAt(String createdAt) {
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

  public InspectionSpecialty updatedAt(String updatedAt) {
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
    InspectionSpecialty inspectionSpecialty = (InspectionSpecialty) o;
    return Objects.equals(this.code, inspectionSpecialty.code)
        && Objects.equals(this.officialNo, inspectionSpecialty.officialNo)
        && Objects.equals(this.name, inspectionSpecialty.name)
        && Objects.equals(this.isOfficial, inspectionSpecialty.isOfficial)
        && Objects.equals(this.enabled, inspectionSpecialty.enabled)
        && Objects.equals(this.sortOrder, inspectionSpecialty.sortOrder)
        && Objects.equals(this.createdAt, inspectionSpecialty.createdAt)
        && Objects.equals(this.updatedAt, inspectionSpecialty.updatedAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        code, officialNo, name, isOfficial, enabled, sortOrder, createdAt, updatedAt);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InspectionSpecialty {\n");
    sb.append("    code: ").append(toIndentedString(code)).append("\n");
    sb.append("    officialNo: ").append(toIndentedString(officialNo)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    isOfficial: ").append(toIndentedString(isOfficial)).append("\n");
    sb.append("    enabled: ").append(toIndentedString(enabled)).append("\n");
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
