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

/** CatalogListSpecs200Response */
@JsonTypeName("Catalog_listSpecs_200_response")
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    date = "2026-09-04T01:01:08.136686300+08:00[Asia/Shanghai]",
    comments = "Generator version: 7.24.0")
public class CatalogListSpecs200Response {

  private List<@Valid InspectionSpec> items = new ArrayList<>();

  private Integer page;

  private Integer pageSize;

  private Long total;

  public CatalogListSpecs200Response() {
    super();
  }

  /** Constructor with only required parameters */
  public CatalogListSpecs200Response(
      List<@Valid InspectionSpec> items, Integer page, Integer pageSize, Long total) {
    this.items = items;
    this.page = page;
    this.pageSize = pageSize;
    this.total = total;
  }

  public CatalogListSpecs200Response items(List<@Valid InspectionSpec> items) {
    this.items = items;
    return this;
  }

  public CatalogListSpecs200Response addItemsItem(InspectionSpec itemsItem) {
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
  public List<@Valid InspectionSpec> getItems() {
    return items;
  }

  @JsonProperty("items")
  public void setItems(List<@Valid InspectionSpec> items) {
    this.items = items;
  }

  public CatalogListSpecs200Response page(Integer page) {
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

  public CatalogListSpecs200Response pageSize(Integer pageSize) {
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

  public CatalogListSpecs200Response total(Long total) {
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
    CatalogListSpecs200Response catalogListSpecs200Response = (CatalogListSpecs200Response) o;
    return Objects.equals(this.items, catalogListSpecs200Response.items)
        && Objects.equals(this.page, catalogListSpecs200Response.page)
        && Objects.equals(this.pageSize, catalogListSpecs200Response.pageSize)
        && Objects.equals(this.total, catalogListSpecs200Response.total);
  }

  @Override
  public int hashCode() {
    return Objects.hash(items, page, pageSize, total);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CatalogListSpecs200Response {\n");
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
