package com.quickcourier.quickcourier_api.service;

import com.quickcourier.quickcourier_api.domain.model.ShippingRule;
import com.quickcourier.quickcourier_api.domain.model.Zone;
import com.quickcourier.quickcourier_api.repository.ShippingRuleRepository;
import com.quickcourier.quickcourier_api.repository.ZoneRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShippingRuleService {

    private final ShippingRuleRepository shippingRuleRepository;
    private final ZoneRepository zoneRepository;

    public ShippingRuleService(ShippingRuleRepository shippingRuleRepository, ZoneRepository zoneRepository) {
        this.shippingRuleRepository = shippingRuleRepository;
        this.zoneRepository = zoneRepository;
    }

    // 🔹 Listar todas las reglas
    public List<ShippingRule> findAll() {
        return shippingRuleRepository.findAll();
    }

    // 🔹 Buscar una regla por ID
    public ShippingRule findById(Long id) {
        return shippingRuleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Regla no encontrada con ID: " + id));
    }

    // 🔹 Crear una nueva regla ligada a una zona
    public ShippingRule createRule(Long zoneId, ShippingRule rule) {
        Zone zone = zoneRepository.findById(zoneId)
                .orElseThrow(() -> new RuntimeException("Zona no encontrada con ID: " + zoneId));
        rule.setZone(zone);
        return shippingRuleRepository.save(rule);
    }

    // 🔹 Actualizar una regla
    public ShippingRule update(Long id, ShippingRule updatedRule) {
        ShippingRule existing = findById(id);
        existing.setBaseRate(updatedRule.getBaseRate());
        existing.setCostPerKm(updatedRule.getCostPerKm());
        return shippingRuleRepository.save(existing);
    }

    // 🔹 Eliminar una regla
    public void delete(Long id) {
        shippingRuleRepository.deleteById(id);
    }
}
