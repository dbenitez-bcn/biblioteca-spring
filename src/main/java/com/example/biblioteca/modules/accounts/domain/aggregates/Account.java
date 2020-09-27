package com.example.biblioteca.modules.accounts.domain.aggregates;

import com.example.biblioteca.modules.accounts.domain.valueObjects.AccountEmail;
import com.example.biblioteca.modules.accounts.domain.valueObjects.HashedPassword;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;


@Getter
@EqualsAndHashCode
public class Account {
    private UUID id;
    private AccountEmail email;
    private HashedPassword password;

    public Account(String email, String password) {
        this.id = UUID.randomUUID();
        this.email = new AccountEmail(email);
        this.password = new HashedPassword(password);
    }
}
