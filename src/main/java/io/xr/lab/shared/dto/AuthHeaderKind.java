package io.xr.lab.shared.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.annotation.Generated;
import jakarta.validation.constraints.*;
import java.util.*;

/** Gets or Sets AuthHeaderKind */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    date = "2026-08-18T09:31:54.550738400+08:00[Asia/Shanghai]",
    comments = "Generator version: 7.24.0")
public enum AuthHeaderKind {
  AUTHORIZATION("Authorization"),

  X_AUTH_TOKEN("X-Auth-Token");

  private final String value;

  AuthHeaderKind(String value) {
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
  public static AuthHeaderKind fromValue(String value) {
    for (AuthHeaderKind b : AuthHeaderKind.values()) {
      if (b.value.equals(value)) {
        return b;
      }
    }
    throw new IllegalArgumentException("Unexpected value '" + value + "'");
  }
}
