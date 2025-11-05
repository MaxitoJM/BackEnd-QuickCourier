package com.quickcourier.quickcourier_api.service.impl;

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
    public User register(User userPlain) {
        if (userPlain.getEmail() == null || userPlain.getEmail().isBlank()) {
            throw new IllegalArgumentException("El email es obligatorio");
        }
        if (userPlain.getPasswordHash() == null || userPlain.getPasswordHash().isBlank()) {
            throw new IllegalArgumentException("La contraseña es obligatoria");
        }

        // Verificar si ya existe
        if (userRepository.findByEmail(userPlain.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Ya existe un usuario con ese email");
        }

        // Encriptar la contraseña
        String hashed = passwordEncoder.encode(userPlain.getPasswordHash());
        userPlain.setPasswordHash(hashed);

        // Rol por defecto
        if (userPlain.getRole() == null || userPlain.getRole().isBlank()) {
            userPlain.setRole("USER");
        }

        return userRepository.save(userPlain);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
