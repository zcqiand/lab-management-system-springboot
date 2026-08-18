package io.xr.lab.platform.entity.enums;

import io.xr.lab.shared.dto.RequirementValueType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/** technical_requirements.value_type: NUMERIC/STRING/RANGE/FORMULA/MANUAL ↔ PG enum 小写标签。 */
@Converter(autoApply = false)
public class RequirementValueTypeConverter
    implements AttributeConverter<RequirementValueType, String> {

  @Override
  public String convertToDatabaseColumn(RequirementValueType attribute) {
    return attribute == null ? null : attribute.getValue();
  }

  @Override
  public RequirementValueType convertToEntityAttribute(String dbData) {
    if (dbData == null) {
      return null;
    }
    for (RequirementValueType t : RequirementValueType.values()) {
      if (t.getValue().equals(dbData)) {
        return t;
      }
    }
    throw new IllegalArgumentException("Unknown requirement_value_type: " + dbData);
  }
}
