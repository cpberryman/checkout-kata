package com.berryman.checkout.model;

import com.berryman.checkout.rules.PricingRule;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    countPromotionalItemsForPricingRules();

    return totalForNonPromotionalItems().add(totalForPromotionalItems());
  }

  private void countPromotionalItemsForPricingRules() {
    for (PricingRule pricingRule : pricingRules) {
      int specialItemCount =
          (int) items.stream().filter(item -> pricingRule.getItem().equals(item)).count();

      pricingRule.setItemCount(specialItemCount);
    }
  }

  private BigDecimal totalForNonPromotionalItems() {
    List<Product> promotionalItems =
        pricingRules.stream()
            .map(PricingRule::getItem)
            .collect(Collectors.toList());

    List<Product> nonPromotionalItems =
        items.stream()
            .filter(item -> !promotionalItems.contains(item))
            .collect(Collectors.toList());

    return nonPromotionalItems.stream()
        .map(Product::getUnitPrice)
        .reduce(BigDecimal::add)
        .orElse(BigDecimal.valueOf(0));
  }

  private BigDecimal totalForPromotionalItems() {
    return pricingRules.stream()
        .map(PricingRule::apply)
        .reduce(BigDecimal::add)
        .orElse(BigDecimal.valueOf(0));
  }

}
