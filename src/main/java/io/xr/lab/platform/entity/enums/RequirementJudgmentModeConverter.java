package io.xr.lab.platform.entity.enums;

import io.xr.lab.shared.dto.RequirementJudgmentMode;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/** technical_requirements.judgment_mode: automatic/manual ↔ PG enum 字符串。 */
@Converter(autoApply = false)
public class RequirementJudgmentModeConverter
    implements AttributeConverter<RequirementJudgmentMode, String> {

  @Override
  public String convertToDatabaseColumn(RequirementJudgmentMode attribute) {
    return attribute == null ? null : attribute.getValue();
  }

  @Override
  public RequirementJudgmentMode convertToEntityAttribute(String dbData) {
    if (dbData == null) {
      return null;
    }
    for (RequirementJudgmentMode t : RequirementJudgmentMode.values()) {
      if (t.getValue().equals(dbData)) {
        return t;
      }
    }
    throw new IllegalArgumentException("Unknown requirement_judgment_mode: " + dbData);
  }
}
