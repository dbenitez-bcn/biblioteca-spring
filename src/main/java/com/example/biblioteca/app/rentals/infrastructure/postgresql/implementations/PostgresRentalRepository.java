package com.example.biblioteca.app.rentals.infrastructure.postgresql.implementations;

import com.example.biblioteca.app.rentals.infrastructure.postgresql.entities.RentalEntity;
import com.example.biblioteca.modules.rentals.domain.aggregates.Rental;
import com.example.biblioteca.modules.rentals.domain.valueObjects.MovieId;
import com.example.biblioteca.modules.rentals.repositories.RentalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PostgresRentalRepository implements RentalRepository {

    private final RentalRepositoryJPA repositoryJPA;

    @Override
    public void save(Rental rental) {
        RentalEntity rentalToSave = new RentalEntity(
            rental.getMovieId()
                  .getValue(),
            rental.getUserId()
                  .getValue()
        );
        repositoryJPA.save(rentalToSave);
    }

    @Override
    public Optional<Rental> findByMovie(MovieId movieId) {
        Optional<RentalEntity> rentalMaybe = repositoryJPA.findById(movieId.getValue());
        return rentalMaybe.map(
            rentalEntity -> new Rental(rentalEntity.getMovieId(), rentalEntity.getUserId())
        );
    }

    @Override
    public void removeByMovie(MovieId movieId) {
        repositoryJPA.deleteById(movieId.getValue());
    }
}
