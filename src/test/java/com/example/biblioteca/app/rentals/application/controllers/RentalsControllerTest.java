package com.example.biblioteca.app.rentals.application.controllers;

import com.example.biblioteca.modules.rentals.application.RentalsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.example.biblioteca.modules.rentals.domain.fixtures.RentalFixture.MOVIE_ID;
import static com.example.biblioteca.modules.rentals.domain.fixtures.RentalFixture.USER_ID;
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
        ResponseEntity<Object> result = sut.rent(USER_ID, MOVIE_ID);

        verify(rentalsService).rent(MOVIE_ID, USER_ID);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void checkout_shouldCheckoutTheMovie() {
        ResponseEntity<Object> result = sut.checkout(USER_ID, MOVIE_ID);

        verify(rentalsService).checkout(MOVIE_ID, USER_ID);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}