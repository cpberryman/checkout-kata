package com.berryman.checkout.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class DiscountCalculator {

  private static final BigDecimal ONE_HUNDRED = BigDecimal.valueOf(100);

  private DiscountCalculator() {
    // util class
  }

  public static BigDecimal calculateTotalWithDiscount(
      BigDecimal unitPrice, int itemCount, int purchaseThreshold, BigDecimal percentage) {

    int remainder = itemCount % purchaseThreshold;
    int itemCountToApplyDiscount = itemCount - remainder;

    BigDecimal totalBeforeDiscount = unitPrice.multiply(BigDecimal.valueOf(itemCount));

    BigDecimal totalToApplyDiscountTo =
        unitPrice.multiply(BigDecimal.valueOf(itemCountToApplyDiscount));

    BigDecimal discount =
        totalToApplyDiscountTo.multiply(percentage).divide(ONE_HUNDRED, 0, RoundingMode.HALF_UP);

    return totalBeforeDiscount.subtract(discount);
  }
}
