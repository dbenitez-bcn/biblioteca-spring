package com.example.biblioteca.modules.rentals.domain.fixtures;

import com.example.biblioteca.modules.rentals.domain.aggregates.Movie;
import com.example.biblioteca.modules.rentals.domain.aggregates.Rental;

import java.util.UUID;

public class RentalFixture {

    public static final UUID USER_ID = UUID.randomUUID();
    public static final UUID MOVIE_ID = UUID.randomUUID();
    public static final String MOVIE_NAME = "A movie name";

    public static Rental defaultRental() {
        return new Rental(MOVIE_ID, USER_ID);
    }

    public static Movie defaultMovie() {
        return new Movie(MOVIE_ID, MOVIE_NAME);
    }

    public static Rental customRental(UUID movieId, UUID userId) {
        return new Rental(movieId, userId);
    }
}
