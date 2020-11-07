package com.example.biblioteca.app.rentals.infrastructure.postgresql.implementations;

import com.example.biblioteca.app.rentals.infrastructure.postgresql.entities.MovieEntity;
import com.example.biblioteca.modules.rentals.domain.aggregates.Movie;
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

class PostgresMovieRepositoryTest {
    @Mock
    private MovieRepositoryJPA movieRepositoryJPA;

    @InjectMocks
    private PostgresMovieRepository sut;

    @Captor
    private ArgumentCaptor<MovieEntity> entityCaptor;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    void create_shouldCreateTheMovie() {
        UUID id = UUID.randomUUID();
        String movieName = "MovieName";
        Movie movie = new Movie(id, movieName);

        sut.create(movie);

        verify(movieRepositoryJPA).save(entityCaptor.capture());
        MovieEntity capturedEntity = entityCaptor.getValue();
        assertThat(capturedEntity.getId()).isEqualByComparingTo(id);
        assertThat(capturedEntity.getName()).isEqualTo(movieName);
    }
}