package com.quickcourier.quickcourier_api.service;

import com.quickcourier.quickcourier_api.domain.model.Quote;
import com.quickcourier.quickcourier_api.domain.model.ShippingRule;
import com.quickcourier.quickcourier_api.domain.model.Zone;
import com.quickcourier.quickcourier_api.domain.dto.QuoteResponse;
import com.quickcourier.quickcourier_api.repository.QuoteRepository;
import com.quickcourier.quickcourier_api.repository.ShippingRuleRepository;
import com.quickcourier.quickcourier_api.repository.ZoneRepository;
import org.springframework.stereotype.Service;

@Service
public class QuoteService {

    private final QuoteRepository quoteRepository;
    private final ZoneRepository zoneRepository;
    private final ShippingRuleRepository shippingRuleRepository;

    public QuoteService(QuoteRepository quoteRepository,
                        ZoneRepository zoneRepository,
                        ShippingRuleRepository shippingRuleRepository) {
        this.quoteRepository = quoteRepository;
        this.zoneRepository = zoneRepository;
        this.shippingRuleRepository = shippingRuleRepository;
    }

    // Lógica principal de cotización
    public QuoteResponse calculateQuote(double basePrice, double distanceKm, boolean weekend, boolean insurance, Long zoneId) {
        Zone zone = zoneRepository.findById(zoneId)
                .orElseThrow(() -> new RuntimeException("Zona no encontrada con ID: " + zoneId));

        // Si implementaste Opción B del repositorio, cambia esta línea por findTopByZoneIdOrderByIdDesc(zoneId)
        ShippingRule rule = shippingRuleRepository.findByZone(zone)
                .orElseThrow(() -> new RuntimeException("No existe una regla para esta zona."));

        double total = basePrice + (distanceKm * rule.getCostPerKm() * zone.getMultiplier());
        if (weekend) total *= 1.1;
        if (insurance) total *= 1.05;

        Quote quote = new Quote();
        quote.setBasePrice(basePrice);
        quote.setDistanceKm(distanceKm);
        quote.setWeekend(weekend);
        quote.setInsurance(insurance);
        quote.setTotal(total);
        quote.setZone(zone);
        quoteRepository.save(quote);

        // Opción A: usando constructor agregado en QuoteResponse
        return new QuoteResponse(zone.getName(), distanceKm, weekend, insurance, total);

    }
}
