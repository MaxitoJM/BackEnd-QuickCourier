package com.quickcourier.quickcourier_api.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ShippingRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double baseRate;
    private double costPerKm;

    @ManyToOne
    private Zone zone;
}