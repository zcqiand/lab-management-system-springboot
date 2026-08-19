package io.xr.lab.shared.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.lang.Nullable;

/** SummaryData */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    date = "2026-08-19T17:37:44.319774200+08:00[Asia/Shanghai]",
    comments = "Generator version: 7.24.0")
public class SummaryData {

  private String summaryName;

  private List<@Valid SummaryColumn> columns = new ArrayList<>();

  private List<Map<String, String>> rows = new ArrayList<>();

  public SummaryData() {
    super();
  }

  /** Constructor with only required parameters */
  public SummaryData(
      String summaryName, List<@Valid SummaryColumn> columns, List<Map<String, String>> rows) {
    this.summaryName = summaryName;
    this.columns = columns;
    this.rows = rows;
  }

  public SummaryData summaryName(String summaryName) {
    this.summaryName = summaryName;
    return this;
  }

  /**
   * Get summaryName
   *
   * @return summaryName
   */
  @NotNull
  @Schema(name = "summaryName", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("summaryName")
  public String getSummaryName() {
    return summaryName;
  }

  @JsonProperty("summaryName")
  public void setSummaryName(String summaryName) {
    this.summaryName = summaryName;
  }

  public SummaryData columns(List<@Valid SummaryColumn> columns) {
    this.columns = columns;
    return this;
  }

  public SummaryData addColumnsItem(SummaryColumn columnsItem) {
    if (this.columns == null) {
      this.columns = new ArrayList<>();
    }
    this.columns.add(columnsItem);
    return this;
  }

  /**
   * Get columns
   *
   * @return columns
   */
  @NotNull
  @Valid
  @Schema(name = "columns", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("columns")
  public List<@Valid SummaryColumn> getColumns() {
    return columns;
  }

  @JsonProperty("columns")
  public void setColumns(List<@Valid SummaryColumn> columns) {
    this.columns = columns;
  }

  public SummaryData rows(List<Map<String, String>> rows) {
    this.rows = rows;
    return this;
  }

  public SummaryData addRowsItem(Map<String, String> rowsItem) {
    if (this.rows == null) {
      this.rows = new ArrayList<>();
    }
    this.rows.add(rowsItem);
    return this;
  }

  /**
   * Get rows
   *
   * @return rows
   */
  @NotNull
  @Valid
  @Schema(name = "rows", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("rows")
  public List<Map<String, String>> getRows() {
    return rows;
  }

  @JsonProperty("rows")
  public void setRows(List<Map<String, String>> rows) {
    this.rows = rows;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SummaryData summaryData = (SummaryData) o;
    return Objects.equals(this.summaryName, summaryData.summaryName)
        && Objects.equals(this.columns, summaryData.columns)
        && Objects.equals(this.rows, summaryData.rows);
  }

  @Override
  public int hashCode() {
    return Objects.hash(summaryName, columns, rows);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SummaryData {\n");
    sb.append("    summaryName: ").append(toIndentedString(summaryName)).append("\n");
    sb.append("    columns: ").append(toIndentedString(columns)).append("\n");
    sb.append("    rows: ").append(toIndentedString(rows)).append("\n");
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
