package com.example.biblioteca.app.multimedia.application.responses;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
@EqualsAndHashCode
public class MovieCreatedResponse {
    public final UUID id;
}
