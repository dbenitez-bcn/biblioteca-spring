package com.example.biblioteca.modules.rentals.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserCreatedSubscriber {
    private final UserService userService;

    // TODO Implement create
    public void on() {
    }
}
