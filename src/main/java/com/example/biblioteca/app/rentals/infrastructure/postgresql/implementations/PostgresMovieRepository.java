package com.example.biblioteca.app.rentals.infrastructure.postgresql.implementations;

import com.example.biblioteca.app.rentals.infrastructure.postgresql.entities.MovieEntity;
import com.example.biblioteca.modules.rentals.domain.aggregates.Movie;
import com.example.biblioteca.modules.rentals.repositories.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component("rental.movieRepository")
@RequiredArgsConstructor
public class PostgresMovieRepository implements MovieRepository {
    private final MovieRepositoryJPA repositoryJPA;

    @Override
    public void create(Movie movie) {
        MovieEntity movieEntity = new MovieEntity(movie.getId().getValue(), movie.getName().getValue());
        repositoryJPA.save(movieEntity);
    }
}
