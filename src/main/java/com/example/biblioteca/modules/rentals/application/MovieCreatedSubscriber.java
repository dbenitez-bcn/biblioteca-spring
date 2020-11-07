package com.example.biblioteca.modules.rentals.application;

import com.example.biblioteca.modules.shared.events.MovieCreatedEvent;
import com.example.biblioteca.modules.shared.events.Subscriber;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MovieCreatedSubscriber implements Subscriber<MovieCreatedEvent> {
    private final MovieService movieService;

    @Override
    public void on(MovieCreatedEvent event) {
        movieService.create(event.id, event.name);
    }
}
