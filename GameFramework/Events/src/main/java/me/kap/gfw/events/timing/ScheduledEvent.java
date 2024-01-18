package me.kap.gfw.events.timing;

/**
 * Represents an event that has been scheduled to execute at a given time.
 */
record ScheduledEvent(long executionTime, TimedEvent event) {
}
