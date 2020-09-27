package com.example.biblioteca.modules.accounts.application;

import com.example.biblioteca.modules.accounts.domain.valueObjects.HashedPassword;
import com.example.biblioteca.modules.accounts.domain.valueObjects.PlainPassword;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class PasswordChecker {
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {    return new BCryptPasswordEncoder(); }

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public HashedPassword encode(PlainPassword plainPassword) {
        String encodedPassword = bCryptPasswordEncoder.encode(plainPassword.getValue());

        return new HashedPassword(encodedPassword);
    }
}
