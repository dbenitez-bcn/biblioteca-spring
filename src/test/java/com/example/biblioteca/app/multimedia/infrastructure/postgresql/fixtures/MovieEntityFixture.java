package com.example.biblioteca.app.multimedia.infrastructure.postgresql.fixtures;

import com.example.biblioteca.app.multimedia.infrastructure.postgresql.entities.MovieEntity;

import java.util.UUID;

public class MovieEntityFixture {
    public static final String A_MOVIE_ENTITY_NAME = "A random name for a movie";
    public static final int A_MOVIE_ENTITY_YEAR = 2020;
    public static final String MOVIE_ENTITY_ID = UUID.randomUUID().toString();

    public static MovieEntity randomMovieEntity() {
        return new MovieEntity(
                MOVIE_ENTITY_ID,
                A_MOVIE_ENTITY_NAME,
                A_MOVIE_ENTITY_YEAR
        );
    }

    public static MovieEntity customMovieEntity(String id, String name, int year) {
        return new MovieEntity(id, name, year);
    }
}
