package com.example.biblioteca.app.accounts.application.request;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LoginRequest {
    public final String email;
    public final String password;
}
