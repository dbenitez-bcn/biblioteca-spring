package com.example.biblioteca.modules.accounts.application;

import com.example.biblioteca.modules.accounts.domain.valueObjects.HashedPassword;
import com.example.biblioteca.modules.accounts.domain.valueObjects.PlainPassword;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class PasswordManager {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public String encode(PlainPassword plainPassword) {
        return bCryptPasswordEncoder.encode(plainPassword.getValue());
    }

    public boolean matches(PlainPassword plainPassword, HashedPassword hashedPassword) {
        return bCryptPasswordEncoder.matches(plainPassword.getValue(), hashedPassword.getValue());
    }
}
