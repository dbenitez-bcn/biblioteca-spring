package com.example.biblioteca.modules.rentals.domain.valueObjects;

import com.example.biblioteca.modules.accounts.domain.exceptions.InvalidEmailAddress;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@EqualsAndHashCode
public class UserEmail {
    private final String value;

    public UserEmail(String value) {
        if (value == null || !isEmailValid(value)) {
            throw new InvalidEmailAddress();
        }

        this.value = value;
    }

    private boolean isEmailValid(String email) {
        Pattern emailRegex =
                Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = emailRegex.matcher(email);
        return matcher.find();
    }
}
