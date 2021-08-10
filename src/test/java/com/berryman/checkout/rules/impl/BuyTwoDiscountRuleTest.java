package com.berryman.checkout.rules.impl;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import com.berryman.checkout.model.Product;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class BuyTwoDiscountRuleTest {

  private final Product buyTwoSpecialItem = new Product("B", BigDecimal.valueOf(30));

  private final BuyTwoDiscountRule subject = new BuyTwoDiscountRule(buyTwoSpecialItem);

  @Test
  void itShouldCalculateTheDiscountForTwoItems() {

    subject.setItemCount(2);
    BigDecimal result = subject.apply();

    assertThat(result, is(BigDecimal.valueOf(45)));
  }

  @Test
  void itShouldCalculateTheDiscountForThreeItems() {

    subject.setItemCount(3);
    BigDecimal result = subject.apply();

    assertThat(result, is(BigDecimal.valueOf(75)));
  }

  @Test
  void itShouldCalculateTheDiscountForFourItems() {

    subject.setItemCount(4);
    BigDecimal result = subject.apply();

    assertThat(result, is(BigDecimal.valueOf(90)));
  }

  @Test
  void itShouldNotCalculateTheDiscountForOneItem() {

    subject.setItemCount(1);
    BigDecimal result = subject.apply();

    assertThat(result, is(BigDecimal.valueOf(30)));
  }
}
