package com.example.biblioteca.modules.rentals.application;

import com.example.biblioteca.modules.rentals.domain.aggregates.Rental;
import com.example.biblioteca.modules.rentals.domain.exceptions.MovieAlreadyRented;
import com.example.biblioteca.modules.rentals.repositories.RentalRepository;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RentalsService {

    private final RentalRepository repository;

    public void rent(UUID movieId, UUID userId) {
        Rental rental = new Rental(movieId, userId);
        Optional<Rental> rentalMaybe = repository.findByMovie(rental.getMovieId());
        if (rentalMaybe.isPresent()) {
            throw new MovieAlreadyRented();
        }
        repository.rent(rental);
    }
}
