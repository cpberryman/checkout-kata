package com.berryman.checkout;

import com.berryman.checkout.model.Checkout;
import com.berryman.checkout.model.Product;
import com.berryman.checkout.rules.PricingRule;
import com.berryman.checkout.rules.impl.BuyThreeDiscountRule;
import com.berryman.checkout.rules.impl.BuyTwoDiscountRule;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class App {

  private static Set<PricingRule> pricingRules;
  private static Checkout checkout;

  static {
    pricingRules = new HashSet<>();
    Product a = new Product("A", BigDecimal.valueOf(50));
    Product b = new Product("B", BigDecimal.valueOf(30));
    PricingRule buyThreeRule = new BuyThreeDiscountRule(a);
    PricingRule buyTwoRule = new BuyTwoDiscountRule(b);
    pricingRules.add(buyThreeRule);
    pricingRules.add(buyTwoRule);
    checkout = new Checkout(pricingRules, new ArrayList<>());
  }

  public static void main(String[] args) {

    Scanner scanner = new Scanner(System.in);
    boolean done = false;

    while (!done) {

      System.out.println("Please enter sku or type 'T' for total: ");
      String inputSku = scanner.next();

      String sku = "";
      BigDecimal price = BigDecimal.ZERO;
      if (inputSku.equalsIgnoreCase("T")) {
        done = true;
      } else {
        sku = inputSku;

        System.out.println("Please enter the price or type 'T' for total: ");
        String inputPrice = scanner.next();

        if (inputPrice.equalsIgnoreCase("T")) {
          done = true;
        } else {
          price = BigDecimal.valueOf(Integer.parseInt(inputPrice));
        }
      }

      Product product = new Product(sku, price);
      checkout.scan(product);
    }

    System.out.println("total: " + checkout.total());
  }
}
