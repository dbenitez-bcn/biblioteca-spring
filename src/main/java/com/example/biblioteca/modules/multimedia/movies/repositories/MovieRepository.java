package com.example.biblioteca.modules.multimedia.movies.repositories;

import com.example.biblioteca.modules.multimedia.movies.domain.aggregates.Movie;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MovieRepository {
    void upsert(Movie movie);
    List<Movie> getAll();
    Optional<Movie> getOneById(UUID id);
    void delete(UUID id);
}
