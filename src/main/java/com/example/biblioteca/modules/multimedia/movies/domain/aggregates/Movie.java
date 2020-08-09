package com.example.biblioteca.modules.multimedia.movies.domain.aggregates;

import com.example.biblioteca.modules.multimedia.movies.domain.valueObjects.MovieName;
import com.example.biblioteca.modules.multimedia.movies.domain.valueObjects.MovieYear;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;

@Getter
@EqualsAndHashCode
public class Movie {
    private UUID id;
    private MovieName name;
    private MovieYear year;

    public Movie(String id, String movieName, int year) {
        this.id = UUID.fromString(id);
        this.name = new MovieName(movieName);
        this.year = new MovieYear(year);
    }

    public Movie(String movieName, int year) {
        this.id = UUID.randomUUID();
        this.name = new MovieName(movieName);
        this.year = new MovieYear(year);
    }
}
