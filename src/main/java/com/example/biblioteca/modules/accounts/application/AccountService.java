package com.example.biblioteca.modules.accounts.application;

import com.example.biblioteca.modules.accounts.domain.aggregates.Account;
import com.example.biblioteca.modules.accounts.domain.exceptions.EmailAlreadyInUse;
import com.example.biblioteca.modules.accounts.domain.valueObjects.AccountEmail;
import com.example.biblioteca.modules.accounts.domain.valueObjects.PlainPassword;
import com.example.biblioteca.modules.accounts.repositories.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final PasswordChecker passwordChecker;

    public void register(String email, String password) {
        Optional<Account> accountMaybe = accountRepository.getByEmail(new AccountEmail(email));
        accountMaybe.ifPresent((account) -> {
            throw new EmailAlreadyInUse();
        });
        String hashedPassword = passwordChecker.encode(new PlainPassword(password));
        Account account = new Account(email, hashedPassword);
        accountRepository.create(account);
    }
}
