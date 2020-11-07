package com.example.biblioteca.modules.rentals.application;

import com.example.biblioteca.modules.rentals.domain.aggregates.Movie;
import com.example.biblioteca.modules.rentals.domain.aggregates.Rental;
import com.example.biblioteca.modules.rentals.domain.exceptions.CheckoutNotAllowed;
import com.example.biblioteca.modules.rentals.domain.exceptions.MovieAlreadyRented;
import com.example.biblioteca.modules.rentals.domain.valueObjects.MovieId;
import com.example.biblioteca.modules.rentals.domain.valueObjects.UserId;
import com.example.biblioteca.modules.rentals.repositories.RentalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
        repository.save(rental);
    }

    public void checkout(UUID movieId, UUID userId) {
        Optional<Rental> rentalMaybe = repository.findByMovie(new MovieId(movieId));
        rentalMaybe.ifPresent(rental -> {
            if (!rental.isRentedBy(userId)) {
                throw new CheckoutNotAllowed();
            }
            repository.removeByMovie(rental.getMovieId());
        });
    }

    public List<Movie> rentals(UUID userId) {
        return repository.getMoviesRentedByUser(new UserId(userId));
    }
}
