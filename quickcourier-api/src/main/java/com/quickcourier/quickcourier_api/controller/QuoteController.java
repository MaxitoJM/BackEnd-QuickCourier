package com.quickcourier.quickcourier_api.controller;

import com.quickcourier.quickcourier_api.domain.model.Quote;
import com.quickcourier.quickcourier_api.domain.dto.QuoteResponse;
import com.quickcourier.quickcourier_api.repository.QuoteRepository;
import com.quickcourier.quickcourier_api.service.QuoteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quote")
public class QuoteController {
    
    private final QuoteService quoteService;
    private final QuoteRepository quoteRepository;

    public QuoteController(QuoteService quoteService, QuoteRepository quoteRepository) {
        this.quoteService = quoteService;
        this.quoteRepository = quoteRepository;
    }

    // Endpoint para cotizar
    @GetMapping
    public QuoteResponse getQuote(
            @RequestParam double basePrice,
            @RequestParam double distanceKm,
            @RequestParam boolean weekend,
            @RequestParam boolean insurance,
            @RequestParam Long zoneId
    ) {
        return quoteService.calculateQuote(basePrice, distanceKm, weekend, insurance, zoneId);
    }

    // Endpoint para listar todas las cotizaciones guardadas
    @GetMapping("/all")
    public List<Quote> getAllQuotes() {
        return quoteRepository.findAll();
    }
}