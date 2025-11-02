package com.quickcourier.quickcourier_api.pricing.strategy;

public class StandardShipping implements ShippingStrategy {
    @Override
    public double calculate(double basePrice, double distanceKm) {
        return basePrice + distanceKm * 0.5; // costo básico
    }
}
