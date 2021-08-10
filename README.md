- The assumption is that the item prices and totals are in pence, `BigDecimal` has been used so that if totals need to 
  be in pounds and pence an expression such as the following could be used:

```
    int total = 130;
    
    BigDecimal.valueOf(total).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP); // 1.30
    
```

- Products are immutable so when they are added to the pricing rules; a scanned promotional product needs to have the
same price and SKU to be recognised as a promotional item. Otherwise, the assumption is that a promotional product 
  without the promotional price for this week is not valid for discount. If the requirements were such that items with 
  the same SKU but different price were valid for discount; changing `Checkout.java:line 33` to 
  `(int) items.stream().filter(item -> pricingRule.getItem().getSku().equals(item.getSku())).count();` would enable 
  discounts to be applied to promotional items based on SKU only.
  
- The `main` method accepts user input to scan one product at a time. An improvement on this may be to read a CSV file
with SKUs and prices, instantiate and scan products, then calculate the total to automate this.
  
- There is a `PricingRule` interface that could have implementations for Buy One Get One Free rules for example.
The `apply()` operation described in this interface can have custom logic to calculate a total with discount. `getItem()`
  is required by the `Checkout` to check if a given item is related to a promotional rule. `setItemCount()` is required 
  to determine if the number of promotional items have met the purchase threshold required by the promotional rule for 
  discount.
  
- The rules required to determine the discounts as per the requirements have common members which have been placed in 
the `BaseDiscountRule` class which is a `PricingRule`, this could be changed so that there is just a
  `PercentageDiscount` class with `purchaseThreshold` and `discountPercentage` constructor parameters. If there was 
  additional business logic to add to one of the concrete discount rules, however, this approach may prove difficult to 
  extend. Therefore, there are separate discount rule subclasses with static variables for purchase threshold and discount percentage
  for readability.
  