package com.example.biblioteca.modules.rentals.domain.aggregates;

import com.example.biblioteca.modules.rentals.domain.valueObjects.MovieId;
import com.example.biblioteca.modules.rentals.domain.valueObjects.MovieName;
import lombok.Getter;

import java.util.UUID;

@Getter
public class Movie {
    private MovieId id;
    private MovieName name;

    public Movie(UUID id, String name) {
        this.id = new MovieId(id);
        this.name = new MovieName(name);
    }
}
