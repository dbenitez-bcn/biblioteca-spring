package com.example.biblioteca.modules.multimedia.movies.domain.valueObjects;

import com.example.biblioteca.modules.multimedia.movies.domain.exceptions.InvalidYearForMovie;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class MovieYear {
    private int value;

    public MovieYear(int value) {
        if (value < 1888) {
            throw new InvalidYearForMovie();
        }
        this.value = value;
    }
}
