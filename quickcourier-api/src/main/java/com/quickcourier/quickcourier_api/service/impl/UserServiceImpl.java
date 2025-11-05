package com.quickcourier.quickcourier_api.service.impl;

import com.quickcourier.quickcourier_api.domain.dto.RegisterRequest;
import com.quickcourier.quickcourier_api.domain.model.User;
import com.quickcourier.quickcourier_api.repository.UserRepository;
import com.quickcourier.quickcourier_api.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public User register(RegisterRequest request) {
        if (request.getEmail() == null || request.getEmail().isBlank()) {
            throw new IllegalArgumentException("El email es obligatorio");
        }
        if (request.getPassword() == null || request.getPassword().isBlank()) {
            throw new IllegalArgumentException("La contraseña es obligatoria");
        }

        // Verificar si ya existe
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Ya existe un usuario con ese email");
        }

        User user = new User();
        user.setEmail(request.getEmail());

        // Encriptar la contraseña
        String hashed = passwordEncoder.encode(request.getPassword());
        user.setPasswordHash(hashed);

        // Rol por defecto
        String role = request.getRole();
        user.setRole((role == null || role.isBlank()) ? "USER" : role);

        return userRepository.save(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
