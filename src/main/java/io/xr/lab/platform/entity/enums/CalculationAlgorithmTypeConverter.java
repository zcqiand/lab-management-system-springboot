package io.xr.lab.platform.entity.enums;

import io.xr.lab.shared.dto.CalculationAlgorithmType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * 计算算法类型 enum ↔ PG enum 字符串。shared DTO 端用 {@code @JsonValue} 走小写，JPA 端持久化同款值。
 *
 * <p>Hibernate {@code @JdbcTypeCode(SqlTypes.NAMED_ENUM) + @Enumerated(STRING)} 会传 enum 常量名（{@code
 * SIMPLE_AVG}），与 PG enum 的小写标签不一致；用 AttributeConverter 显式控制为 DTO 序列化相同的值。
 */
@Converter(autoApply = false)
public class CalculationAlgorithmTypeConverter
    implements AttributeConverter<CalculationAlgorithmType, String> {

  @Override
  public String convertToDatabaseColumn(CalculationAlgorithmType attribute) {
    return attribute == null ? null : attribute.getValue();
  }

  @Override
  public CalculationAlgorithmType convertToEntityAttribute(String dbData) {
    if (dbData == null) {
      return null;
    }
    for (CalculationAlgorithmType t : CalculationAlgorithmType.values()) {
      if (t.getValue().equals(dbData)) {
        return t;
      }
    }
    throw new IllegalArgumentException("Unknown calculation_algorithm_type: " + dbData);
  }
}
