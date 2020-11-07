package com.example.biblioteca.app.rentals.infrastructure.fixtures;

import com.example.biblioteca.app.rentals.infrastructure.postgresql.entities.MovieEntity;
import com.example.biblioteca.app.rentals.infrastructure.postgresql.entities.RentalEntity;

import java.util.UUID;

public class RentalEntityFixture {

    public static final UUID USER_ID = UUID.randomUUID();
    public static final UUID MOVIE_ID = UUID.randomUUID();
    public static final MovieEntity MOVIE_ENTITY = new MovieEntity(MOVIE_ID, "MovieName");

    public static RentalEntity defaultRentalEntity() {
        return new RentalEntity(MOVIE_ID, USER_ID, MOVIE_ENTITY);
    }

    public static MovieEntity customMovieEntity(UUID id, String name) {
        return new MovieEntity(id, name);
    }
}
