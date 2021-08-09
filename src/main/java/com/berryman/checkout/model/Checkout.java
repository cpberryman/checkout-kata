package com.berryman.checkout.model;

import com.berryman.checkout.rules.PricingRule;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public class Checkout {

  private final Set<PricingRule> pricingRules;
  private final List<Product> items;

  public Checkout(Set<PricingRule> pricingRules, List<Product> items) {
    this.pricingRules = pricingRules;
    this.items = items;
  }

  public void scan(Product item) {
    items.add(item);
  }

  public BigDecimal total() {

    for (PricingRule p : pricingRules) {
      int itemCount = (int) items.stream().filter(item -> p.getItem().equals(item)).count();

      p.setItemCount(itemCount);
    }

    return pricingRules.stream().map(PricingRule::apply).reduce(BigDecimal::add).get();
  }

  public void reset() {
    this.items.clear();
  }
}
