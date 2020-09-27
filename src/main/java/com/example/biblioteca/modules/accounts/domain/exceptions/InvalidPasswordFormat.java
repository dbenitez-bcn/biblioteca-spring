package com.example.biblioteca.modules.accounts.domain.exceptions;

public class InvalidPasswordFormat extends RuntimeException {
    public InvalidPasswordFormat() {
        super("Should have at least 8 characters and contain numbers and letters");
    }
}
