package com.example.biblioteca.app.accounts.infrasctructure.postgresql.implementations;

import com.example.biblioteca.app.accounts.infrasctructure.postgresql.entities.AccountEntity;
import com.example.biblioteca.modules.accounts.domain.aggregates.Account;
import com.example.biblioteca.modules.accounts.repositories.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PostgresAccountRepository implements AccountRepository {
    private final AccountRepositoryJPA accountRepositoryJPA;

    @Override
    public void create(Account account) {
        AccountEntity accountEntity = new AccountEntity(
                account.getId(),
                account.getEmail().getValue(),
                account.getPassword().getValue()
        );
        accountRepositoryJPA.save(accountEntity);
    }
}
