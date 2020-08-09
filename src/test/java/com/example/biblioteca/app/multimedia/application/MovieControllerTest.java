package com.example.biblioteca.app.multimedia.application;

import com.example.biblioteca.app.multimedia.requests.MovieRequestVM;
import com.example.biblioteca.app.multimedia.responses.MovieResponseVM;
import com.example.biblioteca.modules.multimedia.movies.application.MovieService;
import com.example.biblioteca.modules.multimedia.movies.domain.aggregates.Movie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class MovieControllerTest {
    private static final String A_MOVIE_NAME = "Movie name";
    private static final int A_RELEASE_YEAR = 2020;
    private static final Movie A_MOVIE = new Movie(A_MOVIE_NAME, A_RELEASE_YEAR);
    private static final UUID MOVIE_ID = UUID.randomUUID();
    private static final MovieResponseVM A_MOVIE_RESPONSE = new MovieResponseVM(
            A_MOVIE.getId().toString(),
            A_MOVIE.getName().getValue(),
            A_MOVIE.getYear().getValue()
    );

    @Mock
    private MovieService movieService;

    @InjectMocks
    private MovieController sut;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    void createMovie_shouldCreateAMovie() {
        MovieRequestVM requestVM = new MovieRequestVM(A_MOVIE_NAME, A_RELEASE_YEAR);

        ResponseEntity result = sut.createMovie(requestVM);

        verify(movieService).createMovie(A_MOVIE_NAME, A_RELEASE_YEAR);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
    }

    @Test
    void getAllMovies_whenMoviesAreFound_shouldReturnAllMovies() {
        List<MovieResponseVM> expectedMovies = asList(A_MOVIE_RESPONSE, A_MOVIE_RESPONSE);
        when(movieService.getAllMovies()).thenReturn(asList(A_MOVIE, A_MOVIE));

        ResponseEntity<List<MovieResponseVM> >result = sut.getAllMovies();

        verify(movieService).getAllMovies();
        assertEquals(expectedMovies, result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void getAllMovies_whenNoMoviesAreFound_shouldReturnNotFoundResponse() {
        when(movieService.getAllMovies()).thenReturn(emptyList());

        ResponseEntity<List<MovieResponseVM> >result = sut.getAllMovies();

        verify(movieService).getAllMovies();
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    void getMovieById_whenAMovieIsFound_shouldReturnTheMovie() {
        when(movieService.getMovieById(MOVIE_ID)).thenReturn(Optional.of(A_MOVIE));

        ResponseEntity<MovieResponseVM> result = sut.getMovieById(MOVIE_ID);

        verify(movieService).getMovieById(MOVIE_ID);
        assertEquals(A_MOVIE_RESPONSE, result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void getMovieById_whenNoMovieIsFound_shouldReturnNotFoundResponse() {
        when(movieService.getMovieById(MOVIE_ID)).thenReturn(Optional.empty());

        ResponseEntity<MovieResponseVM> result = sut.getMovieById(MOVIE_ID);

        verify(movieService).getMovieById(MOVIE_ID);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    void deleteMovie_shouldDeleteTheMovie() {
        ResponseEntity result = sut.deleteMovie(MOVIE_ID);

        verify(movieService).deleteMovie(MOVIE_ID);
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
    }
}