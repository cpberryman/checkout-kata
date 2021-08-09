package com.berryman.checkout.rules.impl;

import com.berryman.checkout.model.Product;
import com.berryman.checkout.rules.PricingRule;
import java.math.BigDecimal;

public class BuyTwoDiscountRule implements PricingRule {

  private final Product item;
  private int itemCount;

  private static final BigDecimal ONE_HUNDRED = new BigDecimal(100);
  private static final BigDecimal PERCENTAGE = new BigDecimal(25);

  public BuyTwoDiscountRule(Product item) {
    this.item = item;
  }

  @Override
  public BigDecimal apply() {

    int remainder = itemCount % 2;
    int itemCountToApplyDiscount = itemCount - remainder;

    BigDecimal totalBeforeDiscount = item.getUnitPrice().multiply(BigDecimal.valueOf(itemCount));

    BigDecimal totalToApplyDiscountTo =
        item.getUnitPrice().multiply(BigDecimal.valueOf(itemCountToApplyDiscount));

    BigDecimal discount = totalToApplyDiscountTo.multiply(PERCENTAGE).divide(ONE_HUNDRED);

    return totalBeforeDiscount.subtract(discount);
  }

  @Override
  public Product getItem() {
    return this.item;
  }

  @Override
  public void setItemCount(int itemCount) {
    this.itemCount = itemCount;
  }
}
