package com.example.biblioteca.modules.rentals.repositories;

import com.example.biblioteca.modules.rentals.domain.aggregates.Movie;
import com.example.biblioteca.modules.rentals.domain.aggregates.Rental;
import com.example.biblioteca.modules.rentals.domain.valueObjects.MovieId;
import com.example.biblioteca.modules.rentals.domain.valueObjects.UserId;

import java.util.List;
import java.util.Optional;

public interface RentalRepository {

    void save(Rental rental);

    Optional<Rental> findByMovie(MovieId movieId);

    void removeByMovie(MovieId movieId);

    List<Movie> getMoviesRentedByUser(UserId userId);
}
