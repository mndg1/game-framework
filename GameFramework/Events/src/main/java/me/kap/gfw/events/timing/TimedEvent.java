package me.kap.gfw.events.timing;

import java.time.Duration;

/**
 * Represents an event that can execute at a given time.
 */
public record TimedEvent(Runnable callback, boolean repeating, Duration interval) {
}
