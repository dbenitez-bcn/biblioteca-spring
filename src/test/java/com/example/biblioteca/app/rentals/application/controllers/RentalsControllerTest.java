package com.example.biblioteca.app.rentals.application.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

import com.example.biblioteca.app.rentals.application.request.RentRequest;
import com.example.biblioteca.modules.rentals.application.RentalsService;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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
        RentRequest request = new RentRequest(movieId, userId);

        ResponseEntity result = sut.rent(request);

        verify(rentalsService).rent(movieId, userId);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}