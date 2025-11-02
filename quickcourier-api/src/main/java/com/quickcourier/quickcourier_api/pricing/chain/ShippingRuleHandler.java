package com.quickcourier.quickcourier_api.pricing.chain;

public abstract class ShippingRuleHandler {
    protected ShippingRuleHandler next;

    public ShippingRuleHandler setNext(ShippingRuleHandler next) {
        this.next = next;
        return next;
    }

    public double apply(double price) {
        double newPrice = handle(price);
        if (next != null) {
            return next.apply(newPrice);
        }
        return newPrice;
    }

    protected abstract double handle(double price);
}
