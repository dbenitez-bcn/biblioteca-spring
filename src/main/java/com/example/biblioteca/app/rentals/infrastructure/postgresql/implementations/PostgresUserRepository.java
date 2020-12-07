package com.example.biblioteca.app.rentals.infrastructure.postgresql.implementations;

import com.example.biblioteca.app.rentals.infrastructure.postgresql.entities.UserEntity;
import com.example.biblioteca.modules.rentals.domain.aggregates.User;
import com.example.biblioteca.modules.rentals.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostgresUserRepository implements UserRepository {
    private final UserRepositoryJPA repository;

    @Override
    public void create(User user) {
        UserEntity userEntity = new UserEntity(user.getId().getValue(), user.getEmail().getValue());
        repository.save(userEntity);
    }
}
