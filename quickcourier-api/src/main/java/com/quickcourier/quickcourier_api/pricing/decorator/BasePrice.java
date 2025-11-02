package com.quickcourier.quickcourier_api.pricing.decorator;

public class BasePrice implements PriceComponent {
    private final double price;

    public BasePrice(double price) {
        this.price = price;
    }

    @Override
    public double getPrice() {
        return price;
    }
}
