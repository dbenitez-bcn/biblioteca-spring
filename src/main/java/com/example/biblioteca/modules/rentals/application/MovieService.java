package com.example.biblioteca.modules.rentals.application;

import com.example.biblioteca.modules.rentals.domain.aggregates.Movie;
import com.example.biblioteca.modules.rentals.repositories.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component("rental.movieService")
@RequiredArgsConstructor
public class MovieService {
    @Qualifier("rental.movieRepository")
    private final MovieRepository movieRepository;

    public void create(UUID id, String name) {
        Movie movie = new Movie(id, name);
        movieRepository.create(movie);
    }
}
