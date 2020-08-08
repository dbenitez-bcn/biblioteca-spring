package com.example.biblioteca.modules.multimedia.movies.repositories;

import com.example.biblioteca.modules.multimedia.movies.domain.aggregates.Movie;

public interface MovieRepository {
    void create(Movie movie);
}
