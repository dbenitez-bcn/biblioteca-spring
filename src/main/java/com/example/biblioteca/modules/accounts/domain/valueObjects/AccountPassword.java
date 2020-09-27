package com.example.biblioteca.modules.accounts.domain.valueObjects;

import com.example.biblioteca.modules.accounts.domain.exceptions.InvalidPasswordFormat;
import lombok.Getter;

@Getter
public class AccountPassword {
    private final String value;

    public AccountPassword(String value) {
        if (!isPasswordValid(value)) {
            throw new InvalidPasswordFormat();
        }
        this.value = value;
    }

    private boolean isPasswordValid(String password) {
        return password.matches("^(?=.*[0-9])(?=.*[a-zA-Z]).{8,}$");
    }
}
