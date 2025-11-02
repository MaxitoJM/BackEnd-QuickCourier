package com.quickcourier.quickcourier_api.pricing.chain;

public class DiscountRule extends ShippingRuleHandler {
    @Override
    protected double handle(double price) {
        return price * 0.95; // 5% de descuento promocional
    }
}
