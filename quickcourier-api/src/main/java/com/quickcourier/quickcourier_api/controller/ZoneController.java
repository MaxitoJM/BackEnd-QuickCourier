package com.quickcourier.quickcourier_api.controller;

import com.quickcourier.quickcourier_api.domain.model.Zone;
import com.quickcourier.quickcourier_api.repository.ZoneRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/zones")
@CrossOrigin(origins = "*") // Permite probar desde frontend
public class ZoneController {

    private final ZoneRepository zoneRepository;

    public ZoneController(ZoneRepository zoneRepository) {
        this.zoneRepository = zoneRepository;
    }

    // ✅ Obtener todas las zonas
    @GetMapping
    public List<Zone> getAllZones() {
        return zoneRepository.findAll();
    }

    // ✅ Obtener una zona por ID
    @GetMapping("/{id}")
    public Zone getZoneById(@PathVariable Long id) {
        return zoneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Zona no encontrada"));
    }

    // ✅ Crear una nueva zona
    @PostMapping
    public Zone createZone(@RequestBody Zone zone) {
        return zoneRepository.save(zone);
    }

    // ✅ Actualizar una zona
    @PutMapping("/{id}")
    public Zone updateZone(@PathVariable Long id, @RequestBody Zone updatedZone) {
        Zone existing = zoneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Zona no encontrada"));
        existing.setName(updatedZone.getName());
        existing.setMultiplier(updatedZone.getMultiplier());
        return zoneRepository.save(existing);
    }

    // ✅ Eliminar una zona
    @DeleteMapping("/{id}")
    public void deleteZone(@PathVariable Long id) {
        zoneRepository.deleteById(id);
    }
}
