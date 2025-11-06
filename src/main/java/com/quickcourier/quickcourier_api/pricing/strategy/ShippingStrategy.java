package com.quickcourier.quickcourier_api.pricing.strategy;

public interface ShippingStrategy {
    double calculate(double basePrice, double distanceKm);
}
