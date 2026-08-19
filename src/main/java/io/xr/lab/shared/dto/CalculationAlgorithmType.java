package io.xr.lab.shared.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.annotation.Generated;
import jakarta.validation.constraints.*;
import java.util.*;

/** Gets or Sets CalculationAlgorithmType */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    date = "2026-08-19T17:37:44.319774200+08:00[Asia/Shanghai]",
    comments = "Generator version: 7.24.0")
public enum CalculationAlgorithmType {
  SIMPLE_AVG("simple_avg"),

  COMPRESSIVE_STRENGTH("compressive_strength"),

  FLEXURAL_STRENGTH("flexural_strength"),

  STEEL_TENSILE("steel_tensile"),

  FORMULA("formula"),

  MANUAL("manual"),

  AUTO_CALC_RATIO("auto_calc_ratio");

  private final String value;

  CalculationAlgorithmType(String value) {
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
  public static CalculationAlgorithmType fromValue(String value) {
    for (CalculationAlgorithmType b : CalculationAlgorithmType.values()) {
      if (b.value.equals(value)) {
        return b;
      }
    }
    throw new IllegalArgumentException("Unexpected value '" + value + "'");
  }
}
