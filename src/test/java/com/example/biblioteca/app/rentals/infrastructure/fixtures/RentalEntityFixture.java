package com.example.biblioteca.app.rentals.infrastructure.fixtures;

import com.example.biblioteca.app.rentals.infrastructure.postgresql.entities.RentalEntity;
import java.util.UUID;

public class RentalEntityFixture {

    public static final UUID USER_ID = UUID.randomUUID();
    public static final UUID MOVIE_ID = UUID.randomUUID();

    public static RentalEntity defaultRentalEntity() {
        return new RentalEntity(MOVIE_ID, USER_ID);
    }
}
