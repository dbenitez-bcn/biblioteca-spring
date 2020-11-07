package com.example.biblioteca.app.rentals.application.responses;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@EqualsAndHashCode
@AllArgsConstructor
public class MovieResponse {
    public final UUID id;
    public final String name;
}
