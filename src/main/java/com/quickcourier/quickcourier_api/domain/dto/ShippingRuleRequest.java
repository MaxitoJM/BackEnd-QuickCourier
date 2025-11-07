package com.quickcourier.quickcourier_api.domain.dto;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class ShippingRuleRequest {

    @Min(value = 0, message = "La tarifa base debe ser mayor o igual a 0")
    private double baseRate;

    @Min(value = 0, message = "El costo por kilómetro debe ser mayor o igual a 0")
    private double costPerKm;
}
