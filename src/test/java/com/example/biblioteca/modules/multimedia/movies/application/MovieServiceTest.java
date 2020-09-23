package com.example.biblioteca.modules.multimedia.movies.application;

import com.example.biblioteca.modules.multimedia.movies.domain.aggregates.Movie;
import com.example.biblioteca.modules.multimedia.movies.domain.exceptions.InvalidNameForMovie;
import com.example.biblioteca.modules.multimedia.movies.domain.exceptions.InvalidYearForMovie;
import com.example.biblioteca.modules.multimedia.movies.domain.valueObjects.MovieName;
import com.example.biblioteca.modules.multimedia.movies.domain.valueObjects.MovieYear;
import com.example.biblioteca.modules.multimedia.movies.repositories.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.example.biblioteca.modules.multimedia.movies.domain.fixtures.MovieFixture.*;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class MovieServiceTest {
    private static final Movie A_MOVIE = defaultMovie();

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieService sut;

    @Captor
    private ArgumentCaptor<Movie> movieCaptor;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    void createMovie_shouldCreateAMovie() {
        final MovieName expectedMovieName = new MovieName(A_MOVIE_NAME);
        final MovieYear expectedMovieYear = new MovieYear(A_MOVIE_YEAR);

        sut.createMovie(A_MOVIE_NAME, A_MOVIE_YEAR);

        verify(movieRepository).create(movieCaptor.capture());
        Movie capturedMovie = movieCaptor.getValue();
        assertEquals(expectedMovieName, capturedMovie.getName());
        assertEquals(expectedMovieYear, capturedMovie.getYear());
        assertNotNull(capturedMovie.getId());
    }

    @Test
    void createMovie_givenAnEmptyName_shouldThrowInvalidNameForMovie() {
        assertThrows(
                InvalidNameForMovie.class,
                () -> sut.createMovie("", A_MOVIE_YEAR)
        );
    }

    @Test
    void createMovie_givenANullName_shouldThrowInvalidNameForMovie() {
        assertThrows(
                InvalidNameForMovie.class,
                () -> sut.createMovie(null, A_MOVIE_YEAR)
        );
    }

    @Test
    void createMovie_givenABlankName_shouldThrowInvalidNameForMovie() {
        assertThrows(
                InvalidNameForMovie.class,
                () -> sut.createMovie("     ", A_MOVIE_YEAR)
        );
    }

    @Test
    void createMovie_givenAYearSmallerThat1888_shouldThrowInvalidYearForMovie() {
        assertThrows(
                InvalidYearForMovie.class,
                () -> sut.createMovie(A_MOVIE_NAME, 1887)
        );
    }

    @Test
    void createMovie_givenAYearGreaterThat1887_shouldCreateAMovie() {
        sut.createMovie(A_MOVIE_NAME, 1888);

        verify(movieRepository).create(any(Movie.class));
    }

    @Test
    void getAllMovies_shouldReturnAllMovies() {
        List<Movie> movies = asList(A_MOVIE, A_MOVIE, A_MOVIE);
        when(movieRepository.getAll()).thenReturn(movies);

        List<Movie> result = sut.getAllMovies();

        verify(movieRepository).getAll();
        assertEquals(movies, result);
    }

    @Test
    void getMovieById_whenFindsOneResult_shouldReturnAMovie() {
        when(movieRepository.getOneById(MOVIE_ID)).thenReturn(Optional.of(A_MOVIE));

        Optional<Movie> movieMaybe = sut.getMovieById(MOVIE_ID);

        verify(movieRepository).getOneById(MOVIE_ID);
        assertEquals(A_MOVIE, movieMaybe.get());
    }

    @Test
    void getMovieById_whenDoNotFindsOneResult_shouldReturnAnEmptyOptional() {
        when(movieRepository.getOneById(MOVIE_ID)).thenReturn(Optional.empty());

        Optional<Movie> movieMaybe = sut.getMovieById(MOVIE_ID);

        verify(movieRepository).getOneById(MOVIE_ID);
        assertFalse(movieMaybe.isPresent());
    }

    @Test
    void deleteMovie_shouldDeleteTheMovie() {
        sut.deleteMovie(MOVIE_ID);

        verify(movieRepository).delete(MOVIE_ID);
    }

    @Test
    void updateMovie_whenMovieExist_shouldUpdateAMovie() {
        Movie movieToUpdate = customMovie(MOVIE_ID, "wrong name", 2000);
        Movie expectedMovieSaved = customMovie(MOVIE_ID, A_MOVIE_NAME, A_MOVIE_YEAR);
        when(movieRepository.getOneById(MOVIE_ID)).thenReturn(Optional.of(movieToUpdate));

        sut.updateMovie(MOVIE_ID, A_MOVIE_NAME, A_MOVIE_YEAR);

        verify(movieRepository).update(eq(MOVIE_ID), movieCaptor.capture());
        assertEquals(expectedMovieSaved, movieCaptor.getValue());
    }

    @Test
    void updateMovie_whenMovieDoesNotExist_shouldNotUpdateAMovie() {
        when(movieRepository.getOneById(MOVIE_ID)).thenReturn(Optional.empty());

        sut.updateMovie(MOVIE_ID, A_MOVIE_NAME, A_MOVIE_YEAR);

        verify(movieRepository, never()).update(any(UUID.class), any(Movie.class));
    }

    @Test
    void updateMovie_givenANullName_shouldThrowInvalidNameForMovie() {
        when(movieRepository.getOneById(MOVIE_ID)).thenReturn(Optional.of(A_MOVIE));

        assertThrows(
                InvalidNameForMovie.class,
                () -> sut.updateMovie(MOVIE_ID, null, A_MOVIE_YEAR)
        );
    }

    @Test
    void updateMovie_givenABlankName_shouldThrowInvalidNameForMovie() {
        when(movieRepository.getOneById(MOVIE_ID)).thenReturn(Optional.of(A_MOVIE));

        assertThrows(
                InvalidNameForMovie.class,
                () -> sut.updateMovie(MOVIE_ID, "  ", A_MOVIE_YEAR)
        );
    }

    @Test
    void updateMovie_givenAYearSmallerThat1888_shouldThrowInvalidYearForMovie() {
        when(movieRepository.getOneById(MOVIE_ID)).thenReturn(Optional.of(A_MOVIE));

        assertThrows(
                InvalidYearForMovie.class,
                () -> sut.updateMovie(MOVIE_ID, A_MOVIE_NAME, 1887)
        );
    }
}