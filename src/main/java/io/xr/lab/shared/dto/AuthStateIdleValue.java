package io.xr.lab.shared.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.constraints.*;
import java.util.*;
import java.util.Objects;
import org.springframework.lang.Nullable;

/** AuthStateIdleValue */
@JsonTypeName("AuthStateIdle_value")
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    date = "2026-09-02T22:35:42.457326500+08:00[Asia/Shanghai]",
    comments = "Generator version: 7.24.0")
public class AuthStateIdleValue {

  /** Gets or Sets kind */
  public enum KindEnum {
    IDLE("idle");

    private final String value;

    KindEnum(String value) {
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
    public static KindEnum fromValue(String value) {
      for (KindEnum b : KindEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  private KindEnum kind;

  public AuthStateIdleValue() {
    super();
  }

  /** Constructor with only required parameters */
  public AuthStateIdleValue(KindEnum kind) {
    this.kind = kind;
  }

  public AuthStateIdleValue kind(KindEnum kind) {
    this.kind = kind;
    return this;
  }

  /**
   * Get kind
   *
   * @return kind
   */
  @NotNull
  @Schema(name = "kind", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("kind")
  public KindEnum getKind() {
    return kind;
  }

  @JsonProperty("kind")
  public void setKind(KindEnum kind) {
    this.kind = kind;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AuthStateIdleValue authStateIdleValue = (AuthStateIdleValue) o;
    return Objects.equals(this.kind, authStateIdleValue.kind);
  }

  @Override
  public int hashCode() {
    return Objects.hash(kind);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AuthStateIdleValue {\n");
    sb.append("    kind: ").append(toIndentedString(kind)).append("\n");
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
