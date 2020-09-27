package com.example.biblioteca.app.accounts.infrasctructure.postgresql.implementations;

import com.example.biblioteca.app.accounts.infrasctructure.postgresql.entities.AccountEntity;
import com.example.biblioteca.modules.accounts.domain.aggregates.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static com.example.biblioteca.modules.accounts.domain.fixtures.AccountFixture.defaultAccount;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
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
    }
}