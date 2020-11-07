package com.example.biblioteca.modules.rentals.application;

import com.example.biblioteca.modules.rentals.domain.aggregates.Movie;
import com.example.biblioteca.modules.rentals.domain.valueObjects.MovieId;
import com.example.biblioteca.modules.rentals.domain.valueObjects.MovieName;
import com.example.biblioteca.modules.rentals.repositories.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

class MovieServiceTest {

    @Mock
    private MovieRepository repository;

    @InjectMocks
    private MovieService sut;

    @Captor
    private ArgumentCaptor<Movie> movieCaptor;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    void create_shouldCreateAMovie() {
        UUID id = UUID.randomUUID();
        String movieName = "MovieName";

        sut.create(id, movieName);

        verify(repository).create(movieCaptor.capture());
        Movie capturedMovie = movieCaptor.getValue();
        assertThat(capturedMovie.getId()).isEqualTo(new MovieId(id));
        assertThat(capturedMovie.getName()).isEqualTo(new MovieName(movieName));
    }
}