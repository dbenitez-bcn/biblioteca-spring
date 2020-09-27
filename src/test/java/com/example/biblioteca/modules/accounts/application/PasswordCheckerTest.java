package com.example.biblioteca.modules.accounts.application;

import com.example.biblioteca.modules.accounts.domain.valueObjects.PlainPassword;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static com.example.biblioteca.modules.accounts.domain.fixtures.AccountFixture.ACCOUNT_PASSWORD;
import static com.example.biblioteca.modules.accounts.domain.fixtures.AccountFixture.ENCODED_PASSWORD;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class PasswordCheckerTest {
    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private PasswordChecker sut;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    void encode_shouldEncodeTheRawPassword() {
        PlainPassword plainPassword = new PlainPassword(ACCOUNT_PASSWORD);
        when(bCryptPasswordEncoder.encode(ACCOUNT_PASSWORD)).thenReturn(ENCODED_PASSWORD);

        String result = sut.encode(plainPassword);

        assertThat(result).isEqualTo(ENCODED_PASSWORD);
    }
}