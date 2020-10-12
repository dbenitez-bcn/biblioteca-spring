package com.example.biblioteca.app.rentals.application.request;

import java.util.UUID;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RentRequest {
    public final UUID movieId;
    public final UUID userId;
}
