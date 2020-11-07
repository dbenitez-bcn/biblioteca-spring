package com.example.biblioteca.modules.rentals.application;

import com.example.biblioteca.modules.shared.events.MovieCreatedEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

class MovieCreatedSubscriberTest {

    @Mock
    private MovieService movieService;

    @InjectMocks 
    private MovieCreatedSubscriber sut;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    void on_shouldCreateTheMovieWithTheGivenValues() {
        MovieCreatedEvent movieEvent = new MovieCreatedEvent(UUID.randomUUID(), "Movie name", 2020);

        sut.on(movieEvent);

        verify(movieService).create(movieEvent.id, movieEvent.name);
    }
}