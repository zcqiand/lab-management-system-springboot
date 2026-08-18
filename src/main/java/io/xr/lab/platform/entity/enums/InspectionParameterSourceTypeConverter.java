package io.xr.lab.platform.entity.enums;

import io.xr.lab.shared.dto.InspectionParameterSourceType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * 检测参数 source_type enum ↔ PG enum 字符串。shared DTO 用 {@code @JsonValue} 走小写值，JPA 持久化同款。
 *
 * <p>镜像 {@link CalculationAlgorithmTypeConverter} 模式：Hibernate 默认 {@code @Enumerated(STRING)} 传常量名与
 * PG enum 标签不一致，改用 AttributeConverter 显式写 DTO 序列化值。
 */
@Converter(autoApply = false)
public class InspectionParameterSourceTypeConverter
    implements AttributeConverter<InspectionParameterSourceType, String> {

  @Override
  public String convertToDatabaseColumn(InspectionParameterSourceType attribute) {
    return attribute == null ? null : attribute.getValue();
  }

  @Override
  public InspectionParameterSourceType convertToEntityAttribute(String dbData) {
    if (dbData == null) {
      return null;
    }
    for (InspectionParameterSourceType t : InspectionParameterSourceType.values()) {
      if (t.getValue().equals(dbData)) {
        return t;
      }
    }
    throw new IllegalArgumentException("Unknown inspection_parameter_source_type: " + dbData);
  }
}
