package com.berryman.checkout.rules.impl;

import com.berryman.checkout.model.Product;
import com.berryman.checkout.rules.PricingRule;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * @author chris berryman.
 */
class DiscountRuleTest {


    private final Product buyTwoSpecialItem = new Product("B", BigDecimal.valueOf(30));
    private final Product buyThreeSpecialItem = new Product("A", BigDecimal.valueOf(50));
    private final PricingRule buyTwoSubject = new DiscountRule(buyTwoSpecialItem, 2, BigDecimal.valueOf(25));
    private final PricingRule buyThreeSubject = new DiscountRule(buyThreeSpecialItem, 3, new BigDecimal("13.33333"));

    @Test
    void itShouldCalculateTheDiscountForTwoItemsWhenPurchaseThresholdIsTwo() {

        BigDecimal result = buyTwoSubject.apply(2);

        assertThat(result, is(BigDecimal.valueOf(45)));
    }

    @Test
    void itShouldCalculateTheDiscountForThreeItemsWhenPurchaseThresholdIsTwo() {

        BigDecimal result = buyTwoSubject.apply(3);

        assertThat(result, is(BigDecimal.valueOf(75)));
    }

    @Test
    void itShouldCalculateTheDiscountForFourItemsWhenPurchaseThresholdIsTwo() {

        BigDecimal result = buyTwoSubject.apply(4);

        assertThat(result, is(BigDecimal.valueOf(90)));
    }

    @Test
    void itShouldNotCalculateTheDiscountForOneItemWhenPurchaseThresholdIsTwo() {

        BigDecimal result = buyTwoSubject.apply(1);

        assertThat(result, is(BigDecimal.valueOf(30)));
    }


    @Test
    void itShouldCalculateTheDiscountForThreeItemsWhenPurchaseThresholdIsThree() {

        BigDecimal result = buyThreeSubject.apply(3);

        assertThat(result, is(BigDecimal.valueOf(130)));
    }

    @Test
    void itShouldCalculateTheDiscountForFourItemsWhenPurchaseThresholdIsThree() {

        BigDecimal result = buyThreeSubject.apply(4);

        assertThat(result, is(BigDecimal.valueOf(180)));
    }

    @Test
    void itShouldCalculateTheDiscountForSixItemsWhenPurchaseThresholdIsThree() {

        BigDecimal result = buyThreeSubject.apply(6);

        assertThat(result, is(BigDecimal.valueOf(260)));
    }

    @Test
    void itShouldNotCalculateTheDiscountForOneItemWhenPurchaseThresholdIsThree() {

        BigDecimal result = buyThreeSubject.apply(1);

        assertThat(result, is(BigDecimal.valueOf(50)));
    }

    @Test
    void itShouldNotCalculateTheDiscountForTwoItemsWhenPurchaseThresholdIsThree() {

        BigDecimal result = buyThreeSubject.apply(2);

        assertThat(result, is(BigDecimal.valueOf(100)));
    }

}