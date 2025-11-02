package com.quickcourier.quickcourier_api.domain.model;

import org.springframework.stereotype.Component;

@Component("ivaTaxPolicy")
public class IvaTaxPolicy implements TaxPolicy {

    private static final double IVA_RATE = 0.19;

    @Override
    public double calculateTax(double baseAmount) {
        return baseAmount * IVA_RATE;
    }
}
