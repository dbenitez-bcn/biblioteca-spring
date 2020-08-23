package com.example.biblioteca.app.multimedia.infrastructure.inMemory;

import com.example.biblioteca.modules.multimedia.movies.domain.aggregates.Movie;
import com.example.biblioteca.modules.multimedia.movies.repositories.MovieRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository("inMemory")
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
    public Movie getOneById(UUID id) {
        return db
                .stream()
                .filter(movie -> movie.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void delete(UUID id) {
        Movie movie = getOneById(id);
        if (movie != null) db.remove(movie);
    }

    @Override
    public void update(UUID id, Movie movie) {
        Movie originalMovie = getOneById(id);
        if (originalMovie != null) {
            Movie updatedMovie = new Movie(id, movie.getName().getValue(), movie.getYear().getValue());
            int index = db.indexOf(originalMovie);
            db.set(index, updatedMovie);
        }
    }
}
