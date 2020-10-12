package com.example.biblioteca.app.rentals.infrastructure.postgresql.implementations;

import com.example.biblioteca.app.rentals.infrastructure.postgresql.entities.RentalEntity;
import com.example.biblioteca.modules.rentals.domain.aggregates.Rental;
import com.example.biblioteca.modules.rentals.domain.valueObjects.MovieId;
import com.example.biblioteca.modules.rentals.repositories.RentalRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PostgresRentalRepository implements RentalRepository {

    private final RentalRepositoryJPA repositoryJPA;

    @Override
    public void rent(Rental rental) {
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
}
