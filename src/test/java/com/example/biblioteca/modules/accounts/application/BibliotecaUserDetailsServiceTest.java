package com.example.biblioteca.modules.accounts.application;

import com.example.biblioteca.modules.accounts.domain.aggregates.Account;
import com.example.biblioteca.modules.accounts.domain.valueObjects.AccountEmail;
import com.example.biblioteca.modules.accounts.repositories.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Optional;

import static com.example.biblioteca.modules.accounts.domain.fixtures.AccountFixture.ACCOUNT_EMAIL;
import static com.example.biblioteca.modules.accounts.domain.fixtures.AccountFixture.defaultAccount;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class BibliotecaUserDetailsServiceTest {
    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private BibliotecaUserDetailsService sut;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    void loadByUserName_whenUserExist_shouldLoadTheUser() {
        Account account = defaultAccount();
        User expectedUserDetails = new User(
                account.getEmail().getValue(),
                account.getPassword().getValue(),
                new ArrayList<>()
        );
        when(accountRepository.getByEmail(account.getEmail())).thenReturn(Optional.of(account));

        UserDetails result = sut.loadUserByUsername(account.getEmail().getValue());

        assertThat(result).isEqualTo(expectedUserDetails);
    }

    @Test
    void loadByUserName_whenUserNotFound_shouldRiseAnException() {
        when(accountRepository.getByEmail(any(AccountEmail.class))).thenReturn(Optional.empty());

        assertThatThrownBy(() -> sut.loadUserByUsername(ACCOUNT_EMAIL))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessage(ACCOUNT_EMAIL + " not found.");
    }
}