package io.xr.lab.platform.entity.enums;

import io.xr.lab.shared.dto.ReceiptResult;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/** sample_receipts.result : PASS / FAIL / EMPTY('') ↔ PG text。 */
@Converter(autoApply = false)
public class ReceiptResultConverter implements AttributeConverter<ReceiptResult, String> {

  @Override
  public String convertToDatabaseColumn(ReceiptResult attribute) {
    return attribute == null ? null : attribute.getValue();
  }

  @Override
  public ReceiptResult convertToEntityAttribute(String dbData) {
    if (dbData == null) return ReceiptResult.EMPTY;
    for (ReceiptResult t : ReceiptResult.values()) {
      if (t.getValue().equals(dbData)) return t;
    }
    throw new IllegalArgumentException("Unknown receipt_result: " + dbData);
  }
}
