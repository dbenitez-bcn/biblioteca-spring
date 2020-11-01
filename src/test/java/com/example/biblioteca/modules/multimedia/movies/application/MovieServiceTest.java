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

import static com.example.biblioteca.modules.multimedia.movies.domain.fixtures.MovieFixture.*;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
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

        verify(movieRepository).upsert(movieCaptor.capture());
        Movie capturedMovie = movieCaptor.getValue();
        assertThat(expectedMovieName).isEqualTo(capturedMovie.getName());
        assertThat(expectedMovieYear).isEqualTo(capturedMovie.getYear());
        assertThat(capturedMovie.getId()).isNotNull();
    }

    @Test
    void createMovie_givenAnEmptyName_shouldThrowInvalidNameForMovie() {
        assertThatThrownBy(() -> sut.createMovie("", A_MOVIE_YEAR))
                .isInstanceOf(InvalidNameForMovie.class);
    }

    @Test
    void createMovie_givenANullName_shouldThrowInvalidNameForMovie() {
        assertThatThrownBy(() -> sut.createMovie(null, A_MOVIE_YEAR))
                .isInstanceOf(InvalidNameForMovie.class);
    }

    @Test
    void createMovie_givenABlankName_shouldThrowInvalidNameForMovie() {
        assertThatThrownBy(() -> sut.createMovie("     ", A_MOVIE_YEAR))
                .isInstanceOf(InvalidNameForMovie.class);
    }

    @Test
    void createMovie_givenAYearSmallerThat1888_shouldThrowInvalidYearForMovie() {
        assertThatThrownBy(() -> sut.createMovie(A_MOVIE_NAME, 1887))
                .isInstanceOf(InvalidYearForMovie.class);
    }

    @Test
    void createMovie_givenAYearGreaterThat1887_shouldCreateAMovie() {
        sut.createMovie(A_MOVIE_NAME, 1888);

        verify(movieRepository).upsert(any(Movie.class));
    }

    @Test
    void getAllMovies_shouldReturnAllMovies() {
        List<Movie> movies = asList(A_MOVIE, A_MOVIE, A_MOVIE);
        when(movieRepository.getAll()).thenReturn(movies);

        List<Movie> result = sut.getAllMovies();

        verify(movieRepository).getAll();
        assertThat(movies).isEqualTo(result);
    }

    @Test
    void getMovieById_whenFindsOneResult_shouldReturnAMovie() {
        when(movieRepository.getOneById(MOVIE_ID)).thenReturn(Optional.of(A_MOVIE));

        Optional<Movie> movieMaybe = sut.getMovieById(MOVIE_ID);

        verify(movieRepository).getOneById(MOVIE_ID);
        assertThat(A_MOVIE).isEqualTo(movieMaybe.get());
    }

    @Test
    void getMovieById_whenDoNotFindsOneResult_shouldReturnAnEmptyOptional() {
        when(movieRepository.getOneById(MOVIE_ID)).thenReturn(Optional.empty());

        Optional<Movie> movieMaybe = sut.getMovieById(MOVIE_ID);

        verify(movieRepository).getOneById(MOVIE_ID);
        assertThat(movieMaybe.isPresent()).isFalse();
    }

    @Test
    void deleteMovie_whenMovieIsFound_shouldDeleteTheMovie() {
        when(movieRepository.getOneById(MOVIE_ID)).thenReturn(Optional.of(A_MOVIE));

        sut.deleteMovie(MOVIE_ID);

        verify(movieRepository).delete(MOVIE_ID);
    }

    @Test
    void deleteMovie_whenMovieIsNotFound_shouldDoNothing() {
        when(movieRepository.getOneById(MOVIE_ID)).thenReturn(Optional.empty());

        sut.deleteMovie(MOVIE_ID);

        verify(movieRepository, never()).delete(MOVIE_ID);
    }

    @Test
    void updateMovie_whenMovieExist_shouldUpdateAMovie() {
        Movie movieToUpdate = customMovie(MOVIE_ID, "wrong name", 2000);
        Movie expectedMovieSaved = customMovie(MOVIE_ID, A_MOVIE_NAME, A_MOVIE_YEAR);
        when(movieRepository.getOneById(MOVIE_ID)).thenReturn(Optional.of(movieToUpdate));

        sut.updateMovie(MOVIE_ID, A_MOVIE_NAME, A_MOVIE_YEAR);

        verify(movieRepository).upsert(movieCaptor.capture());
        assertThat(expectedMovieSaved).isEqualTo(movieCaptor.getValue());
    }

    @Test
    void updateMovie_whenMovieDoesNotExist_shouldNotUpdateAMovie() {
        when(movieRepository.getOneById(MOVIE_ID)).thenReturn(Optional.empty());

        sut.updateMovie(MOVIE_ID, A_MOVIE_NAME, A_MOVIE_YEAR);

        verify(movieRepository, never()).upsert(any(Movie.class));
    }

    @Test
    void updateMovie_givenANullName_shouldThrowInvalidNameForMovie() {
        when(movieRepository.getOneById(MOVIE_ID)).thenReturn(Optional.of(A_MOVIE));

        assertThatThrownBy(() -> sut.updateMovie(MOVIE_ID, null, A_MOVIE_YEAR))
                .isInstanceOf(InvalidNameForMovie.class);
    }

    @Test
    void updateMovie_givenABlankName_shouldThrowInvalidNameForMovie() {
        when(movieRepository.getOneById(MOVIE_ID)).thenReturn(Optional.of(A_MOVIE));

        assertThatThrownBy(() -> sut.updateMovie(MOVIE_ID, "  ", A_MOVIE_YEAR))
                .isInstanceOf(InvalidNameForMovie.class);
    }

    @Test
    void updateMovie_givenAYearSmallerThat1888_shouldThrowInvalidYearForMovie() {
        when(movieRepository.getOneById(MOVIE_ID)).thenReturn(Optional.of(A_MOVIE));

        assertThatThrownBy(() -> sut.updateMovie(MOVIE_ID, A_MOVIE_NAME, 1887))
                .isInstanceOf(InvalidYearForMovie.class);
    }
}