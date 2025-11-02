package com.quickcourier.quickcourier_api.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Zone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // Ej: "Zona Norte", "Centro", etc.
    private double multiplier; // Ej: 1.0, 1.2, 1.5
}
