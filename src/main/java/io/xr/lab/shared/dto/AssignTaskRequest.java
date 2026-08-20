package io.xr.lab.shared.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.constraints.*;
import java.util.*;
import java.util.Objects;
import org.springframework.lang.Nullable;

/** AssignTaskRequest */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    date = "2026-08-20T13:31:51.674991500+08:00[Asia/Shanghai]",
    comments = "Generator version: 7.24.0")
public class AssignTaskRequest {

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String assigneeId;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String assigneeName;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String plannedTestDate;

  public AssignTaskRequest assigneeId(@Nullable String assigneeId) {
    this.assigneeId = assigneeId;
    return this;
  }

  /**
   * Get assigneeId
   *
   * @return assigneeId
   */
  @Schema(name = "assigneeId", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("assigneeId")
  public @Nullable String getAssigneeId() {
    return assigneeId;
  }

  @JsonProperty("assigneeId")
  public void setAssigneeId(@Nullable String assigneeId) {
    this.assigneeId = assigneeId;
  }

  public AssignTaskRequest assigneeName(@Nullable String assigneeName) {
    this.assigneeName = assigneeName;
    return this;
  }

  /**
   * Get assigneeName
   *
   * @return assigneeName
   */
  @Schema(name = "assigneeName", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("assigneeName")
  public @Nullable String getAssigneeName() {
    return assigneeName;
  }

  @JsonProperty("assigneeName")
  public void setAssigneeName(@Nullable String assigneeName) {
    this.assigneeName = assigneeName;
  }

  public AssignTaskRequest plannedTestDate(@Nullable String plannedTestDate) {
    this.plannedTestDate = plannedTestDate;
    return this;
  }

  /**
   * Get plannedTestDate
   *
   * @return plannedTestDate
   */
  @Schema(name = "plannedTestDate", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("plannedTestDate")
  public @Nullable String getPlannedTestDate() {
    return plannedTestDate;
  }

  @JsonProperty("plannedTestDate")
  public void setPlannedTestDate(@Nullable String plannedTestDate) {
    this.plannedTestDate = plannedTestDate;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AssignTaskRequest assignTaskRequest = (AssignTaskRequest) o;
    return Objects.equals(this.assigneeId, assignTaskRequest.assigneeId)
        && Objects.equals(this.assigneeName, assignTaskRequest.assigneeName)
        && Objects.equals(this.plannedTestDate, assignTaskRequest.plannedTestDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(assigneeId, assigneeName, plannedTestDate);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AssignTaskRequest {\n");
    sb.append("    assigneeId: ").append(toIndentedString(assigneeId)).append("\n");
    sb.append("    assigneeName: ").append(toIndentedString(assigneeName)).append("\n");
    sb.append("    plannedTestDate: ").append(toIndentedString(plannedTestDate)).append("\n");
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
