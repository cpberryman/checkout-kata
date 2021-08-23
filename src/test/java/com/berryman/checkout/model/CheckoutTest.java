package com.berryman.checkout.model;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import com.berryman.checkout.rules.PricingRule;

import java.math.BigDecimal;
import java.util.*;

import com.berryman.checkout.rules.impl.DiscountRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CheckoutTest {

  private Product a = new Product("A", BigDecimal.valueOf(50));
  private Product b = new Product("B", BigDecimal.valueOf(30));
  private Product c = new Product("C", BigDecimal.valueOf(20));
  private Product d = new Product("D", BigDecimal.valueOf(15));
  private Set<PricingRule> pricingRules = new HashSet<>();
  private List<Product> items = new ArrayList<>();
  private Checkout subject;

  @BeforeEach
  void init() {
    PricingRule buyThreeDiscountRule = new DiscountRule(a, 3, new BigDecimal("13.33333"));
    PricingRule buyTwoDiscountRule = new DiscountRule(b, 2, BigDecimal.valueOf(25));
    pricingRules.add(buyThreeDiscountRule);
    pricingRules.add(buyTwoDiscountRule);
    subject = new Checkout(pricingRules, items);
  }

  @Test
  void scanShouldAddItem() {
    subject.scan(a);

    assertThat(items.size(), is(1));
  }

  @Test
  void totalShouldCalculateTotalWithDiscountToPromotionalItemsWithPurchaseThresholdOfTwo() {

    BigDecimal result = getTotalFromCheckout(Arrays.asList(b, a, b));

    assertThat(result, is(BigDecimal.valueOf(95)));
  }

  @Test
  void totalShouldCalculateTotalWithDiscountToPromotionalItemsWithPurchaseThresholdOfThree() {

    BigDecimal result = getTotalFromCheckout(Arrays.asList(a, a, a, b));

    assertThat(result, is(BigDecimal.valueOf(160)));
  }

  @Test
  void totalShouldCalculateTotalWithDiscountForAllPromotionalItemsWhenPurchaseThresholdIsReached() {

    BigDecimal result = getTotalFromCheckout(Arrays.asList(a, a, a, b, b));

    assertThat(result, is(BigDecimal.valueOf(175)));
  }

  @Test
  void totalShouldCalculateTotalAndApplyDiscountToItemsThatReachPurchaseThresholdOnly() {

    BigDecimal result = getTotalFromCheckout(Arrays.asList(a, a, a, a, b, b, b));

    assertThat(result, is(BigDecimal.valueOf(255)));
  }

  @Test
  void
      totalShouldCalculateTotalWithDiscountForMultiplesOfPromotionalItemsThatReachPurchaseThreshold() {

    BigDecimal result = getTotalFromCheckout(Arrays.asList(a, a, a, a, a, a, b, b, b, b));

    assertThat(result, is(BigDecimal.valueOf(350)));
  }

  @Test
  void
      totalShouldCalculateTotalWithDiscountToPromotionalItemsWithPurchaseThresholdOfTwoAndNonPromotionalItems() {

    BigDecimal result = getTotalFromCheckout(Arrays.asList(b, a, b, c, c, d));

    assertThat(result, is(BigDecimal.valueOf(150)));
  }

  @Test
  void totalShouldCalculateTotalForCollectionWithNoPromotionalItems() {

    BigDecimal result = getTotalFromCheckout(Arrays.asList(a, b, c, d));

    assertThat(result, is(BigDecimal.valueOf(115)));
  }

  @Test
  void totalShouldCalculateTotalForAnotherCollectionWithNoPromotionalItems() {

    BigDecimal result = getTotalFromCheckout(Arrays.asList(a, a, b, c, c, c, d, d));

    assertThat(result, is(BigDecimal.valueOf(220)));
  }

  private BigDecimal getTotalFromCheckout(List<Product> products) {
    products.forEach(product -> subject.scan(product));

    return subject.total();
  }
}
