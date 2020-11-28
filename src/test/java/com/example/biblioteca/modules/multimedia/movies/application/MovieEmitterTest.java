package com.example.biblioteca.modules.multimedia.movies.application;

import com.example.biblioteca.modules.multimedia.movies.domain.aggregates.Movie;
import com.example.biblioteca.modules.shared.events.EventBus;
import com.example.biblioteca.modules.shared.events.MovieCreatedEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static com.example.biblioteca.modules.multimedia.movies.domain.fixtures.MovieFixture.defaultMovie;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

class MovieEmitterTest {

    @Mock
    private EventBus eventBus;

    @InjectMocks
    private MovieEmitter sut;

    @Captor
    private ArgumentCaptor<MovieCreatedEvent> eventCaptor;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    void emitMovieCreated_shouldEmitMovieCreatedEvent() {
        Movie movie = defaultMovie();

        sut.emitMovieCreated(movie);

        verify(eventBus).publish(eventCaptor.capture());
        MovieCreatedEvent eventEmitted = eventCaptor.getValue();
        assertThat(eventEmitted.id).isEqualTo(movie.getId());
        assertThat(eventEmitted.name).isEqualTo(movie.getName().getValue());
        assertThat(eventEmitted.year).isEqualTo(movie.getYear().getValue());
    }
}