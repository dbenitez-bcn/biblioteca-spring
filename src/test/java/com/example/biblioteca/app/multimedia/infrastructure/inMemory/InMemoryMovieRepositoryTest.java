package com.example.biblioteca.app.multimedia.infrastructure.inMemory;

import com.example.biblioteca.modules.multimedia.movies.domain.aggregates.Movie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.example.biblioteca.modules.multimedia.movies.domain.fixtures.MovieFixture.customMovie;
import static com.example.biblioteca.modules.multimedia.movies.domain.fixtures.MovieFixture.randomMovie;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class InMemoryMovieRepositoryTest {
    private InMemoryMovieRepository sut;

    @BeforeEach
    void beforeEach() {
        sut = new InMemoryMovieRepository();
    }

    @Test
    void upsert_whenTheMovieDoesNotExist_shouldStoreTheMovie() {
        sut.upsert(randomMovie());

        assertEquals(1, sut.getAll().size());
    }

    @Test
    void upsert_whenTheMovieExist_shouldUpdateIt() {
        Movie originalMovie = willPersistAMovie();
        Movie updatedMovie = customMovie(originalMovie.getId(), "New Name", 1998);

        sut.upsert(updatedMovie);

        assertEquals(updatedMovie, sut.getOneById(updatedMovie.getId()).get());
    }

    @Test
    void getAll_shoutReturnAllMovies() {
        Movie firstMovie = willPersistAMovie();
        Movie secondMovie = willPersistAMovie();
        Movie thirdMovie = willPersistAMovie();

        List<Movie> result = sut.getAll();

        assertEquals(3, result.size());
        assertEquals(firstMovie, result.get(0));
        assertEquals(secondMovie, result.get(1));
        assertEquals(thirdMovie, result.get(2));
    }

    @Test
    void getOneById_whenMovieExist_shouldReturnTheMovieForTheGivenId() {
        Movie randomMovie = willPersistAMovie();

        Movie result = sut.getOneById(randomMovie.getId()).get();

        assertEquals(randomMovie, result);
    }

    @Test
    void getOneById_whenMovieDoesNotExist_shouldReturnNull() {
        Optional<Movie> result = sut.getOneById(UUID.randomUUID());

        assertFalse(result.isPresent());
    }

    @Test
    void delete_shouldRemoveTheMovieForTheGivenId() {
        Movie movie = willPersistAMovie();

        sut.delete(movie.getId());

        assertFalse(sut.getOneById(movie.getId()).isPresent());
    }

    @Test
    void delete_whenMovieDoesNotExist_shouldDoAnything() {
        willPersistAMovie();

        sut.delete(UUID.randomUUID());

        assertEquals(1, sut.getAll().size());
    }

    private Movie willPersistAMovie() {
        Movie movie = randomMovie();
        sut.upsert(movie);
        return movie;
    }
}