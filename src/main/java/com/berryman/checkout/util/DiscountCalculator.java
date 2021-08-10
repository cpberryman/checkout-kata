package com.berryman.checkout.util;

import com.berryman.checkout.model.Product;
import java.math.BigDecimal;
import java.math.RoundingMode;

public final class DiscountCalculator {

  private static final BigDecimal ONE_HUNDRED = BigDecimal.valueOf(100);

  private DiscountCalculator() {
    // util class
  }

  public static BigDecimal calculateTotalWithDiscount(
      Product item, int itemCount, int purchaseThreshold, BigDecimal percentage) {

    int remainder = itemCount % purchaseThreshold;
    int itemCountToApplyDiscount = itemCount - remainder;

    BigDecimal totalBeforeDiscount = item.getUnitPrice().multiply(BigDecimal.valueOf(itemCount));

    BigDecimal totalToApplyDiscountTo =
        item.getUnitPrice().multiply(BigDecimal.valueOf(itemCountToApplyDiscount));

    BigDecimal discount =
        totalToApplyDiscountTo
            .multiply(percentage)
            .divide(ONE_HUNDRED)
            .setScale(0, RoundingMode.HALF_UP);

    return totalBeforeDiscount.subtract(discount);
  }
}
