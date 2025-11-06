package com.quickcourier.quickcourier_api.controller;

import com.quickcourier.quickcourier_api.domain.model.User;
import com.quickcourier.quickcourier_api.domain.dto.LoginRequest;
import com.quickcourier.quickcourier_api.domain.dto.RegisterRequest;
import com.quickcourier.quickcourier_api.security.JwtTokenUtil;
import com.quickcourier.quickcourier_api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(UserService userService,
                          JwtTokenUtil jwtTokenUtil,
                          PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 🧾 Registro de nuevo usuario
     */
    @PostMapping("/register")
    public User register(@RequestBody RegisterRequest request) {
        if (request.getEmail() == null || request.getEmail().isBlank() ||
            request.getPassword() == null || request.getPassword().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email y contraseña son obligatorios");
        }

        try {
            User user = new User();
            user.setEmail(request.getEmail());
            user.setPasswordHash(request.getPassword());
            user.setRole("USER"); // Rol por defecto
            return userService.register(user);
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

    /**
     * 🔐 Inicio de sesión (Login)
     */
    @PostMapping("/login")
    public Map<String, String> login(@RequestBody LoginRequest request) {
        String email = request.getEmail();
        String password = request.getPassword();

        if (email == null || email.isBlank() || password == null || password.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email y contraseña son obligatorios");
        }

        User user = userService.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        if (!passwordEncoder.matches(password, user.getPasswordHash())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales inválidas");
        }

        String token = jwtTokenUtil.generateToken(email);
        return Map.of(
                "token", token,
                "email", email,
                "role", user.getRole()
        );
    }
}
