package com.example.biblioteca.modules.rentals.domain.aggregates;

import com.example.biblioteca.modules.rentals.domain.valueObjects.MovieId;
import com.example.biblioteca.modules.rentals.domain.valueObjects.UserId;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;

@Getter
@EqualsAndHashCode
public class Rental {

    private final MovieId movieId;
    private final UserId userId;

    public Rental(UUID movieId, UUID userId) {
        this.movieId = new MovieId(movieId);
        this.userId = new UserId(userId);
    }

    public boolean isRentedBy(UUID id) {
        return userId.equals(new UserId(id));
    }
}
