package com.example.biblioteca.app.multimedia.infrastructure.postgresql.implementations;

import com.example.biblioteca.app.multimedia.infrastructure.postgresql.entities.MovieEntity;
import com.example.biblioteca.modules.multimedia.movies.domain.aggregates.Movie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.example.biblioteca.app.multimedia.infrastructure.postgresql.fixtures.MovieEntityFixture.randomMovieEntity;
import static com.example.biblioteca.modules.multimedia.movies.domain.fixtures.MovieFixture.randomMovie;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class PostgresMovieRepositoryTest {

    @Mock
    private MoviesRepositoryJPA moviesRepositoryJPA;

    @InjectMocks
    private PostgresMovieRepository sut;

    @Captor
    private ArgumentCaptor<MovieEntity> movieCaptor;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    void create_whenAMovieIsSend_shouldCreateAMovie() {
        Movie movieToSave = randomMovie();

        sut.create(movieToSave);

        verify(moviesRepositoryJPA).save(movieCaptor.capture());
        MovieEntity capturedMovie = movieCaptor.getValue();
        assertThat(capturedMovie.getName()).isEqualTo(movieToSave.getName().getValue());
        assertThat(capturedMovie.getYear()).isEqualTo(movieToSave.getYear().getValue());
        assertThat(capturedMovie.getId()).isEqualTo(movieToSave.getId().toString());
    }

    @Test
    void getAll_whenMoviesAreFound_shouldReturnAListOfMovies() {
        List<MovieEntity> moviesList = willFindMovies();

        List<Movie> result = sut.getAll();

        assertThat(result.size()).isEqualTo(moviesList.size());
    }

    @Test
    void getOneById_whenAMovieIsFound_shouldReturnAMovie() {
        Movie aMovie = willFindAMovie();

        Movie result = sut.getOneById(aMovie.getId());

        assertThat(result).isEqualTo(aMovie);
    }

    @Test
    void getOneById_whenNoMovieIsFound_shouldReturnNull() {
        willFindNoMovie();

        Movie result = sut.getOneById(UUID.randomUUID());

        assertThat(result).isNull();
    }

    @Test
    void delete_shouldDeleteAMovie() {
        UUID idToDelete = UUID.randomUUID();

        sut.delete(idToDelete);

        verify(moviesRepositoryJPA).deleteById(idToDelete.toString());
    }

    private List<MovieEntity> willFindMovies() {
        List<MovieEntity> moviesList = asList(randomMovieEntity(), randomMovieEntity(), randomMovieEntity());

        when(moviesRepositoryJPA.findAll()).thenReturn(moviesList);

        return moviesList;
    }

    private Movie willFindAMovie() {
        Movie movie = randomMovie();
        MovieEntity movieEntity = new MovieEntity(
                movie.getId().toString(),
                movie.getName().getValue(),
                movie.getYear().getValue()
        );

        when(moviesRepositoryJPA.findById(movie.getId().toString())).thenReturn(Optional.of(movieEntity));

        return movie;
    }

    private void willFindNoMovie() {
        when(moviesRepositoryJPA.findById(anyString())).thenReturn(Optional.empty());
    }
}