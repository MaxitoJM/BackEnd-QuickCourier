package com.quickcourier.quickcourier_api.controller;

import com.quickcourier.quickcourier_api.domain.dto.QuoteRequest;
import com.quickcourier.quickcourier_api.domain.dto.QuoteResponse;
import com.quickcourier.quickcourier_api.domain.model.Quote;
import com.quickcourier.quickcourier_api.repository.QuoteRepository;
import com.quickcourier.quickcourier_api.service.QuoteService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quote")
@CrossOrigin(origins = "*")
public class QuoteController {

    private final QuoteService quoteService;
    private final QuoteRepository quoteRepository;

    public QuoteController(QuoteService quoteService, QuoteRepository quoteRepository) {
        this.quoteService = quoteService;
        this.quoteRepository = quoteRepository;
    }

    @PostMapping
    public QuoteResponse calculateQuote(@Valid @RequestBody QuoteRequest request) {
        return quoteService.calculateQuote(
                request.getBasePrice(),
                request.getDistanceKm(),
                request.isWeekend(),
                request.isInsurance(),
                request.getZoneId()
        );
    }

    @GetMapping("/all")
    public List<Quote> getAllQuotes() {
        return quoteRepository.findAll();
    }
}
