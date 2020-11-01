package com.example.biblioteca.modules.rentals.repositories;

import com.example.biblioteca.modules.rentals.domain.aggregates.Rental;
import com.example.biblioteca.modules.rentals.domain.valueObjects.MovieId;

import java.util.Optional;

public interface RentalRepository {

    void save(Rental rental);

    Optional<Rental> findByMovie(MovieId movieId);

    void removeByMovie(MovieId movieId);
}
