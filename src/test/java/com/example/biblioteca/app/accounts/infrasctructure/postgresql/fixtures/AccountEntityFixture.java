package com.example.biblioteca.app.accounts.infrasctructure.postgresql.fixtures;


import com.example.biblioteca.app.accounts.infrasctructure.postgresql.entities.AccountEntity;

import java.util.UUID;

public class AccountEntityFixture {
    public static UUID ACCOUNT_ENTRY_ID = UUID.randomUUID();
    public static String ACCOUNT_ENTRY_EMAIL = "example@biblioteca.com";
    public static String ENCODED_ENTRY_PASSWORD = "encodedPassword";

    public static AccountEntity customAccountEntity(UUID id, String email, String password) {
        return new AccountEntity(id, email, password);
    }

    public static AccountEntity defaultAccountEntity() {
        return new AccountEntity(ACCOUNT_ENTRY_ID, ACCOUNT_ENTRY_EMAIL, ENCODED_ENTRY_PASSWORD);
    }
}
