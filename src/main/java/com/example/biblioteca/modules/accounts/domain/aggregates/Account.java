package com.example.biblioteca.modules.accounts.domain.aggregates;

import com.example.biblioteca.modules.accounts.domain.valueObjects.AccountEmail;
import com.example.biblioteca.modules.accounts.domain.valueObjects.AccountRole;
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
    private AccountRole role;

    public Account(String email, String password) {
        this.id = UUID.randomUUID();
        this.email = new AccountEmail(email);
        this.password = new HashedPassword(password);
        this.role = AccountRole.USER;
    }

    public Account(UUID id, String email, String password, String role) {
        this.id = id;
        this.email = new AccountEmail(email);
        this.password = new HashedPassword(password);
        this.role = AccountRole.valueOf(role);
    }
}
