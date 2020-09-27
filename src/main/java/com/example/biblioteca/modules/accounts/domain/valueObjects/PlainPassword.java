package com.example.biblioteca.modules.accounts.domain.valueObjects;

import com.example.biblioteca.modules.accounts.domain.exceptions.InvalidPasswordFormat;
import lombok.Getter;

@Getter
public class PlainPassword {
    private final String value;

    public PlainPassword(String value) {
        if (value == null || !isPasswordValid(value)) {
            throw new InvalidPasswordFormat();
        }
        this.value = value;
    }

    private boolean isPasswordValid(String password) {
        return password.matches("^(?=.*[0-9])(?=.*[a-zA-Z]).{8,}$");
    }
}
