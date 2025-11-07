package com.quickcourier.quickcourier_api.service;

import com.quickcourier.quickcourier_api.domain.model.Zone;
import com.quickcourier.quickcourier_api.repository.ZoneRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ZoneService {

    private final ZoneRepository zoneRepository;

    public ZoneService(ZoneRepository zoneRepository) {
        this.zoneRepository = zoneRepository;
    }

    // 🔹 Obtener todas las zonas
    public List<Zone> findAll() {
        return zoneRepository.findAll();
    }

    // 🔹 Buscar zona por ID
    public Zone findById(Long id) {
        return zoneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Zona no encontrada con ID: " + id));
    }

    // 🔹 Crear nueva zona
    public Zone save(Zone zone) {
        return zoneRepository.save(zone);
    }

    // 🔹 Actualizar zona existente
    public Zone update(Long id, Zone updatedZone) {
        Zone existing = findById(id);
        existing.setName(updatedZone.getName());
        existing.setMultiplier(updatedZone.getMultiplier());
        return zoneRepository.save(existing);
    }

    // 🔹 Eliminar zona
    public void delete(Long id) {
        zoneRepository.deleteById(id);
    }
}
