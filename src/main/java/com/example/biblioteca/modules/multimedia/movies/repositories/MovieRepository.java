package com.example.biblioteca.modules.multimedia.movies.repositories;

import com.example.biblioteca.modules.multimedia.movies.domain.aggregates.Movie;

import java.util.List;
import java.util.UUID;

public interface MovieRepository {
    void create(Movie movie);
    List<Movie> getAll();
    Movie getOneById(UUID id);
    void delete(UUID id);
    void update(UUID id, Movie movie);
}
