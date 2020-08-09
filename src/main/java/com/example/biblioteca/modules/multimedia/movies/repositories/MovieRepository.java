package com.example.biblioteca.modules.multimedia.movies.repositories;

import com.example.biblioteca.modules.multimedia.movies.domain.aggregates.Movie;

import java.util.List;

public interface MovieRepository {
    void create(Movie movie);
    List<Movie> getAll();
}
