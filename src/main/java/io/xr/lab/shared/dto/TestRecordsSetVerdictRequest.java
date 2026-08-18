package io.xr.lab.shared.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.constraints.*;
import java.util.*;
import java.util.Objects;
import org.springframework.lang.Nullable;

/** TestRecordsSetVerdictRequest */
@JsonTypeName("TestRecords_setVerdict_request")
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    date = "2026-08-18T09:31:54.550738400+08:00[Asia/Shanghai]",
    comments = "Generator version: 7.24.0")
public class TestRecordsSetVerdictRequest {

  private String verdict;

  public TestRecordsSetVerdictRequest() {
    super();
  }

  /** Constructor with only required parameters */
  public TestRecordsSetVerdictRequest(String verdict) {
    this.verdict = verdict;
  }

  public TestRecordsSetVerdictRequest verdict(String verdict) {
    this.verdict = verdict;
    return this;
  }

  /**
   * Get verdict
   *
   * @return verdict
   */
  @NotNull
  @Schema(name = "verdict", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("verdict")
  public String getVerdict() {
    return verdict;
  }

  @JsonProperty("verdict")
  public void setVerdict(String verdict) {
    this.verdict = verdict;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TestRecordsSetVerdictRequest testRecordsSetVerdictRequest = (TestRecordsSetVerdictRequest) o;
    return Objects.equals(this.verdict, testRecordsSetVerdictRequest.verdict);
  }

  @Override
  public int hashCode() {
    return Objects.hash(verdict);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TestRecordsSetVerdictRequest {\n");
    sb.append("    verdict: ").append(toIndentedString(verdict)).append("\n");
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
