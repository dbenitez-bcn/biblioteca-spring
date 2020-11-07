package com.example.biblioteca.app.rentals.infrastructure.postgresql.implementations;

import com.example.biblioteca.app.rentals.infrastructure.postgresql.entities.MovieEntity;
import com.example.biblioteca.app.rentals.infrastructure.postgresql.entities.RentalEntity;
import com.example.biblioteca.modules.rentals.domain.aggregates.Rental;
import com.example.biblioteca.modules.rentals.domain.exceptions.MovieNotFound;
import com.example.biblioteca.modules.rentals.domain.valueObjects.MovieId;
import com.example.biblioteca.modules.rentals.repositories.RentalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PostgresRentalRepository implements RentalRepository {

    private final RentalRepositoryJPA rentalRepositoryJPA;
    private final MovieRepositoryJPA movieRepositoryJPA;

    @Override
    public void save(Rental rental) {
        Optional<MovieEntity> movie = movieRepositoryJPA.findById(rental.getMovieId().getValue());
        if (!movie.isPresent()) {
            throw new MovieNotFound();
        }
        RentalEntity rentalToSave = new RentalEntity(
            rental.getMovieId().getValue(),
            rental.getUserId().getValue(),
                movie.get()
        );
        rentalRepositoryJPA.save(rentalToSave);
    }

    @Override
    public Optional<Rental> findByMovie(MovieId movieId) {
        Optional<RentalEntity> rentalMaybe = rentalRepositoryJPA.findById(movieId.getValue());
        return rentalMaybe.map(
            rentalEntity -> new Rental(rentalEntity.getMovieId(), rentalEntity.getUserId())
        );
    }

    @Override
    public void removeByMovie(MovieId movieId) {
        rentalRepositoryJPA.deleteById(movieId.getValue());
    }
}
