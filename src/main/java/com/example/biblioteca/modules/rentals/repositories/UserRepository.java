package com.example.biblioteca.modules.rentals.repositories;

import com.example.biblioteca.modules.rentals.domain.aggregates.User;

public interface UserRepository {
    void create(User user);
}
