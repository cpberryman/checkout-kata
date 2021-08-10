package com.berryman.checkout.rules.impl;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import com.berryman.checkout.model.Product;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class BuyThreeDiscountRuleTest {

  private final Product buyThreeSpecialItem = new Product("A", BigDecimal.valueOf(50));

  private final BuyThreeDiscountRule subject = new BuyThreeDiscountRule(buyThreeSpecialItem);

  @Test
  void itShouldCalculateTheDiscountForThreeItems() {

    subject.setItemCount(3);
    BigDecimal result = subject.apply();

    assertThat(result, is(BigDecimal.valueOf(130)));
  }

  @Test
  void itShouldCalculateTheDiscountForFourItems() {

    subject.setItemCount(4);
    BigDecimal result = subject.apply();

    assertThat(result, is(BigDecimal.valueOf(180)));
  }

  @Test
  void itShouldCalculateTheDiscountForSixItems() {

    subject.setItemCount(6);
    BigDecimal result = subject.apply();

    assertThat(result, is(BigDecimal.valueOf(260)));
  }

  @Test
  void itShouldNotCalculateTheDiscountForOneItem() {

    subject.setItemCount(1);
    BigDecimal result = subject.apply();

    assertThat(result, is(BigDecimal.valueOf(50)));
  }

  @Test
  void itShouldNotCalculateTheDiscountForTwoItems() {

    subject.setItemCount(2);
    BigDecimal result = subject.apply();

    assertThat(result, is(BigDecimal.valueOf(100)));
  }
}
