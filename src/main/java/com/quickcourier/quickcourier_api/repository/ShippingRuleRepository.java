package com.quickcourier.quickcourier_api.repository;

import com.quickcourier.quickcourier_api.domain.model.ShippingRule;
import com.quickcourier.quickcourier_api.domain.model.Zone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShippingRuleRepository extends JpaRepository<ShippingRule, Long> {
    Optional<ShippingRule> findByZone(Zone zone);
}
