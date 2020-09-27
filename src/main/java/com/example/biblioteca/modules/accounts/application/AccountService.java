package com.example.biblioteca.modules.accounts.application;

import com.example.biblioteca.modules.accounts.domain.aggregates.Account;
import com.example.biblioteca.modules.accounts.domain.valueObjects.PlainPassword;
import com.example.biblioteca.modules.accounts.repositories.AccountRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final PasswordChecker passwordChecker;

    public void register(String email, String password) {
        String hashedPassword = passwordChecker.encode(new PlainPassword(password));
        Account account = new Account(email, hashedPassword);
        accountRepository.create(account);
    }
}
