package com.quickcourier.quickcourier_api.repository;

import com.quickcourier.quickcourier_api.domain.model.Quote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuoteRepository extends JpaRepository<Quote, Long> {
}
