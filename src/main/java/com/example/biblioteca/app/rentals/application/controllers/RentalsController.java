package com.example.biblioteca.app.rentals.application.controllers;

import com.example.biblioteca.modules.rentals.application.RentalsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("v1")
@RequiredArgsConstructor
public class RentalsController {

    private final RentalsService rentalsService;

    @PostMapping("rent/{movieId}")
    public ResponseEntity<Object> rent(
            @CurrentSecurityContext(expression = "authentication") Authentication authentication,
            @PathVariable UUID movieId
    ) {
        rentalsService.rent(movieId, (UUID) authentication.getPrincipal());
        return ResponseEntity.ok().build();
    }

    @PostMapping("checkout/{movieId}")
    public ResponseEntity<Object> checkout(
            @CurrentSecurityContext(expression = "authentication") Authentication authentication,
            @PathVariable UUID movieId
    ) {
        rentalsService.checkout(movieId, (UUID) authentication.getPrincipal());
        return ResponseEntity.ok().build();
    }
}
