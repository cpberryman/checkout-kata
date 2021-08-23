package com.berryman.checkout.model;

import com.berryman.checkout.rules.PricingRule;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    return totalForNonPromotionalItems().add(totalForPromotionalItems(promotionalItemCounts()));
  }

  private Map<Product, Integer> promotionalItemCounts() {

    Map<Product, Integer> promotionalItemCounts = new HashMap<>();

    for (PricingRule pricingRule : pricingRules) {

      items.forEach(item -> {
        if (pricingRule.getItem().equals(item)) {
          promotionalItemCounts.put(item, promotionalItemCounts.getOrDefault(item, 0) + 1);
        }
      });
    }

    return promotionalItemCounts;
  }

  private BigDecimal totalForNonPromotionalItems() {
    List<Product> promotionalItems =
        pricingRules.stream().map(PricingRule::getItem).collect(Collectors.toList());

    List<Product> nonPromotionalItems =
        items.stream()
            .filter(item -> !promotionalItems.contains(item))
            .collect(Collectors.toList());

    return nonPromotionalItems.stream()
        .map(Product::getUnitPrice)
        .reduce(BigDecimal::add)
        .orElse(BigDecimal.valueOf(0));
  }

  private BigDecimal totalForPromotionalItems(Map <Product, Integer> promotionalItemCounts) {

    return pricingRules.stream()
        .map(rule -> rule.apply(promotionalItemCounts.get(rule.getItem())))
        .reduce(BigDecimal::add)
        .orElse(BigDecimal.valueOf(0));
  }
}
