package com.berryman.checkout.model;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import com.berryman.checkout.rules.PricingRule;
import com.berryman.checkout.rules.impl.BuyThreeDiscountRule;
import com.berryman.checkout.rules.impl.BuyTwoDiscountRule;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.AfterEach;
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
  public void init() {
    PricingRule buyThreeDiscountRule = new BuyThreeDiscountRule(a);
    PricingRule buyTwoDiscountRule = new BuyTwoDiscountRule(b);
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
  void totalShouldCalculateTotalForTwoBsAndOneA() {
    subject.scan(b);
    subject.scan(a);
    subject.scan(b);

    BigDecimal result = subject.total();

    assertThat(result, is(BigDecimal.valueOf(95)));
  }

  @Test
  void totalShouldCalculateTotalForThreeAsAndOneB() {
    subject.scan(a);
    subject.scan(a);
    subject.scan(a);
    subject.scan(b);

    BigDecimal result = subject.total();

    assertThat(result, is(BigDecimal.valueOf(160)));
  }

  @Test
  void totalShouldCalculateTotalForThreeAsAndTwoBs() {
    subject.scan(a);
    subject.scan(a);
    subject.scan(a);
    subject.scan(b);
    subject.scan(b);

    BigDecimal result = subject.total();

    assertThat(result, is(BigDecimal.valueOf(175)));
  }

  @Test
  void totalShouldCalculateTotalForForAsAndThreeBs() {
    subject.scan(a);
    subject.scan(a);
    subject.scan(a);
    subject.scan(a);
    subject.scan(b);
    subject.scan(b);
    subject.scan(b);

    BigDecimal result = subject.total();

    assertThat(result, is(BigDecimal.valueOf(255)));
  }

  @Test
  void totalShouldCalculateTotalForSixAsAndFourBs() {
    subject.scan(a);
    subject.scan(a);
    subject.scan(a);
    subject.scan(a);
    subject.scan(a);
    subject.scan(a);
    subject.scan(b);
    subject.scan(b);
    subject.scan(b);
    subject.scan(b);

    BigDecimal result = subject.total();

    assertThat(result, is(BigDecimal.valueOf(350)));
  }

  @Test
  void totalShouldCalculateTotalForTwoBsOneATwoCsAndOneD() {
    subject.scan(b);
    subject.scan(a);
    subject.scan(b);
    subject.scan(c);
    subject.scan(c);
    subject.scan(d);

    BigDecimal result = subject.total();

    assertThat(result, is(BigDecimal.valueOf(150)));
  }

  @Test
  void totalShouldCalculateTotalForCollectionWithNoPromotionalItems() {
    subject.scan(a);
    subject.scan(b);
    subject.scan(c);
    subject.scan(d);

    BigDecimal result = subject.total();

    assertThat(result, is(BigDecimal.valueOf(115)));
  }

  @Test
  void totalShouldCalculateTotalForAnotherCollectionWithNoPromotionalItems() {
    subject.scan(a);
    subject.scan(a);
    subject.scan(b);
    subject.scan(c);
    subject.scan(c);
    subject.scan(c);
    subject.scan(d);
    subject.scan(d);

    BigDecimal result = subject.total();

    assertThat(result, is(BigDecimal.valueOf(220)));
  }

  @AfterEach
  public void TearDown() {
    subject.reset();
  }
}
