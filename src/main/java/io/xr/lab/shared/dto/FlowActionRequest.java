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

/** FlowActionRequest */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    date = "2026-08-20T13:31:51.674991500+08:00[Asia/Shanghai]",
    comments = "Generator version: 7.24.0")
public class FlowActionRequest {

  private List<String> ids = new ArrayList<>();

  private FlowAction action;

  private String operator;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String reason;

  public FlowActionRequest() {
    super();
  }

  /** Constructor with only required parameters */
  public FlowActionRequest(List<String> ids, FlowAction action, String operator) {
    this.ids = ids;
    this.action = action;
    this.operator = operator;
  }

  public FlowActionRequest ids(List<String> ids) {
    this.ids = ids;
    return this;
  }

  public FlowActionRequest addIdsItem(String idsItem) {
    if (this.ids == null) {
      this.ids = new ArrayList<>();
    }
    this.ids.add(idsItem);
    return this;
  }

  /**
   * Get ids
   *
   * @return ids
   */
  @NotNull
  @Schema(name = "ids", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("ids")
  public List<String> getIds() {
    return ids;
  }

  @JsonProperty("ids")
  public void setIds(List<String> ids) {
    this.ids = ids;
  }

  public FlowActionRequest action(FlowAction action) {
    this.action = action;
    return this;
  }

  /**
   * Get action
   *
   * @return action
   */
  @NotNull
  @Valid
  @Schema(name = "action", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("action")
  public FlowAction getAction() {
    return action;
  }

  @JsonProperty("action")
  public void setAction(FlowAction action) {
    this.action = action;
  }

  public FlowActionRequest operator(String operator) {
    this.operator = operator;
    return this;
  }

  /**
   * Get operator
   *
   * @return operator
   */
  @NotNull
  @Schema(name = "operator", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("operator")
  public String getOperator() {
    return operator;
  }

  @JsonProperty("operator")
  public void setOperator(String operator) {
    this.operator = operator;
  }

  public FlowActionRequest reason(@Nullable String reason) {
    this.reason = reason;
    return this;
  }

  /**
   * Get reason
   *
   * @return reason
   */
  @Schema(name = "reason", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("reason")
  public @Nullable String getReason() {
    return reason;
  }

  @JsonProperty("reason")
  public void setReason(@Nullable String reason) {
    this.reason = reason;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FlowActionRequest flowActionRequest = (FlowActionRequest) o;
    return Objects.equals(this.ids, flowActionRequest.ids)
        && Objects.equals(this.action, flowActionRequest.action)
        && Objects.equals(this.operator, flowActionRequest.operator)
        && Objects.equals(this.reason, flowActionRequest.reason);
  }

  @Override
  public int hashCode() {
    return Objects.hash(ids, action, operator, reason);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class FlowActionRequest {\n");
    sb.append("    ids: ").append(toIndentedString(ids)).append("\n");
    sb.append("    action: ").append(toIndentedString(action)).append("\n");
    sb.append("    operator: ").append(toIndentedString(operator)).append("\n");
    sb.append("    reason: ").append(toIndentedString(reason)).append("\n");
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
