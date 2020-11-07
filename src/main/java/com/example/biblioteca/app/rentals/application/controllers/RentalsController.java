package com.example.biblioteca.app.rentals.application.controllers;

import com.example.biblioteca.modules.rentals.application.RentalsService;
import com.example.biblioteca.modules.rentals.domain.aggregates.Movie;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("v1")
@RequiredArgsConstructor
public class RentalsController {

    private final RentalsService rentalsService;

    @PostMapping("rent/{movieId}")
    public ResponseEntity<Object> rent(
            @CurrentSecurityContext(expression = "authentication.principal") UUID userId,
            @PathVariable UUID movieId
    ) {
        rentalsService.rent(movieId, userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("checkout/{movieId}")
    public ResponseEntity<Object> checkout(
            @CurrentSecurityContext(expression = "authentication.principal") UUID userId,
            @PathVariable UUID movieId
    ) {
        rentalsService.checkout(movieId, userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("rentals")
    public ResponseEntity<List<Movie>> rentals(
            @CurrentSecurityContext(expression = "authentication.principal") UUID userId
    ) {
        List<Movie> moviesRented = rentalsService.rentals(userId);
        return ResponseEntity.ok(moviesRented);
    }
}
