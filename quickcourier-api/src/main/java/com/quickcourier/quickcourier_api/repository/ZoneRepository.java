package com.quickcourier.quickcourier_api.repository;

import com.quickcourier.quickcourier_api.domain.model.Zone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ZoneRepository extends JpaRepository<Zone, Long> {
}
