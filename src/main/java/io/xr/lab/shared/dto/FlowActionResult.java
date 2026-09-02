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

/** FlowActionResult */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    date = "2026-09-02T22:35:42.457326500+08:00[Asia/Shanghai]",
    comments = "Generator version: 7.24.0")
public class FlowActionResult {

  private String id;

  private Boolean ok;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String message;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable FlowStatus flowStatus;

  public FlowActionResult() {
    super();
  }

  /** Constructor with only required parameters */
  public FlowActionResult(String id, Boolean ok) {
    this.id = id;
    this.ok = ok;
  }

  public FlowActionResult id(String id) {
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

  public FlowActionResult ok(Boolean ok) {
    this.ok = ok;
    return this;
  }

  /**
   * Get ok
   *
   * @return ok
   */
  @NotNull
  @Schema(name = "ok", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("ok")
  public Boolean getOk() {
    return ok;
  }

  @JsonProperty("ok")
  public void setOk(Boolean ok) {
    this.ok = ok;
  }

  public FlowActionResult message(@Nullable String message) {
    this.message = message;
    return this;
  }

  /**
   * Get message
   *
   * @return message
   */
  @Schema(name = "message", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("message")
  public @Nullable String getMessage() {
    return message;
  }

  @JsonProperty("message")
  public void setMessage(@Nullable String message) {
    this.message = message;
  }

  public FlowActionResult flowStatus(@Nullable FlowStatus flowStatus) {
    this.flowStatus = flowStatus;
    return this;
  }

  /**
   * Get flowStatus
   *
   * @return flowStatus
   */
  @Valid
  @Schema(name = "flowStatus", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("flowStatus")
  public @Nullable FlowStatus getFlowStatus() {
    return flowStatus;
  }

  @JsonProperty("flowStatus")
  public void setFlowStatus(@Nullable FlowStatus flowStatus) {
    this.flowStatus = flowStatus;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FlowActionResult flowActionResult = (FlowActionResult) o;
    return Objects.equals(this.id, flowActionResult.id)
        && Objects.equals(this.ok, flowActionResult.ok)
        && Objects.equals(this.message, flowActionResult.message)
        && Objects.equals(this.flowStatus, flowActionResult.flowStatus);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, ok, message, flowStatus);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class FlowActionResult {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    ok: ").append(toIndentedString(ok)).append("\n");
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
    sb.append("    flowStatus: ").append(toIndentedString(flowStatus)).append("\n");
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
