package com.example.biblioteca.app.accounts.infrasctructure.postgresql.implementations;

import com.example.biblioteca.app.accounts.infrasctructure.postgresql.entities.AccountEntity;
import com.example.biblioteca.modules.accounts.domain.aggregates.Account;
import com.example.biblioteca.modules.accounts.domain.valueObjects.AccountEmail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static com.example.biblioteca.app.accounts.infrasctructure.postgresql.fixtures.AccountEntityFixture.defaultAccountEntity;
import static com.example.biblioteca.modules.accounts.domain.fixtures.AccountFixture.ACCOUNT_EMAIL;
import static com.example.biblioteca.modules.accounts.domain.fixtures.AccountFixture.defaultAccount;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class PostgresAccountRepositoryTest {
    @Mock
    private AccountRepositoryJPA accountRepositoryJPA;

    @InjectMocks
    private PostgresAccountRepository sut;

    @Captor
    private ArgumentCaptor<AccountEntity> accountCaptor;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    void create_shouldCreateANewUser() {
        Account account = defaultAccount();

        sut.create(account);

        verify(accountRepositoryJPA).save(accountCaptor.capture());
        AccountEntity capturedAccount = accountCaptor.getValue();
        assertThat(capturedAccount.getId()).isEqualTo(account.getId());
        assertThat(capturedAccount.getEmail()).isEqualTo(account.getEmail().getValue());
        assertThat(capturedAccount.getPassword()).isEqualTo(account.getPassword().getValue());
        assertThat(capturedAccount.getRole()).isEqualTo(account.getRole().name());
    }

    @Test
    void getByEmail_whenUserIsFound_shouldReturnAnAccount() {
        AccountEntity accountEntity = defaultAccountEntity();
        when(accountRepositoryJPA.findByEmail(ACCOUNT_EMAIL)).thenReturn(Optional.of(accountEntity));

        Account result = sut.getByEmail(new AccountEmail(ACCOUNT_EMAIL)).get();

        assertThat(result.getId()).isEqualTo(accountEntity.getId());
        assertThat(result.getEmail().getValue()).isEqualTo(accountEntity.getEmail());
        assertThat(result.getPassword().getValue()).isEqualTo(accountEntity.getPassword());
        assertThat(result.getRole().name()).isEqualTo(accountEntity.getRole());
    }

    @Test
    void getByEmail_whenUserIsNotFound_shouldReturnEmptyOptional() {
        when(accountRepositoryJPA.findByEmail(ACCOUNT_EMAIL)).thenReturn(Optional.empty());

        Optional<Account> result = sut.getByEmail(new AccountEmail(ACCOUNT_EMAIL));

        assertThat(result).isEmpty();
    }
}