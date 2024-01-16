package me.kap.gfw.events.timing;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ScheduledEventTest {

    @Test
    void whenCompareExecutionTimes_thenSmallerExecutionTimeIsFirst() {
        // arrange
        var eventFake = mock(TimedEvent.class);
        var firstScheduledEvent = new ScheduledEvent(1, eventFake);
        var secondScheduledEvent = new ScheduledEvent(2, eventFake);
        var comparator = new ScheduledEvent.ExecutionTimeComparator();

        // act
        var result = comparator.compare(firstScheduledEvent, secondScheduledEvent);

        // assert
        assertEquals(-1, result);
    }
}