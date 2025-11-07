package com.quickcourier.quickcourier_api.domain.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class QuoteRequest {

    @NotNull(message = "El precio base no puede ser nulo")
    @Min(value = 1, message = "El precio base debe ser mayor que 0")
    private Double basePrice;

    @NotNull(message = "La distancia no puede ser nula")
    @Min(value = 0, message = "La distancia debe ser mayor o igual a 0")
    private Double distanceKm;

    private boolean weekend;
    private boolean insurance;

    @NotNull(message = "Debe especificarse una zona válida")
    private Long zoneId;
}
