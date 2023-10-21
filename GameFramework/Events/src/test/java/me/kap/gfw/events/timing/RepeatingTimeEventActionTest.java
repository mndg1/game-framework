package me.kap.gfw.events.timing;

import me.kap.gfw.events.timing.actions.RepeatingTimeEventAction;
import me.kap.gfw.events.timing.actions.TimedEventAction;
import org.junit.jupiter.api.Test;

import java.time.Clock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RepeatingTimeEventActionTest {

    @Test
    void whenExecute_withInterval_thenNextExecutionTimeIsCalculated() {
        // arrange
        long startExecutionTime = 0L;
        int interval = 10;

        Clock clock = mock(Clock.class);
        when(clock.millis()).thenReturn(startExecutionTime);
        TimedEventAction action = new RepeatingTimeEventAction(clock, startExecutionTime, interval, () -> {});

        // act
        action.execute();

        // assert
        long expectedExecutionTime = startExecutionTime + interval;

        assertEquals(expectedExecutionTime, action.getNextExecutionTime());
    }
}