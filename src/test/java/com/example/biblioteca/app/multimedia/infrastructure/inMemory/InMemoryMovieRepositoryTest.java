package com.example.biblioteca.app.multimedia.infrastructure.inMemory;

import com.example.biblioteca.modules.multimedia.movies.domain.aggregates.Movie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static com.example.biblioteca.modules.multimedia.movies.domain.fixtures.MovieFixture.randomMovie;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class InMemoryMovieRepositoryTest {
    private InMemoryMovieRepository sut;

    @BeforeEach
    void beforeEach() {
        sut = new InMemoryMovieRepository();
    }

    @Test
    void create_shouldStoreTheMovie() {
        sut.create(randomMovie());

        assertEquals(1, sut.getAll().size());
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

        Movie result = sut.getOneById(randomMovie.getId());

        assertEquals(randomMovie, result);
    }

    @Test
    void getOneById_whenMovieDoesNotExist_shouldReturnNull() {
        Movie result = sut.getOneById(UUID.randomUUID());

        assertNull(result);
    }

    @Test
    void delete_shouldRemoveTheMovieForTheGivenId() {
        Movie movie = willPersistAMovie();

        sut.delete(movie.getId());

        assertNull(sut.getOneById(movie.getId()));
    }

    @Test
    void delete_whenMovieDoesNotExist_shouldDoAnything() {
        willPersistAMovie();

        sut.delete(UUID.randomUUID());

        assertEquals(1, sut.getAll().size());
    }

    @Test
    void update_whenTheMovieExist_shouldUpdateIt() {
        Movie originalMovie = willPersistAMovie();
        Movie updatedMovie = new Movie(originalMovie.getId(), "New Name", 1998);

        sut.update(originalMovie.getId(), updatedMovie);

        assertEquals(updatedMovie, sut.getOneById(updatedMovie.getId()));
    }

    @Test
    void update_whenTheMovieDoesNotExist_shouldNotDoAnything() {
        Movie originalMovie = willPersistAMovie();
        Movie updatedMovie = new Movie(originalMovie.getId(), "New Name", 1998);

        sut.update(UUID.randomUUID(), updatedMovie);

        assertEquals(originalMovie, sut.getOneById(updatedMovie.getId()));
    }

    private Movie willPersistAMovie() {
        Movie movie = randomMovie();
        sut.create(movie);
        return movie;
    }
}