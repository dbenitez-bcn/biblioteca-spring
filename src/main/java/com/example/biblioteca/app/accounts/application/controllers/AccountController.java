package com.example.biblioteca.app.accounts.application.controllers;

import com.example.biblioteca.app.accounts.application.request.LoginRequest;
import com.example.biblioteca.app.accounts.application.request.RegisterRequestVM;
import com.example.biblioteca.app.accounts.application.responses.LoginResponse;
import com.example.biblioteca.modules.accounts.application.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AccountController {
    private final AccountService accountService;

    @PostMapping("/v1/register")
    public void register(@RequestBody RegisterRequestVM requestVM) {
        accountService.register(requestVM.email, requestVM.password);
    }

    @GetMapping("/v1/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        String token = accountService.login(request.email, request.password);
        return new LoginResponse(token);
    }
}
