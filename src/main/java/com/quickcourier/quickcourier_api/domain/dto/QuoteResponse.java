package com.quickcourier.quickcourier_api.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuoteResponse {

    private double basePrice;     
    private double distanceKm;    
    private boolean weekend;      
    private boolean insurance;    
    private String zoneName;      
    private double subtotal;      
    private double tax;           
    private double total;         

    public QuoteResponse(String zoneName, double distanceKm, boolean weekend, boolean insurance, double total) {
        this.zoneName = zoneName;
        this.distanceKm = distanceKm;
        this.weekend = weekend;
        this.insurance = insurance;
        this.total = total;

        
        this.basePrice = 0;
        this.subtotal = total;
        this.tax = 0;
    }
}
