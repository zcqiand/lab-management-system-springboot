package io.xr.lab.shared.dto;

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

/** 运行时注册表:当前激活 + 可切列表。切换/订阅方法由消费方实现,TS 签名见 .state/decision-log.md §2.1 */
@Schema(
    name = "BackendRegistry",
    description = "运行时注册表:当前激活 + 可切列表。切换/订阅方法由消费方实现,TS 签名见 .state/decision-log.md §2.1")
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    date = "2026-08-18T09:31:54.550738400+08:00[Asia/Shanghai]",
    comments = "Generator version: 7.24.0")
public class BackendRegistry {

  private BackendId active;

  private List<@Valid BackendConfig> available = new ArrayList<>();

  public BackendRegistry() {
    super();
  }

  /** Constructor with only required parameters */
  public BackendRegistry(BackendId active, List<@Valid BackendConfig> available) {
    this.active = active;
    this.available = available;
  }

  public BackendRegistry active(BackendId active) {
    this.active = active;
    return this;
  }

  /**
   * 当前激活后端
   *
   * @return active
   */
  @NotNull
  @Valid
  @Schema(name = "active", description = "当前激活后端", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("active")
  public BackendId getActive() {
    return active;
  }

  @JsonProperty("active")
  public void setActive(BackendId active) {
    this.active = active;
  }

  public BackendRegistry available(List<@Valid BackendConfig> available) {
    this.available = available;
    return this;
  }

  public BackendRegistry addAvailableItem(BackendConfig availableItem) {
    if (this.available == null) {
      this.available = new ArrayList<>();
    }
    this.available.add(availableItem);
    return this;
  }

  /**
   * 可切换列表(通常包含全部 4 个槽位)
   *
   * @return available
   */
  @NotNull
  @Valid
  @Schema(
      name = "available",
      description = "可切换列表(通常包含全部 4 个槽位)",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("available")
  public List<@Valid BackendConfig> getAvailable() {
    return available;
  }

  @JsonProperty("available")
  public void setAvailable(List<@Valid BackendConfig> available) {
    this.available = available;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BackendRegistry backendRegistry = (BackendRegistry) o;
    return Objects.equals(this.active, backendRegistry.active)
        && Objects.equals(this.available, backendRegistry.available);
  }

  @Override
  public int hashCode() {
    return Objects.hash(active, available);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BackendRegistry {\n");
    sb.append("    active: ").append(toIndentedString(active)).append("\n");
    sb.append("    available: ").append(toIndentedString(available)).append("\n");
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
