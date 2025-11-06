package com.quickcourier.quickcourier_api.service;

import com.quickcourier.quickcourier_api.domain.model.User;

import java.util.Optional;

public interface UserService {
    User register(User userPlain);
    Optional<User> findByEmail(String email);
}
