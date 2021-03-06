package com.berryman.checkout.rules.impl;

import com.berryman.checkout.model.Product;
import com.berryman.checkout.rules.BaseDiscountRule;
import com.berryman.checkout.util.DiscountCalculator;
import java.math.BigDecimal;

public class BuyThreeDiscountRule extends BaseDiscountRule {

  private static final int PURCHASE_THRESHOLD = 3;
  private static final BigDecimal DISCOUNT_PERCENTAGE = new BigDecimal("13.33333");

  public BuyThreeDiscountRule(Product item) {
    super(item);
  }

  @Override
  public BigDecimal apply() {

    return DiscountCalculator.calculateTotalWithDiscount(
        getItem().getUnitPrice(), getItemCount(), PURCHASE_THRESHOLD, DISCOUNT_PERCENTAGE);
  }
}
