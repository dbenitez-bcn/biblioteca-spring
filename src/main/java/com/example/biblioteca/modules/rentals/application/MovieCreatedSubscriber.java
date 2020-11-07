package com.example.biblioteca.modules.rentals.application;

import com.example.biblioteca.modules.shared.events.MovieCreatedEvent;
import com.example.biblioteca.modules.shared.events.Subscriber;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MovieCreatedSubscriber implements Subscriber<MovieCreatedEvent> {
    @Qualifier("rental.movieService")
    private final MovieService movieService;

    @Override
    public void on(MovieCreatedEvent event) {
        movieService.create(event.id, event.name);
    }
}
