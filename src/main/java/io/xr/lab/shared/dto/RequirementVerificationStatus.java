package io.xr.lab.shared.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.annotation.Generated;
import jakarta.validation.constraints.*;
import java.util.*;

/** Gets or Sets RequirementVerificationStatus */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    date = "2026-09-04T01:01:08.136686300+08:00[Asia/Shanghai]",
    comments = "Generator version: 7.24.0")
public enum RequirementVerificationStatus {
  DRAFT("draft"),

  REVIEWED("reviewed"),

  VERIFIED("verified"),

  REJECTED("rejected");

  private final String value;

  RequirementVerificationStatus(String value) {
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
  public static RequirementVerificationStatus fromValue(String value) {
    for (RequirementVerificationStatus b : RequirementVerificationStatus.values()) {
      if (b.value.equals(value)) {
        return b;
      }
    }
    throw new IllegalArgumentException("Unexpected value '" + value + "'");
  }
}
