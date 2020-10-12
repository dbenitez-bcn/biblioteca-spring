package com.example.biblioteca.app.rentals.application.controllers;

import com.example.biblioteca.app.rentals.application.request.RentRequest;
import com.example.biblioteca.modules.rentals.application.RentalsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RentalsController {

    private final RentalsService rentalsService;

    @PostMapping("/v1/rent")
    public ResponseEntity rent(@RequestBody RentRequest rentRequest) {
        rentalsService.rent(rentRequest.movieId, rentRequest.userId);
        return ResponseEntity.ok().build();
    }
}
