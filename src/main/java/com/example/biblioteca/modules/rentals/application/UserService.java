package com.example.biblioteca.modules.rentals.application;

import com.example.biblioteca.modules.rentals.domain.aggregates.User;
import com.example.biblioteca.modules.rentals.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

    public void create(UUID id, String email) {
        User user = new User(id, email);
        repository.create(user);
    }
}
