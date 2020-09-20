package com.example.biblioteca.app.multimedia.infrastructure.postgresql.implementations;

import com.example.biblioteca.app.multimedia.infrastructure.postgresql.entities.MovieEntity;
import com.example.biblioteca.modules.multimedia.movies.domain.aggregates.Movie;
import com.example.biblioteca.modules.multimedia.movies.repositories.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository("postgres")
@RequiredArgsConstructor
public class PostgresMovieRepository implements MovieRepository {
    private final MoviesRepositoryJPA repository;

    @Override
    public void create(Movie movie) {
        MovieEntity movieEntity = new MovieEntity(movie.getId().toString(), movie.getName().getValue(), movie.getYear().getValue());
        repository.save(movieEntity);
    }

    @Override
    public List<Movie> getAll() {
        List<Movie> movies = repository.findAll().stream().map(movieEntity -> new Movie(UUID.fromString(movieEntity.getId()), movieEntity.getName(), movieEntity.getYear())).collect(Collectors.toList());
        return movies;
    }

    @Override
    public Optional<Movie> getOneById(UUID id) {
        Movie movie = repository.findById(id.toString()).map(movieEntity -> new Movie(UUID.fromString(movieEntity.getId()), movieEntity.getName(), movieEntity.getYear())).orElse(null);
        return Optional.ofNullable(movie);
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id.toString());
    }

    @Override
    public void update(UUID id, Movie movie) {
        if (getOneById(id).isPresent()) {
            MovieEntity movieEntity = new MovieEntity(movie.getId().toString(), movie.getName().getValue(), movie.getYear().getValue());
            repository.save(movieEntity);
        }
    }
}
