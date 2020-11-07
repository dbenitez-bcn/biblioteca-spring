package com.example.biblioteca.app.rentals.application.controllers;

import com.example.biblioteca.app.rentals.application.responses.MovieResponse;
import com.example.biblioteca.modules.rentals.application.RentalsService;
import com.example.biblioteca.modules.rentals.domain.aggregates.Movie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static com.example.biblioteca.modules.rentals.domain.fixtures.RentalFixture.*;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
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

    @Test
    void rentals_shouldReturnTheListOfMoviesRented() {
        Movie movie = defaultMovie();
        MovieResponse movieResponse = new MovieResponse(movie.getId().getValue(), movie.getName().getValue());
        when(rentalsService.rentals(USER_ID)).thenReturn(singletonList(movie));

        ResponseEntity<List<MovieResponse>> result = sut.rentals(USER_ID);

        assertThat(result.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
        assertThat(result.getBody()).containsOnly(movieResponse);
    }
}