package io.xr.lab.platform.entity.enums;

import io.xr.lab.shared.dto.ContractStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/** contracts.status : ACTIVE / ARCHIVED ↔ PG text 'active' / 'archived'。 */
@Converter(autoApply = false)
public class ContractStatusConverter implements AttributeConverter<ContractStatus, String> {

  @Override
  public String convertToDatabaseColumn(ContractStatus attribute) {
    return attribute == null ? null : attribute.getValue();
  }

  @Override
  public ContractStatus convertToEntityAttribute(String dbData) {
    if (dbData == null) return null;
    for (ContractStatus t : ContractStatus.values()) {
      if (t.getValue().equals(dbData)) return t;
    }
    throw new IllegalArgumentException("Unknown contract_status: " + dbData);
  }
}
