package me.kap.gfw.events.timing.comparators;

import me.kap.gfw.events.timing.SingularTimedEventAction;
import me.kap.gfw.events.timing.TimedEventAction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TimedEventComparatorTest {

    @Test
    void whenComparingTimedEventActions_thenSmallerExecutionTimeIsFirst() {
        // arrange
        TimedEventAction eventActionA = new SingularTimedEventAction(1, () -> {});
        TimedEventAction eventActionB = new SingularTimedEventAction(2, () -> {});
        TimedEventComparator comparator = new TimedEventComparator();

        // act
        int result = comparator.compare(eventActionA, eventActionB);

        // assert
        assertEquals(-1, result);
    }
}