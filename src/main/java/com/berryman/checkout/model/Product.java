package com.berryman.checkout.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Product {

  private final String sku;
  private final BigDecimal unitPrice;

  public Product(String sku, BigDecimal unitPrice) {
    this.sku = sku;
    this.unitPrice = unitPrice;
  }

  public String getSku() {
    return sku;
  }

  public BigDecimal getUnitPrice() {
    return unitPrice;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Product product = (Product) o;
    return Objects.equals(sku, product.sku) && Objects.equals(unitPrice, product.unitPrice);
  }

  @Override
  public int hashCode() {
    return Objects.hash(sku, unitPrice);
  }
}
