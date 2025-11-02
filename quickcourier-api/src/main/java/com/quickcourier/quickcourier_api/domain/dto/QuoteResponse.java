package com.quickcourier.quickcourier_api.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QuoteResponse {
    private double basePrice;
    private double distanceKm;
    private boolean weekend;
    private boolean insurance;
    private String zoneName;
    private double subtotal;
    private double tax;
    private double total;
}
