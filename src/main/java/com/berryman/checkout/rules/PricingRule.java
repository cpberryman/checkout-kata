package com.berryman.checkout.rules;

import com.berryman.checkout.model.Product;
import java.math.BigDecimal;

public interface PricingRule {

  Product getItem();

  void setItemCount(int itemCount);

  BigDecimal apply();
}
