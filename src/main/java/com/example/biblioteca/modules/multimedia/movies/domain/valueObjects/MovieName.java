package com.example.biblioteca.modules.multimedia.movies.domain.valueObjects;

import com.example.biblioteca.modules.multimedia.movies.domain.exceptions.InvalidNameForMovie;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class MovieName {
    private final String value;

    public MovieName(String value) {
        if (value == null || value.isEmpty() || value.trim().length() == 0) {
            throw new InvalidNameForMovie();
        }
        this.value = value;
    }
}
