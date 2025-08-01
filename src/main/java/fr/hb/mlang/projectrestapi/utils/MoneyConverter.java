package fr.hb.mlang.projectrestapi.utils;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Converter(autoApply = false)
public class MoneyConverter implements AttributeConverter<BigDecimal, BigDecimal> {

  @Override
  public BigDecimal convertToDatabaseColumn(BigDecimal attribute) {
    if (attribute == null) {
      return null;
    }
    return attribute.setScale(2, RoundingMode.HALF_UP);
  }

  @Override
  public BigDecimal convertToEntityAttribute(BigDecimal dbData) {
    if (dbData == null) {
      return null;
    }

    return dbData.setScale(2, RoundingMode.HALF_UP);
  }
}
