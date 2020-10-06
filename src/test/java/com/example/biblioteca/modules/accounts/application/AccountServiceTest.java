package com.example.biblioteca.modules.accounts.application;

import com.example.biblioteca.modules.accounts.domain.aggregates.Account;
import com.example.biblioteca.modules.accounts.domain.exceptions.EmailAlreadyInUse;
import com.example.biblioteca.modules.accounts.domain.exceptions.InvalidEmailAddress;
import com.example.biblioteca.modules.accounts.domain.exceptions.InvalidPasswordFormat;
import com.example.biblioteca.modules.accounts.domain.valueObjects.AccountEmail;
import com.example.biblioteca.modules.accounts.domain.valueObjects.PlainPassword;
import com.example.biblioteca.modules.accounts.repositories.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static com.example.biblioteca.modules.accounts.domain.fixtures.AccountFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class AccountServiceTest {
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AccountService sut;

    @Captor
    private ArgumentCaptor<Account> accountCaptor;
    @Captor
    private ArgumentCaptor<PlainPassword> passwordCaptor;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }


    @Test
    void register_shouldCreateANewUser() {
        when(passwordEncoder.encode(any(PlainPassword.class))).thenReturn(ENCODED_PASSWORD);

        sut.register(ACCOUNT_EMAIL, ACCOUNT_PASSWORD);

        verify(accountRepository).create(accountCaptor.capture());
        Account capturedAccount = accountCaptor.getValue();
        assertThat(capturedAccount.getEmail().getValue()).isEqualTo(ACCOUNT_EMAIL);
        assertThat(capturedAccount.getPassword().getValue()).isEqualTo(ENCODED_PASSWORD);
    }

    @Test
    void register_shouldEncodeThePassword() {
        when(passwordEncoder.encode(any(PlainPassword.class))).thenReturn(ENCODED_PASSWORD);

        sut.register(ACCOUNT_EMAIL, ACCOUNT_PASSWORD);

        verify(passwordEncoder).encode(passwordCaptor.capture());
        PlainPassword capturedPassword = passwordCaptor.getValue();
        assertThat(capturedPassword.getValue()).isEqualTo(ACCOUNT_PASSWORD);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "  ", "invalid#email.com", "invalid.com", "email@example.c"})
    void register_whenInvalidEmailIsIntroduced_shouldThrowInvalidEmailAddress(String email) {
        assertThatThrownBy(() -> sut.register(email, ACCOUNT_PASSWORD))
                .isInstanceOf(InvalidEmailAddress.class);
    }

    @Test
    void register_whenNodEmailIsIntroduced_shouldThrowInvalidEmailAddress() {
        assertThatThrownBy(() -> sut.register(null, ACCOUNT_PASSWORD))
                .isInstanceOf(InvalidEmailAddress.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "  ", "password", "1234", "pass123"})
    void register_whenInvalidPasswordIsIntroduced_shouldThrowInvalidPasswordFormat(String password) {
        assertThatThrownBy(() -> sut.register(ACCOUNT_EMAIL, password))
                .isInstanceOf(InvalidPasswordFormat.class)
                .hasMessage("Should have at least 8 characters and contain numbers and letters");
    }

    @Test
    void register_whenNoPasswordIsIntroduced_shouldThrowInvalidPasswordFormat() {
        assertThatThrownBy(() -> sut.register(ACCOUNT_EMAIL, null))
                .isInstanceOf(InvalidPasswordFormat.class)
                .hasMessage("Should have at least 8 characters and contain numbers and letters");
    }

    @Test
    void register_whenAccountAlreadyExist_shouldThrowEmailAlreadyInUse() {
        when(accountRepository.getByEmail(new AccountEmail(ACCOUNT_EMAIL))).thenReturn(Optional.of(defaultAccount()));

        assertThatThrownBy(() -> sut.register(ACCOUNT_EMAIL, ACCOUNT_PASSWORD))
                .isInstanceOf(EmailAlreadyInUse.class);

    }
}