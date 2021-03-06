package com.example.biblioteca.app.accounts.application.controllers;

import com.example.biblioteca.app.accounts.application.request.LoginRequest;
import com.example.biblioteca.app.accounts.application.request.RegisterRequestVM;
import com.example.biblioteca.app.accounts.application.responses.LoginResponse;
import com.example.biblioteca.app.accounts.utils.JwtUtils;
import com.example.biblioteca.modules.accounts.application.AccountService;
import com.example.biblioteca.modules.accounts.domain.aggregates.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RequiredArgsConstructor
@RestController
public class AccountController {
    private final AccountService accountService;
    private final JwtUtils jwtUtils;

    @PostMapping("/v1/register")
    public void register(@RequestBody RegisterRequestVM requestVM) {
        accountService.register(requestVM.email, requestVM.password);
    }

    @GetMapping("/v1/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        Account account = accountService.login(request.email, request.password);
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("role", account.getRole().name());
        String token = jwtUtils.generateToken(account.getId().toString(), claims);

        return new LoginResponse(token);
    }
}
