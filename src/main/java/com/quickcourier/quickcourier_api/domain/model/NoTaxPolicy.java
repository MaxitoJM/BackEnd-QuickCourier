package com.quickcourier.quickcourier_api.domain.model;

import org.springframework.stereotype.Component;

@Component("noTaxPolicy")
public class NoTaxPolicy implements TaxPolicy {

    @Override
    public double calculateTax(double baseAmount) {
        return 0;
    }
}
