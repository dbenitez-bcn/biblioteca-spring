package com.example.biblioteca.modules.multimedia.movies.domain.fixtures;

import com.example.biblioteca.modules.multimedia.movies.domain.aggregates.Movie;

public class MovieFixture {
    public static Movie randomMovie() {
        return new Movie("A random name for a movie", 2020);
    }
}
