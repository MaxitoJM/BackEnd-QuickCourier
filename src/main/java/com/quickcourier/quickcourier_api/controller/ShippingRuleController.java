package com.quickcourier.quickcourier_api.controller;

import com.quickcourier.quickcourier_api.domain.dto.ShippingRuleRequest;
import com.quickcourier.quickcourier_api.domain.model.ShippingRule;
import com.quickcourier.quickcourier_api.domain.model.Zone;
import com.quickcourier.quickcourier_api.repository.ShippingRuleRepository;
import com.quickcourier.quickcourier_api.repository.ZoneRepository;
import jakarta.validation.Valid;
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

    @GetMapping
    public List<ShippingRule> getAllRules() {
        return shippingRuleRepository.findAll();
    }

    @GetMapping("/{id}")
    public ShippingRule getRuleById(@PathVariable Long id) {
        return shippingRuleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Regla no encontrada"));
    }

    @PostMapping
    public ShippingRule createRule(@RequestParam Long zoneId, @Valid @RequestBody ShippingRuleRequest request) {
        Zone zone = zoneRepository.findById(zoneId)
                .orElseThrow(() -> new RuntimeException("Zona no encontrada"));
        ShippingRule rule = new ShippingRule();
        rule.setBaseRate(request.getBaseRate());
        rule.setCostPerKm(request.getCostPerKm());
        rule.setZone(zone);
        return shippingRuleRepository.save(rule);
    }

    @PutMapping("/{id}")
    public ShippingRule updateRule(@PathVariable Long id, @Valid @RequestBody ShippingRuleRequest request) {
        ShippingRule existing = shippingRuleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Regla no encontrada"));
        existing.setBaseRate(request.getBaseRate());
        existing.setCostPerKm(request.getCostPerKm());
        return shippingRuleRepository.save(existing);
    }

    @DeleteMapping("/{id}")
    public void deleteRule(@PathVariable Long id) {
        shippingRuleRepository.deleteById(id);
    }
}
