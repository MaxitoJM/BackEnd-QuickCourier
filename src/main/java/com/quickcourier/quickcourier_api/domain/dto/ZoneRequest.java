package com.quickcourier.quickcourier_api.domain.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ZoneRequest {

    @NotBlank(message = "El nombre de la zona es obligatorio")
    private String name;

    @Min(value = 1, message = "El multiplicador debe ser mayor que 0")
    private double multiplier;
}
