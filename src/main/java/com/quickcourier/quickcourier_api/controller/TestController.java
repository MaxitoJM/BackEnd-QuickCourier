package com.quickcourier.quickcourier_api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TestController {

    @GetMapping("/status")
    public ResponseEntity<String> status() {
        return ResponseEntity.ok("✅ QuickCourier API funcionando correctamente");
    }
}
