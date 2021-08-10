package com.berryman.checkout;

import com.berryman.checkout.model.Checkout;
import com.berryman.checkout.model.Product;
import com.berryman.checkout.rules.PricingRule;
import com.berryman.checkout.rules.impl.BuyThreeDiscountRule;
import com.berryman.checkout.rules.impl.BuyTwoDiscountRule;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.*;

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

    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    boolean done = false;

    while (!done) {

      try {

        System.out.println("Please enter SKU, space bar to exit: ");
        String inputSku = in.readLine();

        String sku = "";
        BigDecimal price = BigDecimal.ZERO;
        if (inputSku.equals(" ")) {
          done = true;
        } else {
          sku = inputSku;

          System.out.println("Please enter the price, space bar for total: ");
          String inputPrice = in.readLine();

          if (inputPrice.equals(" ")) {
            done = true;
          } else {
            price = BigDecimal.valueOf(Integer.parseInt(inputPrice));
          }
        }

        Product product = new Product(sku, price);
        checkout.scan(product);

      } catch (IOException e) {
        System.out.println(Arrays.toString(e.getStackTrace()));
      }
    }

    System.out.println("total: " + checkout.total());
  }
}
