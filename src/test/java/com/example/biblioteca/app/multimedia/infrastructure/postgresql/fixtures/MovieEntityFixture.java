package com.example.biblioteca.app.multimedia.infrastructure.postgresql.fixtures;

import com.example.biblioteca.app.multimedia.infrastructure.postgresql.entities.MovieEntity;

import java.util.UUID;

public class MovieEntityFixture {
    public static MovieEntity randomMovieEntity() {
        return new MovieEntity(
                UUID.randomUUID().toString(),
                "A random name for a movie",
                2020
        );
    }
}
