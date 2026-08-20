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

/** FlowHistoryEntry */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    date = "2026-08-20T13:31:51.674991500+08:00[Asia/Shanghai]",
    comments = "Generator version: 7.24.0")
public class FlowHistoryEntry {

  private FlowAction action;

  private FlowStatus from;

  private FlowStatus to;

  private String operator;

  private String at;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String reason;

  public FlowHistoryEntry() {
    super();
  }

  /** Constructor with only required parameters */
  public FlowHistoryEntry(
      FlowAction action, FlowStatus from, FlowStatus to, String operator, String at) {
    this.action = action;
    this.from = from;
    this.to = to;
    this.operator = operator;
    this.at = at;
  }

  public FlowHistoryEntry action(FlowAction action) {
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

  public FlowHistoryEntry from(FlowStatus from) {
    this.from = from;
    return this;
  }

  /**
   * Get from
   *
   * @return from
   */
  @NotNull
  @Valid
  @Schema(name = "from", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("from")
  public FlowStatus getFrom() {
    return from;
  }

  @JsonProperty("from")
  public void setFrom(FlowStatus from) {
    this.from = from;
  }

  public FlowHistoryEntry to(FlowStatus to) {
    this.to = to;
    return this;
  }

  /**
   * Get to
   *
   * @return to
   */
  @NotNull
  @Valid
  @Schema(name = "to", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("to")
  public FlowStatus getTo() {
    return to;
  }

  @JsonProperty("to")
  public void setTo(FlowStatus to) {
    this.to = to;
  }

  public FlowHistoryEntry operator(String operator) {
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

  public FlowHistoryEntry at(String at) {
    this.at = at;
    return this;
  }

  /**
   * Get at
   *
   * @return at
   */
  @NotNull
  @Schema(name = "at", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("at")
  public String getAt() {
    return at;
  }

  @JsonProperty("at")
  public void setAt(String at) {
    this.at = at;
  }

  public FlowHistoryEntry reason(@Nullable String reason) {
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
    FlowHistoryEntry flowHistoryEntry = (FlowHistoryEntry) o;
    return Objects.equals(this.action, flowHistoryEntry.action)
        && Objects.equals(this.from, flowHistoryEntry.from)
        && Objects.equals(this.to, flowHistoryEntry.to)
        && Objects.equals(this.operator, flowHistoryEntry.operator)
        && Objects.equals(this.at, flowHistoryEntry.at)
        && Objects.equals(this.reason, flowHistoryEntry.reason);
  }

  @Override
  public int hashCode() {
    return Objects.hash(action, from, to, operator, at, reason);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class FlowHistoryEntry {\n");
    sb.append("    action: ").append(toIndentedString(action)).append("\n");
    sb.append("    from: ").append(toIndentedString(from)).append("\n");
    sb.append("    to: ").append(toIndentedString(to)).append("\n");
    sb.append("    operator: ").append(toIndentedString(operator)).append("\n");
    sb.append("    at: ").append(toIndentedString(at)).append("\n");
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
