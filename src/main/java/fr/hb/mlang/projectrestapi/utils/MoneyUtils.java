package fr.hb.mlang.projectrestapi.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Allows us to use BigDecimal types for monetary value without having to set the scale everytime.
 */
public final class MoneyUtils {

  private MoneyUtils() {
  }

  public static BigDecimal of(double amount) {
    return BigDecimal.valueOf(amount).setScale(2, RoundingMode.HALF_UP);
  }
}
