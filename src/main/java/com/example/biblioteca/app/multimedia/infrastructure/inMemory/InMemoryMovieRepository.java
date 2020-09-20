package com.example.biblioteca.app.multimedia.infrastructure.inMemory;

import com.example.biblioteca.modules.multimedia.movies.domain.aggregates.Movie;
import com.example.biblioteca.modules.multimedia.movies.repositories.MovieRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

//@Repository("inMemory")
public class InMemoryMovieRepository implements MovieRepository {
    private final List<Movie> db = new ArrayList();

    @Override
    public void create(Movie movie) {
        db.add(movie);
    }

    @Override
    public List<Movie> getAll() {
        return db;
    }

    @Override
    public Optional<Movie> getOneById(UUID id) {
        return db
                .stream()
                .filter(movie -> movie.getId().equals(id))
                .findFirst();
    }

    @Override
    public void delete(UUID id) {
        Optional<Movie> movie = getOneById(id);
        movie.ifPresent(db::remove);
    }

    @Override
    public void update(UUID id, Movie movie) {
        Optional<Movie> originalMovie = getOneById(id);
        if (originalMovie.isPresent()) {
            Movie updatedMovie = new Movie(id, movie.getName().getValue(), movie.getYear().getValue());
            int index = db.indexOf(originalMovie.get());
            db.set(index, updatedMovie);
        }
    }
}
