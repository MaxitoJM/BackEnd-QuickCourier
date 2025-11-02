package com.quickcourier.quickcourier_api.pricing.decorator;

public class InsuranceExtra extends ExtraDecorator {

    public InsuranceExtra(PriceComponent component) {
        super(component);
    }

    @Override
    public double getPrice() {
        return component.getPrice() + 5000; // seguro adicional fijo
    }
}
