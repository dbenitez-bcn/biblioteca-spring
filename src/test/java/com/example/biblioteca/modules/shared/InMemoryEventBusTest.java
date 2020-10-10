package com.example.biblioteca.modules.shared;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.MockitoAnnotations.initMocks;

import com.example.biblioteca.modules.shared.events.Event;
import com.example.biblioteca.modules.shared.events.InMemoryEventBus;
import com.example.biblioteca.modules.shared.events.Subscriber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

class InMemoryEventBusTest {

    private InMemoryEventBus sut;

    @Mock
    Subscriber<TestEvent> testSubscriber;
    @Mock
    Subscriber<Event> eventSubscriber;

    private static class TestEvent extends Event {

    }

    @BeforeEach
    void setUp() {
        initMocks(this);
        sut = new InMemoryEventBus();
    }

    @Test
    void publish_whenNewEventIsEmitted_shouldExecuteTheSubscribers() {
        TestEvent testEvent = new TestEvent();
        Event event = new Event();
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

        sut.publish(event);

        verifyZeroInteractions(eventSubscriber);
    }
}