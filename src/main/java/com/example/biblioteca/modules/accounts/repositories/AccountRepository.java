package com.example.biblioteca.modules.accounts.repositories;

import com.example.biblioteca.modules.accounts.domain.aggregates.Account;

public interface AccountRepository {
    void create(Account account);
}
