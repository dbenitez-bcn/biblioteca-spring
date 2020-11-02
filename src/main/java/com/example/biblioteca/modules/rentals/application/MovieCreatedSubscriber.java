package com.example.biblioteca.modules.rentals.application;

import com.example.biblioteca.modules.shared.events.MovieCreatedEvent;
import com.example.biblioteca.modules.shared.events.Subscriber;
import org.springframework.stereotype.Component;

@Component
public class MovieCreatedSubscriber implements Subscriber<MovieCreatedEvent> {
    @Override
    public void on(MovieCreatedEvent event) {
        System.out.println("Id: " + event.id);
        System.out.println("Name: " + event.name);
        System.out.println("Year: " + event.year);
    }
}
