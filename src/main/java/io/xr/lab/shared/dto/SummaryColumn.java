package io.xr.lab.shared.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.constraints.*;
import java.util.*;
import java.util.Objects;
import org.springframework.lang.Nullable;

/** SummaryColumn */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    date = "2026-09-02T21:47:39.355598900+08:00[Asia/Shanghai]",
    comments = "Generator version: 7.24.0")
public class SummaryColumn {

  private String key;

  private String label;

  public SummaryColumn() {
    super();
  }

  /** Constructor with only required parameters */
  public SummaryColumn(String key, String label) {
    this.key = key;
    this.label = label;
  }

  public SummaryColumn key(String key) {
    this.key = key;
    return this;
  }

  /**
   * Get key
   *
   * @return key
   */
  @NotNull
  @Schema(name = "key", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("key")
  public String getKey() {
    return key;
  }

  @JsonProperty("key")
  public void setKey(String key) {
    this.key = key;
  }

  public SummaryColumn label(String label) {
    this.label = label;
    return this;
  }

  /**
   * Get label
   *
   * @return label
   */
  @NotNull
  @Schema(name = "label", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("label")
  public String getLabel() {
    return label;
  }

  @JsonProperty("label")
  public void setLabel(String label) {
    this.label = label;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SummaryColumn summaryColumn = (SummaryColumn) o;
    return Objects.equals(this.key, summaryColumn.key)
        && Objects.equals(this.label, summaryColumn.label);
  }

  @Override
  public int hashCode() {
    return Objects.hash(key, label);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SummaryColumn {\n");
    sb.append("    key: ").append(toIndentedString(key)).append("\n");
    sb.append("    label: ").append(toIndentedString(label)).append("\n");
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
