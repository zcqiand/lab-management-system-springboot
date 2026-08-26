package io.xr.lab.shared.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.lang.Nullable;

/** ExtFieldDef */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    date = "2026-08-26T12:43:04.549030500+08:00[Asia/Shanghai]",
    comments = "Generator version: 7.24.0")
public class ExtFieldDef {

  private String key;

  private String label;

  private ExtFieldDefType type;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable Boolean required;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private List<String> options = new ArrayList<>();

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String tag;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable ExtFieldDefSource source;

  public ExtFieldDef() {
    super();
  }

  /** Constructor with only required parameters */
  public ExtFieldDef(String key, String label, ExtFieldDefType type) {
    this.key = key;
    this.label = label;
    this.type = type;
  }

  public ExtFieldDef key(String key) {
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

  public ExtFieldDef label(String label) {
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

  public ExtFieldDef type(ExtFieldDefType type) {
    this.type = type;
    return this;
  }

  /**
   * Get type
   *
   * @return type
   */
  @NotNull
  @Valid
  @Schema(name = "type", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("type")
  public ExtFieldDefType getType() {
    return type;
  }

  @JsonProperty("type")
  public void setType(ExtFieldDefType type) {
    this.type = type;
  }

  public ExtFieldDef required(@Nullable Boolean required) {
    this.required = required;
    return this;
  }

  /**
   * Get required
   *
   * @return required
   */
  @Schema(name = "required", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("required")
  public @Nullable Boolean getRequired() {
    return required;
  }

  @JsonProperty("required")
  public void setRequired(@Nullable Boolean required) {
    this.required = required;
  }

  public ExtFieldDef options(List<String> options) {
    this.options = options;
    return this;
  }

  public ExtFieldDef addOptionsItem(String optionsItem) {
    if (this.options == null) {
      this.options = new ArrayList<>();
    }
    this.options.add(optionsItem);
    return this;
  }

  /**
   * Get options
   *
   * @return options
   */
  @Schema(name = "options", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("options")
  public List<String> getOptions() {
    return options;
  }

  @JsonProperty("options")
  public void setOptions(List<String> options) {
    this.options = options;
  }

  public ExtFieldDef tag(@Nullable String tag) {
    this.tag = tag;
    return this;
  }

  /**
   * Get tag
   *
   * @return tag
   */
  @Schema(name = "tag", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("tag")
  public @Nullable String getTag() {
    return tag;
  }

  @JsonProperty("tag")
  public void setTag(@Nullable String tag) {
    this.tag = tag;
  }

  public ExtFieldDef source(@Nullable ExtFieldDefSource source) {
    this.source = source;
    return this;
  }

  /**
   * Get source
   *
   * @return source
   */
  @Valid
  @Schema(name = "source", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("source")
  public @Nullable ExtFieldDefSource getSource() {
    return source;
  }

  @JsonProperty("source")
  public void setSource(@Nullable ExtFieldDefSource source) {
    this.source = source;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ExtFieldDef extFieldDef = (ExtFieldDef) o;
    return Objects.equals(this.key, extFieldDef.key)
        && Objects.equals(this.label, extFieldDef.label)
        && Objects.equals(this.type, extFieldDef.type)
        && Objects.equals(this.required, extFieldDef.required)
        && Objects.equals(this.options, extFieldDef.options)
        && Objects.equals(this.tag, extFieldDef.tag)
        && Objects.equals(this.source, extFieldDef.source);
  }

  @Override
  public int hashCode() {
    return Objects.hash(key, label, type, required, options, tag, source);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ExtFieldDef {\n");
    sb.append("    key: ").append(toIndentedString(key)).append("\n");
    sb.append("    label: ").append(toIndentedString(label)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    required: ").append(toIndentedString(required)).append("\n");
    sb.append("    options: ").append(toIndentedString(options)).append("\n");
    sb.append("    tag: ").append(toIndentedString(tag)).append("\n");
    sb.append("    source: ").append(toIndentedString(source)).append("\n");
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
