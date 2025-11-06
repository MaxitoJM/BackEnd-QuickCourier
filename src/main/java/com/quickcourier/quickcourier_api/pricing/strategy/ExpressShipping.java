package com.quickcourier.quickcourier_api.pricing.strategy;

public class ExpressShipping implements ShippingStrategy {
    @Override
    public double calculate(double basePrice, double distanceKm) {
        return basePrice + distanceKm * 1.2; // más caro por urgencia
    }
}
