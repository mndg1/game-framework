package me.kap.gfw.events.timing;

import java.util.Comparator;

/**
 * Represents an event that has been scheduled to execute at a given time.
 */
record ScheduledEvent(long executionTime, TimedEvent event) {
    static class ExecutionTimeComparator implements Comparator<ScheduledEvent> {
        @Override
        public int compare(ScheduledEvent o1, ScheduledEvent o2) {
            return (int) (o1.executionTime() - o2.executionTime());
        }
    }
}
