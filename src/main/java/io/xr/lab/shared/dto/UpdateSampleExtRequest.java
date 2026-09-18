package io.xr.lab.shared.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.constraints.*;
import java.util.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.springframework.lang.Nullable;

/** UpdateSampleExtRequest */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    date = "2026-09-18T22:23:36.149543100+08:00[Asia/Shanghai]",
    comments = "Generator version: 7.24.0")
public class UpdateSampleExtRequest {

  private Map<String, String> ext = new HashMap<>();

  public UpdateSampleExtRequest() {
    super();
  }

  /** Constructor with only required parameters */
  public UpdateSampleExtRequest(Map<String, String> ext) {
    this.ext = ext;
  }

  public UpdateSampleExtRequest ext(Map<String, String> ext) {
    this.ext = ext;
    return this;
  }

  public UpdateSampleExtRequest putExtItem(String key, String extItem) {
    if (this.ext == null) {
      this.ext = new HashMap<>();
    }
    this.ext.put(key, extItem);
    return this;
  }

  /**
   * Get ext
   *
   * @return ext
   */
  @NotNull
  @Schema(name = "ext", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("ext")
  public Map<String, String> getExt() {
    return ext;
  }

  @JsonProperty("ext")
  public void setExt(Map<String, String> ext) {
    this.ext = ext;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UpdateSampleExtRequest updateSampleExtRequest = (UpdateSampleExtRequest) o;
    return Objects.equals(this.ext, updateSampleExtRequest.ext);
  }

  @Override
  public int hashCode() {
    return Objects.hash(ext);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UpdateSampleExtRequest {\n");
    sb.append("    ext: ").append(toIndentedString(ext)).append("\n");
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
