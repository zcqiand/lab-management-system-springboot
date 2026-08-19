package io.xr.lab.shared.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.annotation.Generated;
import jakarta.validation.constraints.*;
import java.util.*;

/** Gets or Sets FlowAction */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    date = "2026-08-19T17:37:44.319774200+08:00[Asia/Shanghai]",
    comments = "Generator version: 7.24.0")
public enum FlowAction {
  SUBMIT("submit"),

  RETURN("return"),

  WITHDRAW("withdraw");

  private final String value;

  FlowAction(String value) {
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
  public static FlowAction fromValue(String value) {
    for (FlowAction b : FlowAction.values()) {
      if (b.value.equals(value)) {
        return b;
      }
    }
    throw new IllegalArgumentException("Unexpected value '" + value + "'");
  }
}
