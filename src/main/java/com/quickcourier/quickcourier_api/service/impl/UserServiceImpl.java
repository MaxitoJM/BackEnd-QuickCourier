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

    /**
     * Registra un nuevo usuario validando duplicados y encriptando la contraseña.
     */
    @Transactional
    @Override
    public User register(User userPlain) {
        // Validaciones básicas
        if (userPlain.getEmail() == null || userPlain.getEmail().isBlank()) {
            throw new IllegalArgumentException("El email es obligatorio");
        }
        if (userPlain.getPasswordHash() == null || userPlain.getPasswordHash().isBlank()) {
            throw new IllegalArgumentException("La contraseña es obligatoria");
        }

        // Verificar duplicado
        if (userRepository.findByEmail(userPlain.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Ya existe un usuario con ese email");
        }

        // Encriptar contraseña
        String hashedPassword = passwordEncoder.encode(userPlain.getPasswordHash());
        userPlain.setPasswordHash(hashedPassword);

        // Asignar rol por defecto si no se especifica
        if (userPlain.getRole() == null || userPlain.getRole().isBlank()) {
            userPlain.setRole("USER");
        }

        // Guardar y retornar el usuario creado
        return userRepository.save(userPlain);
    }

    /**
     * Busca un usuario por su email.
     */
    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
