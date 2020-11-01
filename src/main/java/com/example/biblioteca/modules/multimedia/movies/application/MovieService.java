package com.example.biblioteca.modules.multimedia.movies.application;

import com.example.biblioteca.modules.multimedia.movies.domain.aggregates.Movie;
import com.example.biblioteca.modules.multimedia.movies.repositories.MovieRepository;
import com.example.biblioteca.modules.shared.events.EventBus;
import com.example.biblioteca.modules.shared.events.MovieCreatedEvent;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class MovieService {
    @Qualifier("postgres")
    private final MovieRepository repository;
    private final EventBus eventBus;

    public UUID createMovie(String movieName, int releaseYear) {
        Movie movie = new Movie(movieName, releaseYear);
        repository.upsert(movie);
        MovieCreatedEvent event = new MovieCreatedEvent(movie.getId(), movieName, releaseYear);
        eventBus.publish(event);
        return movie.getId();
    }

    public List<Movie> getAllMovies() {
        return repository.getAll();
    }

    public Optional<Movie> getMovieById(UUID id) {
        return repository.getOneById(id);
    }

    public void deleteMovie(UUID id) {
        repository
                .getOneById(id)
                .ifPresent(movie -> repository.delete(id));
    }

    public void updateMovie(UUID id, String name, int year) {
        Optional<Movie> maybeMovie = repository.getOneById(id);
        maybeMovie.ifPresent(movie -> {
            movie.update(name, year);
            repository.upsert(movie);
        });
    }
}
