package com.example.biblioteca.modules.rentals.repositories;

import com.example.biblioteca.modules.rentals.domain.aggregates.Movie;

public interface MovieRepository {
    void create(Movie movie);
}
