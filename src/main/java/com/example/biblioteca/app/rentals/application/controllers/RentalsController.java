package com.example.biblioteca.app.rentals.application.controllers;

import com.example.biblioteca.modules.rentals.application.RentalsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class RentalsController {

    private final RentalsService rentalsService;

    @PostMapping("/v1/rent/{movieId}")
    public ResponseEntity rent(
            @CurrentSecurityContext(expression = "authentication") Authentication authentication,
            @PathVariable UUID movieId
    ) {
        rentalsService.rent(movieId, (UUID) authentication.getPrincipal());
        return ResponseEntity.ok().build();
    }
}
