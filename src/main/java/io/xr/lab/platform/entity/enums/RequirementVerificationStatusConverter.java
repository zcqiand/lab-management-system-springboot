package io.xr.lab.platform.entity.enums;

import io.xr.lab.shared.dto.RequirementVerificationStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/** technical_requirements.verification_status: draft/reviewed/verified/rejected ↔ PG enum 字符串。 */
@Converter(autoApply = false)
public class RequirementVerificationStatusConverter
    implements AttributeConverter<RequirementVerificationStatus, String> {

  @Override
  public String convertToDatabaseColumn(RequirementVerificationStatus attribute) {
    return attribute == null ? null : attribute.getValue();
  }

  @Override
  public RequirementVerificationStatus convertToEntityAttribute(String dbData) {
    if (dbData == null) {
      return null;
    }
    for (RequirementVerificationStatus t : RequirementVerificationStatus.values()) {
      if (t.getValue().equals(dbData)) {
        return t;
      }
    }
    throw new IllegalArgumentException("Unknown requirement_verification_status: " + dbData);
  }
}
