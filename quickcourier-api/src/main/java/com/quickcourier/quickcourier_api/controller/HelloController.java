package com.quickcourier.quickcourier_api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/api/public/hello")
    public String hello() {
        return "✅ QuickCourier API funcionando correctamente con Spring Boot 3 y Java 21";
    }
}
