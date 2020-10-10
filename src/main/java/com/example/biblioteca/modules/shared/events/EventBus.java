package com.example.biblioteca.modules.shared.events;

public interface EventBus {

    void addSubscriber(Subscriber subscriber, Class<? extends Event> eventType);

    void publish(Event event);

}
