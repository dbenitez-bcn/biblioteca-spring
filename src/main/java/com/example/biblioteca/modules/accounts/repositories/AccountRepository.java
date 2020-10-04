package com.example.biblioteca.modules.accounts.repositories;

import com.example.biblioteca.modules.accounts.domain.aggregates.Account;
import com.example.biblioteca.modules.accounts.domain.valueObjects.AccountEmail;

import java.util.Optional;

public interface AccountRepository {
    void create(Account account);
    Optional<Account> getByEmail(AccountEmail email);
}
