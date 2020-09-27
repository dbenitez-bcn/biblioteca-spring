package com.example.biblioteca.app.accounts.application.controllers;

import com.example.biblioteca.app.accounts.application.request.RegisterRequestVM;
import com.example.biblioteca.modules.accounts.application.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static com.example.biblioteca.modules.accounts.domain.fixtures.AccountFixture.ACCOUNT_EMAIL;
import static com.example.biblioteca.modules.accounts.domain.fixtures.AccountFixture.ACCOUNT_PASSWORD;
import static org.mockito.Mockito.verify;
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
}