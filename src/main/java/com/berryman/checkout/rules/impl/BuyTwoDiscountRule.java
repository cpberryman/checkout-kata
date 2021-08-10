package com.berryman.checkout.rules.impl;

import com.berryman.checkout.model.Product;
import com.berryman.checkout.rules.BasePercentageDiscountRule;
import com.berryman.checkout.util.DiscountCalculator;
import java.math.BigDecimal;

public class BuyTwoDiscountRule extends BasePercentageDiscountRule {

  private static final int PURCHASE_THRESHOLD = 2;
  private static final BigDecimal DISCOUNT_PERCENTAGE = BigDecimal.valueOf(25);

  public BuyTwoDiscountRule(Product item) {
    super(item);
  }

  @Override
  public BigDecimal apply() {

    return DiscountCalculator.calculateTotalWithDiscount(
        getItem(), getItemCount(), PURCHASE_THRESHOLD, DISCOUNT_PERCENTAGE);
  }
}
