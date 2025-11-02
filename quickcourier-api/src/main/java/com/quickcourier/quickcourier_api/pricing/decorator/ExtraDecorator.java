package com.quickcourier.quickcourier_api.pricing.decorator;

public abstract class ExtraDecorator implements PriceComponent {
    protected final PriceComponent component;

    public ExtraDecorator(PriceComponent component) {
        this.component = component;
    }
}
