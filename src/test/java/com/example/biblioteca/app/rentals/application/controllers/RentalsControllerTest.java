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
    public static final UUID MOVIE_ID = UUID.randomUUID();
    public static final UUID USER_ID = UUID.randomUUID();
    public static final UsernamePasswordAuthenticationToken AUTH = new UsernamePasswordAuthenticationToken(USER_ID, null, null);

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    void rent_shouldRentAMovie() {
        ResponseEntity<Object> result = sut.rent(AUTH, MOVIE_ID);

        verify(rentalsService).rent(MOVIE_ID, USER_ID);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void checkout_shouldCheckoutTheMovie() {
        ResponseEntity<Object> result = sut.checkout(AUTH, MOVIE_ID);

        verify(rentalsService).checkout(MOVIE_ID, USER_ID);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}