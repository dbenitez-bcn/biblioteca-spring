package com.example.biblioteca.modules.accounts.application;

import com.example.biblioteca.modules.accounts.domain.aggregates.Account;
import com.example.biblioteca.modules.accounts.repositories.AccountRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;

    public void register(String email, String password) {
        Account account = new Account(email, password);
        accountRepository.create(account);
    }
}
