package com.quickcourier.quickcourier_api.controller;

import com.quickcourier.quickcourier_api.domain.model.ShippingRule;
import com.quickcourier.quickcourier_api.domain.model.Zone;
import com.quickcourier.quickcourier_api.repository.ShippingRuleRepository;
import com.quickcourier.quickcourier_api.repository.ZoneRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rules")
@CrossOrigin(origins = "*")
public class ShippingRuleController {

    private final ShippingRuleRepository shippingRuleRepository;
    private final ZoneRepository zoneRepository;

    public ShippingRuleController(ShippingRuleRepository shippingRuleRepository, ZoneRepository zoneRepository) {
        this.shippingRuleRepository = shippingRuleRepository;
        this.zoneRepository = zoneRepository;
    }

    // ✅ Obtener todas las reglas
    @GetMapping
    public List<ShippingRule> getAllRules() {
        return shippingRuleRepository.findAll();
    }

    // ✅ Obtener una regla por ID
    @GetMapping("/{id}")
    public ShippingRule getRuleById(@PathVariable Long id) {
        return shippingRuleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Regla no encontrada"));
    }

    // ✅ Crear una nueva regla (ligada a una zona)
    @PostMapping
    public ShippingRule createRule(@RequestParam Long zoneId, @RequestBody ShippingRule rule) {
        Zone zone = zoneRepository.findById(zoneId)
                .orElseThrow(() -> new RuntimeException("Zona no encontrada"));
        rule.setZone(zone);
        return shippingRuleRepository.save(rule);
    }

    // ✅ Actualizar una regla existente
    @PutMapping("/{id}")
    public ShippingRule updateRule(@PathVariable Long id, @RequestBody ShippingRule updatedRule) {
        ShippingRule existing = shippingRuleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Regla no encontrada"));
        existing.setBaseRate(updatedRule.getBaseRate());
        existing.setCostPerKm(updatedRule.getCostPerKm());
        return shippingRuleRepository.save(existing);
    }

    // ✅ Eliminar una regla
    @DeleteMapping("/{id}")
    public void deleteRule(@PathVariable Long id) {
        shippingRuleRepository.deleteById(id);
    }
}
