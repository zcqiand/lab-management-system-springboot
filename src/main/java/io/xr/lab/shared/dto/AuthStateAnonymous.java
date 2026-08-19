package io.xr.lab.shared.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.*;
import java.util.Objects;
import org.springframework.lang.Nullable;

/** AuthStateAnonymous */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    date = "2026-08-19T17:37:44.319774200+08:00[Asia/Shanghai]",
    comments = "Generator version: 7.24.0")
public class AuthStateAnonymous implements AuthState {

  /** Gets or Sets kind */
  public enum KindEnum {
    ANONYMOUS("anonymous");

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

  private AuthStateAnonymousValue value;

  public AuthStateAnonymous() {
    super();
  }

  /** Constructor with only required parameters */
  public AuthStateAnonymous(KindEnum kind, AuthStateAnonymousValue value) {
    this.kind = kind;
    this.value = value;
  }

  public AuthStateAnonymous kind(KindEnum kind) {
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

  public AuthStateAnonymous value(AuthStateAnonymousValue value) {
    this.value = value;
    return this;
  }

  /**
   * Get value
   *
   * @return value
   */
  @NotNull
  @Valid
  @Schema(name = "value", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("value")
  public AuthStateAnonymousValue getValue() {
    return value;
  }

  @JsonProperty("value")
  public void setValue(AuthStateAnonymousValue value) {
    this.value = value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AuthStateAnonymous authStateAnonymous = (AuthStateAnonymous) o;
    return Objects.equals(this.kind, authStateAnonymous.kind)
        && Objects.equals(this.value, authStateAnonymous.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(kind, value);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AuthStateAnonymous {\n");
    sb.append("    kind: ").append(toIndentedString(kind)).append("\n");
    sb.append("    value: ").append(toIndentedString(value)).append("\n");
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
