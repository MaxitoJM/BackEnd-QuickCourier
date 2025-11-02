package com.quickcourier.quickcourier_api.pricing.chain;

public class WeekendRule extends ShippingRuleHandler {
    @Override
    protected double handle(double price) {
        return price * 1.10; // 10% más si es fin de semana
    }
}
