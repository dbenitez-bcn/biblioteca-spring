package com.example.biblioteca.app.accounts.application.controllers;

import com.example.biblioteca.app.accounts.application.request.LoginRequest;
import com.example.biblioteca.app.accounts.application.request.RegisterRequestVM;
import com.example.biblioteca.app.accounts.application.responses.LoginResponse;
import com.example.biblioteca.modules.accounts.application.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static com.example.biblioteca.modules.accounts.domain.fixtures.AccountFixture.ACCOUNT_EMAIL;
import static com.example.biblioteca.modules.accounts.domain.fixtures.AccountFixture.ACCOUNT_PASSWORD;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class AccountControllerTest {
    @Mock
    private AccountService accountService;

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
        when(accountService.login(ACCOUNT_EMAIL, ACCOUNT_PASSWORD)).thenReturn(aToken);

        LoginResponse result = sut.login(new LoginRequest(ACCOUNT_EMAIL, ACCOUNT_PASSWORD));

        assertThat(result.token).isEqualTo(aToken);
    }
}