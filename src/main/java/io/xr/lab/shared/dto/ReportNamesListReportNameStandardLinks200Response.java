package io.xr.lab.shared.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.lang.Nullable;

/** ReportNamesListReportNameStandardLinks200Response */
@JsonTypeName("ReportNames_listReportNameStandardLinks_200_response")
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    date = "2026-09-02T22:35:42.457326500+08:00[Asia/Shanghai]",
    comments = "Generator version: 7.24.0")
public class ReportNamesListReportNameStandardLinks200Response {

  private List<@Valid ReportNameStandardLink> items = new ArrayList<>();

  private Integer page;

  private Integer pageSize;

  private Long total;

  public ReportNamesListReportNameStandardLinks200Response() {
    super();
  }

  /** Constructor with only required parameters */
  public ReportNamesListReportNameStandardLinks200Response(
      List<@Valid ReportNameStandardLink> items, Integer page, Integer pageSize, Long total) {
    this.items = items;
    this.page = page;
    this.pageSize = pageSize;
    this.total = total;
  }

  public ReportNamesListReportNameStandardLinks200Response items(
      List<@Valid ReportNameStandardLink> items) {
    this.items = items;
    return this;
  }

  public ReportNamesListReportNameStandardLinks200Response addItemsItem(
      ReportNameStandardLink itemsItem) {
    if (this.items == null) {
      this.items = new ArrayList<>();
    }
    this.items.add(itemsItem);
    return this;
  }

  /**
   * Get items
   *
   * @return items
   */
  @NotNull
  @Valid
  @Schema(name = "items", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("items")
  public List<@Valid ReportNameStandardLink> getItems() {
    return items;
  }

  @JsonProperty("items")
  public void setItems(List<@Valid ReportNameStandardLink> items) {
    this.items = items;
  }

  public ReportNamesListReportNameStandardLinks200Response page(Integer page) {
    this.page = page;
    return this;
  }

  /**
   * Get page
   *
   * @return page
   */
  @NotNull
  @Schema(name = "page", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("page")
  public Integer getPage() {
    return page;
  }

  @JsonProperty("page")
  public void setPage(Integer page) {
    this.page = page;
  }

  public ReportNamesListReportNameStandardLinks200Response pageSize(Integer pageSize) {
    this.pageSize = pageSize;
    return this;
  }

  /**
   * Get pageSize
   *
   * @return pageSize
   */
  @NotNull
  @Schema(name = "pageSize", example = "20", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("pageSize")
  public Integer getPageSize() {
    return pageSize;
  }

  @JsonProperty("pageSize")
  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }

  public ReportNamesListReportNameStandardLinks200Response total(Long total) {
    this.total = total;
    return this;
  }

  /**
   * Get total
   *
   * @return total
   */
  @NotNull
  @Schema(name = "total", example = "0", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("total")
  public Long getTotal() {
    return total;
  }

  @JsonProperty("total")
  public void setTotal(Long total) {
    this.total = total;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ReportNamesListReportNameStandardLinks200Response
        reportNamesListReportNameStandardLinks200Response =
            (ReportNamesListReportNameStandardLinks200Response) o;
    return Objects.equals(this.items, reportNamesListReportNameStandardLinks200Response.items)
        && Objects.equals(this.page, reportNamesListReportNameStandardLinks200Response.page)
        && Objects.equals(this.pageSize, reportNamesListReportNameStandardLinks200Response.pageSize)
        && Objects.equals(this.total, reportNamesListReportNameStandardLinks200Response.total);
  }

  @Override
  public int hashCode() {
    return Objects.hash(items, page, pageSize, total);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ReportNamesListReportNameStandardLinks200Response {\n");
    sb.append("    items: ").append(toIndentedString(items)).append("\n");
    sb.append("    page: ").append(toIndentedString(page)).append("\n");
    sb.append("    pageSize: ").append(toIndentedString(pageSize)).append("\n");
    sb.append("    total: ").append(toIndentedString(total)).append("\n");
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
