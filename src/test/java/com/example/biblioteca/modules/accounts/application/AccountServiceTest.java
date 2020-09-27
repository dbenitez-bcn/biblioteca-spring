package com.example.biblioteca.modules.accounts.application;

import com.example.biblioteca.modules.accounts.domain.aggregates.Account;
import com.example.biblioteca.modules.accounts.domain.exceptions.InvalidEmailAddress;
import com.example.biblioteca.modules.accounts.domain.exceptions.InvalidPasswordFormat;
import com.example.biblioteca.modules.accounts.repositories.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static com.example.biblioteca.modules.accounts.domain.fixtures.AccountFixture.ACCOUNT_EMAIL;
import static com.example.biblioteca.modules.accounts.domain.fixtures.AccountFixture.ACCOUNT_PASSWORD;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

class AccountServiceTest {
    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService sut;

    @Captor
    private ArgumentCaptor<Account> accountCaptor;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    void register_shouldCreateANewUser() {
        sut.register(ACCOUNT_EMAIL, ACCOUNT_PASSWORD);

        verify(accountRepository).create(accountCaptor.capture());
        Account capturedAccount = accountCaptor.getValue();
        assertThat(capturedAccount.getEmail().getValue()).isEqualTo(ACCOUNT_EMAIL);
        assertThat(capturedAccount.getPassword().getValue()).isEqualTo(ACCOUNT_PASSWORD);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "  ", "invalid#email.com", "invalid.com", "email@example.c"})
    void register_whenInvalidEmailIsIntroduced_shouldThrowInvalidEmailAddress(String email) {
        assertThatThrownBy(() -> sut.register(email, ACCOUNT_PASSWORD))
                .isInstanceOf(InvalidEmailAddress.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "  ", "password", "1234", "pass123"})
    void register_whenInvalidPasswordIsIntroduced_shouldThrowInvalidPasswordFormat(String password) {
        assertThatThrownBy(() -> sut.register(ACCOUNT_EMAIL, password))
                .isInstanceOf(InvalidPasswordFormat.class)
                .hasMessage("Should have at least 8 characters and contain numbers and letters");
    }
}