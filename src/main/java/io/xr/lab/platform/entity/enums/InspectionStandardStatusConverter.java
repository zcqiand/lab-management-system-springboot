package io.xr.lab.platform.entity.enums;

import io.xr.lab.shared.dto.InspectionStandardStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * 检测标准 status enum ↔ PG enum 字符串。shared DTO 用 {@code @JsonValue} 走小写值，JPA 持久化同款。
 *
 * <p>镜像 {@link CalculationAlgorithmTypeConverter} 模式。
 */
@Converter(autoApply = false)
public class InspectionStandardStatusConverter
    implements AttributeConverter<InspectionStandardStatus, String> {

  @Override
  public String convertToDatabaseColumn(InspectionStandardStatus attribute) {
    return attribute == null ? null : attribute.getValue();
  }

  @Override
  public InspectionStandardStatus convertToEntityAttribute(String dbData) {
    if (dbData == null) {
      return null;
    }
    for (InspectionStandardStatus t : InspectionStandardStatus.values()) {
      if (t.getValue().equals(dbData)) {
        return t;
      }
    }
    throw new IllegalArgumentException("Unknown inspection_standard_status: " + dbData);
  }
}
