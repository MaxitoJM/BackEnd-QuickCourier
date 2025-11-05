package com.quickcourier.quickcourier_api.service;

import com.quickcourier.quickcourier_api.domain.dto.RegisterRequest;
import com.quickcourier.quickcourier_api.domain.model.User;

import java.util.Optional;

public interface UserService {
    User register(RegisterRequest request);
    Optional<User> findByEmail(String email);
}
