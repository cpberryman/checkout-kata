package com.berryman.checkout.rules;

import com.berryman.checkout.model.Product;

public abstract class BasePercentageDiscountRule implements PricingRule {

  private final Product item;
  private int itemCount;

  protected BasePercentageDiscountRule(Product item) {
    this.item = item;
  }

  @Override
  public Product getItem() {
    return this.item;
  }

  @Override
  public void setItemCount(int itemCount) {
    this.itemCount = itemCount;
  }

  protected int getItemCount() {
    return itemCount;
  }
}
