package com.berryman.checkout.rules.impl;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import com.berryman.checkout.model.Product;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class BuyTwoDiscountRuleTest {

  private Product buyTwoSpecialItem = new Product("B", BigDecimal.valueOf(30));

  @Test
  void itShouldCalculateTheDiscountForTwoItems() {
    // given...
    BuyTwoDiscountRule subject = new BuyTwoDiscountRule(buyTwoSpecialItem);

    // when...
    subject.setItemCount(2);
    BigDecimal result = subject.apply();

    // then...
    assertThat(result, is(BigDecimal.valueOf(45)));
  }

  @Test
  void itShouldCalculateTheDiscountForThreeItems() {
    // given...
    BuyTwoDiscountRule subject = new BuyTwoDiscountRule(buyTwoSpecialItem);

    // when...
    subject.setItemCount(3);
    BigDecimal result = subject.apply();

    // then...
    assertThat(result, is(BigDecimal.valueOf(75)));
  }

  @Test
  void itShouldCalculateTheDiscountForFourItems() {
    // given...
    BuyTwoDiscountRule subject = new BuyTwoDiscountRule(buyTwoSpecialItem);

    // when...
    subject.setItemCount(4);
    BigDecimal result = subject.apply();

    // then...
    assertThat(result, is(BigDecimal.valueOf(90)));
  }

  @Test
  void itShouldNotCalculateTheDiscountForOneItem() {
    // given...
    BuyTwoDiscountRule subject = new BuyTwoDiscountRule(buyTwoSpecialItem);

    // when...
    subject.setItemCount(1);
    BigDecimal result = subject.apply();

    // then...
    assertThat(result, is(BigDecimal.valueOf(30)));
  }
}
