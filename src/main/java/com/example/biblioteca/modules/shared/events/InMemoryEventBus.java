package com.example.biblioteca.modules.shared.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class InMemoryEventBus implements EventBus {

    private Map<Class<? extends Event>, List<Subscriber>> subscribers;

    public InMemoryEventBus() {
        this.subscribers = new HashMap<>();
    }

    @Override
    public void addSubscriber(Subscriber subscriber, Class<? extends Event> eventType) {
        List<Subscriber> eventSubscribers = this.subscribers.get(eventType);
        if (eventSubscribers != null) {
            eventSubscribers.add(subscriber);
        } else {
            List<Subscriber> newSubscribers = new ArrayList<>();
            newSubscribers.add(subscriber);
            this.subscribers.put(eventType, newSubscribers);
        }
    }

    @Override
    public void publish(Event event) {
        List<Subscriber> eventSubscribers = this.subscribers
            .get(event.getClass());

        if (eventSubscribers != null) {
            eventSubscribers
                .forEach(subscriber -> subscriber.on(event));
        }
    }
}
