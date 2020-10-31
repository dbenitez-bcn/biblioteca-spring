package com.example.biblioteca.app.accounts.application.controllers;

import com.example.biblioteca.app.accounts.application.request.LoginRequest;
import com.example.biblioteca.app.accounts.application.request.RegisterRequestVM;
import com.example.biblioteca.app.accounts.application.responses.LoginResponse;
import com.example.biblioteca.app.accounts.utils.JwtUtils;
import com.example.biblioteca.modules.accounts.application.AccountService;
import com.example.biblioteca.modules.accounts.domain.aggregates.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.HashMap;

import static com.example.biblioteca.modules.accounts.domain.fixtures.AccountFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class AccountControllerTest {
    @Mock
    private AccountService accountService;
    @Mock
    private JwtUtils jwtUtils;

    @InjectMocks
    private AccountController sut;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    void register_shouldRegisterAnAccount() {
        RegisterRequestVM requestVM = new RegisterRequestVM(ACCOUNT_EMAIL, ACCOUNT_PASSWORD);

        sut.register(requestVM);

        verify(accountService).register(ACCOUNT_EMAIL, ACCOUNT_PASSWORD);
    }

    @Test
    void login_shouldLoginAnAccount() {
        String aToken = "A_TOKEN";
        Account account = defaultAccount();
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("role", account.getRole().name());
        when(jwtUtils.generateToken(account.getId().toString(), claims)).thenReturn(aToken);
        when(accountService.login(ACCOUNT_EMAIL, ACCOUNT_PASSWORD)).thenReturn(account);

        LoginResponse result = sut.login(new LoginRequest(ACCOUNT_EMAIL, ACCOUNT_PASSWORD));

        assertThat(result.token).isEqualTo(aToken);
    }
}