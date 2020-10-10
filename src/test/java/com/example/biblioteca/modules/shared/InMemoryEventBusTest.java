package com.example.biblioteca.modules.shared;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

import com.example.biblioteca.modules.shared.events.Event;
import com.example.biblioteca.modules.shared.events.InMemoryEventBus;
import com.example.biblioteca.modules.shared.events.Subscriber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InMemoryEventBusTest {

    private InMemoryEventBus sut;

    private static class TestEvent extends Event {

    }

    @BeforeEach
    void setUp() {
        sut = new InMemoryEventBus();
    }

    @Test
    void publish_whenNewEventIsEmitted_shouldExecuteTheSubscribers() {
        TestEvent testEvent = new TestEvent();
        Event event = new Event();
        Subscriber testSubscriber = mock(Subscriber.class);
        Subscriber eventSubscriber = mock(Subscriber.class);
        sut.addSubscriber(testSubscriber, TestEvent.class);
        sut.addSubscriber(eventSubscriber, Event.class);

        sut.publish(testEvent);
        sut.publish(testEvent);
        sut.publish(testEvent);
        sut.publish(event);

        verify(testSubscriber, times(3)).on(testEvent);
        verify(eventSubscriber, times(1)).on(event);
    }

    @Test
    void publish_whenNoSubscribersSubscribed_shouldNotCallAnySubscriber() {
        Event event = new Event();
        Subscriber eventSubscriber = mock(Subscriber.class);

        sut.publish(event);

        verifyZeroInteractions(eventSubscriber);
    }
}