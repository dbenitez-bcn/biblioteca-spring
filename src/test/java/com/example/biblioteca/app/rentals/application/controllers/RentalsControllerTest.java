package com.example.biblioteca.app.rentals.application.controllers;

import com.example.biblioteca.modules.rentals.application.RentalsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

class RentalsControllerTest {
    @Mock
    private RentalsService rentalsService;

    @InjectMocks
    private RentalsController sut;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    void rent_shouldRentAMovie() {
        UUID movieId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userId, null, null);

        ResponseEntity result = sut.rent(auth, movieId);

        verify(rentalsService).rent(movieId, userId);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}