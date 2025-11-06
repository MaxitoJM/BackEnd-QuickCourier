package com.quickcourier.quickcourier_api.repository;

import com.quickcourier.quickcourier_api.domain.model.ShippingRule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShippingRuleRepository extends JpaRepository<ShippingRule, Long> {
}
