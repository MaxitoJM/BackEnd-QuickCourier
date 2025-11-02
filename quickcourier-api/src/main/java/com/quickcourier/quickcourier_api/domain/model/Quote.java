package com.quickcourier.quickcourier_api.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Quote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double basePrice;
    private double distanceKm;
    private boolean weekend;
    private boolean insurance;
    private double total;

    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne
    private Zone zone;
}
