package com.example.biblioteca.modules.shared.events;

public interface Subscriber<E extends Event> {
    void on(E event);
}
