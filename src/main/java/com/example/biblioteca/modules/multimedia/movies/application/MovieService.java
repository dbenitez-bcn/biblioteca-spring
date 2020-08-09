package com.example.biblioteca.modules.multimedia.movies.application;

import com.example.biblioteca.modules.multimedia.movies.domain.aggregates.Movie;
import com.example.biblioteca.modules.multimedia.movies.repositories.MovieRepository;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
public class MovieService {
    private final MovieRepository repository;

    public void createMovie(String movieName, int releaseYear) {
        Movie movie = new Movie(movieName, releaseYear);
        repository.create(movie);
    }

    public List<Movie> getAllMovies() {
        return repository.getAll();
    }

    public Optional<Movie> getMovieById(UUID id) {
        Movie movieMaybe = repository.getOneById(id);
        return Optional.ofNullable(movieMaybe);
    }

    public void deleteMovie(UUID id) {
        repository.delete(id);
    }

    public void updateMovie(UUID id, String name, int year) {
        Movie movie = new Movie(id, name, year);
        repository.update(movie.getId(), movie);
    }
}
