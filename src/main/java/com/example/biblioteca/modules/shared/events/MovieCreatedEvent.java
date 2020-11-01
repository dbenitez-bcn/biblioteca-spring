package com.example.biblioteca.modules.shared.events;

import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class MovieCreatedEvent extends Event {
    public final UUID id;
    public final String name;
    public final int year;
}
