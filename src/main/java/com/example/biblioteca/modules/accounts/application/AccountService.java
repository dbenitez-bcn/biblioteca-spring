package com.example.biblioteca.modules.accounts.application;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.biblioteca.modules.accounts.domain.aggregates.Account;
import com.example.biblioteca.modules.accounts.domain.exceptions.EmailAlreadyInUse;
import com.example.biblioteca.modules.accounts.domain.exceptions.LoginFailed;
import com.example.biblioteca.modules.accounts.domain.valueObjects.AccountEmail;
import com.example.biblioteca.modules.accounts.domain.valueObjects.PlainPassword;
import com.example.biblioteca.modules.accounts.repositories.AccountRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWT jwt;

    public void register(String email, String password) {
        Optional<Account> accountMaybe = accountRepository.getByEmail(new AccountEmail(email));
        accountMaybe.ifPresent((account) -> {
            throw new EmailAlreadyInUse();
        });
        String hashedPassword = passwordEncoder.encode(new PlainPassword(password));
        Account account = new Account(email, hashedPassword);
        accountRepository.create(account);
    }

    public String login(String email, String password) {
        Optional<Account> accountMaybe = accountRepository.getByEmail(new AccountEmail(email));
        if (accountMaybe.isPresent()) {
            boolean passwordMatches = passwordEncoder
                .matches(new PlainPassword(password), accountMaybe.get()
                                                                  .getPassword());
            if (passwordMatches) {
                String token = jwt
                    .create()
                    .withSubject(accountMaybe
                        .get()
                        .getId()
                        .toString())
                    .withClaim("email", accountMaybe.get()
                                                    .getEmail()
                                                    .getValue())
                    .sign(Algorithm.HMAC512("MY_SECRET"));

                return token;
            }
        }
        throw new LoginFailed();
    }
}
