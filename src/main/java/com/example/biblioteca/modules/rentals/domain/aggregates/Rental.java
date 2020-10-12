package com.example.biblioteca.modules.rentals.domain.aggregates;

import com.example.biblioteca.modules.rentals.domain.valueObjects.MovieId;
import com.example.biblioteca.modules.rentals.domain.valueObjects.UserId;
import java.util.UUID;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class Rental {

    private final MovieId movieId;
    private final UserId userId;

    public Rental(UUID movieId, UUID userId) {
        this.movieId = new MovieId(movieId);
        this.userId = new UserId(userId);
    }
}
