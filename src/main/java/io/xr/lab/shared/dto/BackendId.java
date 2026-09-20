package io.xr.lab.shared.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.annotation.Generated;
import jakarta.validation.constraints.*;
import java.util.*;

/** 已废弃 (ADR-0014);3 个槽位,id 锁定避免拼写漂移。msw 成员 2026-09-17 随 msw 仓剔除删除 */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    date = "2026-09-20T15:32:05.122166700+08:00[Asia/Shanghai]",
    comments = "Generator version: 7.24.0")
public enum BackendId {
  NEXTJS("nextjs"),

  SPRINGBOOT("springboot"),

  ASPNETCORE("aspnetcore");

  private final String value;

  BackendId(String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return value;
  }

  @Override
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static BackendId fromValue(String value) {
    for (BackendId b : BackendId.values()) {
      if (b.value.equals(value)) {
        return b;
      }
    }
    throw new IllegalArgumentException("Unexpected value '" + value + "'");
  }
}
