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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

class MovieServiceTest {
    private static final String A_MOVIE_NAME = "Movie name";
    private static final int A_RELEASE_YEAR = 2020;

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
        final MovieYear expectedMovieYear = new MovieYear(A_RELEASE_YEAR);

        sut.createMovie(A_MOVIE_NAME, A_RELEASE_YEAR);

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
                () -> sut.createMovie("", A_RELEASE_YEAR)
        );
    }

    @Test
    void createMovie_givenANullName_shouldThrowInvalidNameForMovie() {
        assertThrows(
                InvalidNameForMovie.class,
                () -> sut.createMovie(null, A_RELEASE_YEAR)
        );
    }

    @Test
    void createMovie_givenABlankName_shouldThrowInvalidNameForMovie() {
        assertThrows(
                InvalidNameForMovie.class,
                () -> sut.createMovie("     ", A_RELEASE_YEAR)
        );
    }


    @Test
    void createMovie_givenAnInvalidYear_shouldThrowInvalidYearForMovie() {
        assertThrows(
                InvalidYearForMovie.class,
                () -> sut.createMovie(A_MOVIE_NAME, 1887)
        );
    }
}