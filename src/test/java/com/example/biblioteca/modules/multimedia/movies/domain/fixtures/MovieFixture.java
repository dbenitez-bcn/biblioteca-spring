package com.example.biblioteca.modules.multimedia.movies.domain.fixtures;

import com.example.biblioteca.modules.multimedia.movies.domain.aggregates.Movie;

import java.util.UUID;

public class MovieFixture {
    public static final String A_MOVIE_NAME = "Movie name";
    public static final int A_MOVIE_YEAR = 2020;
    public static final UUID MOVIE_ID = UUID.randomUUID();

    public static Movie randomMovie() {
        return new Movie("A random name for a movie", 2020);
    }

    public static Movie customMovie(UUID id, String name, int year) {
        return new Movie(id, name, year);
    }

    public static Movie defaultMovie() {
        return new Movie(MOVIE_ID, A_MOVIE_NAME, A_MOVIE_YEAR);
    }
}
