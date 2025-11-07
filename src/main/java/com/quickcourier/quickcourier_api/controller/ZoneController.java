package com.quickcourier.quickcourier_api.controller;

import com.quickcourier.quickcourier_api.domain.dto.ZoneRequest;
import com.quickcourier.quickcourier_api.domain.model.Zone;
import com.quickcourier.quickcourier_api.repository.ZoneRepository;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/zones")
@CrossOrigin(origins = "*")
public class ZoneController {

    private final ZoneRepository zoneRepository;

    public ZoneController(ZoneRepository zoneRepository) {
        this.zoneRepository = zoneRepository;
    }

    @GetMapping
    public List<Zone> getAllZones() {
        return zoneRepository.findAll();
    }

    @GetMapping("/{id}")
    public Zone getZoneById(@PathVariable Long id) {
        return zoneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Zona no encontrada"));
    }

    @PostMapping
    public Zone createZone(@Valid @RequestBody ZoneRequest request) {
        Zone zone = new Zone();
        zone.setName(request.getName());
        zone.setMultiplier(request.getMultiplier());
        return zoneRepository.save(zone);
    }

    @PutMapping("/{id}")
    public Zone updateZone(@PathVariable Long id, @Valid @RequestBody ZoneRequest request) {
        Zone existing = zoneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Zona no encontrada"));
        existing.setName(request.getName());
        existing.setMultiplier(request.getMultiplier());
        return zoneRepository.save(existing);
    }

    @DeleteMapping("/{id}")
    public void deleteZone(@PathVariable Long id) {
        zoneRepository.deleteById(id);
    }
}
