package com.berryman.checkout.rules.impl;

import com.berryman.checkout.model.Product;
import com.berryman.checkout.rules.PricingRule;
import com.berryman.checkout.util.DiscountCalculator;

import java.math.BigDecimal;

/**
 * @author chris berryman.
 */
public class DiscountRule implements PricingRule {

    private final Product item;
    private final int purchaseThreshold;
    private final BigDecimal discountPercentage;

    public DiscountRule(Product item, int purchaseThreshold, BigDecimal discountPercentage) {
        this.item = item;
        this.purchaseThreshold = purchaseThreshold;
        this.discountPercentage = discountPercentage;
    }

    public Product getItem() {
        return this.item;
    }

    @Override
    public BigDecimal apply(int itemCount) {
        return DiscountCalculator.calculateTotalWithDiscount(
                item.getUnitPrice(), itemCount, purchaseThreshold, discountPercentage);
    }

}
