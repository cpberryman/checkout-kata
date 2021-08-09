package com.berryman.checkout.rules.impl;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import com.berryman.checkout.model.Product;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class BuyThreeDiscountTest {

  private Product buyThreeSpecialItem = new Product("A", new BigDecimal(50));

  @Test
  void itShouldCalculateTheDiscountForThreeItems() {
    // given...
    BuyThreeDiscountRule subject = new BuyThreeDiscountRule(buyThreeSpecialItem);

    // when...
    subject.setItemCount(3);
    BigDecimal result = subject.apply();

    // then...
    assertThat(result, is(BigDecimal.valueOf(130)));
  }

  @Test
  void itShouldCalculateTheDiscountForFourItems() {
    // given...
    BuyThreeDiscountRule subject = new BuyThreeDiscountRule(buyThreeSpecialItem);

    // when...
    subject.setItemCount(4);
    BigDecimal result = subject.apply();

    // then...
    assertThat(result, is(BigDecimal.valueOf(180)));
  }

  @Test
  void itShouldCalculateTheDiscountForSixItems() {
    // given...
    BuyThreeDiscountRule subject = new BuyThreeDiscountRule(buyThreeSpecialItem);

    // when...
    subject.setItemCount(6);
    BigDecimal result = subject.apply();

    // then...
    assertThat(result, is(BigDecimal.valueOf(260)));
  }

  @Test
  void itShouldNotCalculateTheDiscountForOneItem() {
    // given...
    BuyThreeDiscountRule subject = new BuyThreeDiscountRule(buyThreeSpecialItem);

    // when...
    subject.setItemCount(1);
    BigDecimal result = subject.apply();

    // then...
    assertThat(result, is(BigDecimal.valueOf(50)));
  }

  @Test
  void itShouldNotCalculateTheDiscountForTwoItems() {
    // given...
    BuyThreeDiscountRule subject = new BuyThreeDiscountRule(buyThreeSpecialItem);

    // when...
    subject.setItemCount(2);
    BigDecimal result = subject.apply();

    // then...
    assertThat(result, is(BigDecimal.valueOf(100)));
  }
}
