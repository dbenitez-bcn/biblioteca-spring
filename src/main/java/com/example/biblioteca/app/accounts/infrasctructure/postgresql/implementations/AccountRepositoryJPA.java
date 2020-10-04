package com.example.biblioteca.app.accounts.infrasctructure.postgresql.implementations;

import com.example.biblioteca.app.accounts.infrasctructure.postgresql.entities.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AccountRepositoryJPA extends JpaRepository<AccountEntity, UUID> {

    Optional<AccountEntity> findByEmail(String email);
}
