package com.example.biblioteca.modules.multimedia.movies.domain.aggregates;

import com.example.biblioteca.modules.multimedia.movies.domain.valueObjects.MovieName;
import com.example.biblioteca.modules.multimedia.movies.domain.valueObjects.MovieYear;
import lombok.Getter;

import java.util.UUID;

@Getter
public class Movie {
    private UUID id;
    private MovieName name;
    private MovieYear year;

    public Movie(String movieName, int year) {
        this.id = UUID.randomUUID();
        this.name = new MovieName(movieName);
        this.year = new MovieYear(year);
    }
}
