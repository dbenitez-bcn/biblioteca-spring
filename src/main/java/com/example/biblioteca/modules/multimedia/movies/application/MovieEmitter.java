package com.example.biblioteca.modules.multimedia.movies.application;

import com.example.biblioteca.modules.multimedia.movies.domain.aggregates.Movie;
import com.example.biblioteca.modules.shared.events.EventBus;
import com.example.biblioteca.modules.shared.events.MovieCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class MovieEmitter {
    private final EventBus eventBus;

    public void emitMovieCreated(Movie movie) {
        MovieCreatedEvent event = new MovieCreatedEvent(
                movie.getId(),
                movie.getName().getValue(),
                movie.getYear().getValue()
        );

        eventBus.publish(event);
    }
}
