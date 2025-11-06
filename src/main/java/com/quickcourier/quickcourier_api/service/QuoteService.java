package com.quickcourier.quickcourier_api.service;

import com.quickcourier.quickcourier_api.domain.model.TaxPolicy;
import com.quickcourier.quickcourier_api.domain.model.Quote;
import com.quickcourier.quickcourier_api.domain.model.Zone;
import com.quickcourier.quickcourier_api.domain.dto.QuoteResponse;
import com.quickcourier.quickcourier_api.repository.QuoteRepository;
import com.quickcourier.quickcourier_api.repository.ZoneRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class QuoteService {
    
    private final TaxPolicy taxPolicy;
    private final QuoteRepository quoteRepository;
    private final ZoneRepository zoneRepository;

    public QuoteService(@Qualifier("ivaTaxPolicy") TaxPolicy taxPolicy,
                        QuoteRepository quoteRepository,
                        ZoneRepository zoneRepository) {
        this.taxPolicy = taxPolicy;
        this.quoteRepository = quoteRepository;
        this.zoneRepository = zoneRepository;
    }

    public QuoteResponse calculateQuote(double basePrice, double distanceKm, 
                                        boolean weekend, boolean insurance, Long zoneId) {
        // Buscar la zona y lanzar excepción si no existe
        Zone zone = zoneRepository.findById(zoneId)
            .orElseThrow(() -> new RuntimeException("Zona no encontrada"));

        // Calcular los diferentes componentes del precio
        double distanceFee = distanceKm * 200 * zone.getMultiplier();
        double weekendFee = weekend ? basePrice * 0.15 : 0;
        double insuranceFee = insurance ? 5000 : 0;

        // Calcular subtotal, impuestos y total
        double subtotal = basePrice + distanceFee + weekendFee + insuranceFee;
        double tax = taxPolicy.calculateTax(subtotal);
        double total = subtotal + tax;

        // Crear y guardar la entidad Quote
        Quote quote = new Quote();
        quote.setBasePrice(basePrice);
        quote.setDistanceKm(distanceKm);
        quote.setWeekend(weekend);
        quote.setInsurance(insurance);
        quote.setTotal(total);
        quote.setZone(zone);
        quoteRepository.save(quote);

        // Retornar el DTO con toda la información del cálculo
        return new QuoteResponse(
            basePrice, 
            distanceKm, 
            weekend, 
            insurance, 
            zone.getName(), 
            subtotal, 
            tax, 
            total
        );
    }
}