package io.xr.lab.platform.entity.enums;

import io.xr.lab.shared.dto.RequirementComparison;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/** technical_requirements.comparison: ≥/≤/=/range/eq ↔ PG enum 字符串。 */
@Converter(autoApply = false)
public class RequirementComparisonConverter
    implements AttributeConverter<RequirementComparison, String> {

  @Override
  public String convertToDatabaseColumn(RequirementComparison attribute) {
    return attribute == null ? null : attribute.getValue();
  }

  @Override
  public RequirementComparison convertToEntityAttribute(String dbData) {
    if (dbData == null) {
      return null;
    }
    for (RequirementComparison t : RequirementComparison.values()) {
      if (t.getValue().equals(dbData)) {
        return t;
      }
    }
    throw new IllegalArgumentException("Unknown requirement_comparison: " + dbData);
  }
}
