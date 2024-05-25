package me.kap.gfw.events.timing;

import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.Clock;
import java.time.Duration;
import java.util.*;

/**
 * A class that is responsible for executing scheduled events.
 */
public class EventTimer {
    private final Clock clock;
    private final Queue<ScheduledEvent> scheduledEvents = new PriorityQueue<>(Comparator.comparing(ScheduledEvent::executionTime));

    private BukkitRunnable timer;

    public EventTimer() {
        this(Clock.systemUTC());
    }

    public EventTimer(Clock clock) {
        this.clock = clock;
    }

    /**
     * Starts the timer.
     *
     * @param plugin The {@link Plugin} that schedules the task.
     */
    public void startTimer(Plugin plugin) {
        timer = new BukkitRunnable() {
            @Override
            public void run() {
                processEvents();
            }
        };

        timer.runTaskTimer(plugin, 0, 20);
    }

    /**
     * Stops the timer.
     */
    public void stopTimer() {
        timer.cancel();
        scheduledEvents.clear();
    }

    /**
     * Schedules an event to execute at a certain time.
     * When passing an interval that is greater than 0, the event will be repeating itself indefinitely.
     *
     * @param delay    The delay after which to execute the event.
     * @param callback The callback which should be executed.
     */
    public void scheduleEvent(Duration delay, Runnable callback) {
        scheduleEvent(delay, callback, Duration.ZERO);
    }

    /**
     * Schedules an event to execute at a certain time.
     * When passing an interval that is greater than 0, the event will be repeating itself indefinitely.
     *
     * @param delay     The delay after which to execute the event.
     * @param callback  The callback which should be executed.
     * @param interval  The interval used to schedule repeating events.
     */
    public void scheduleEvent(Duration delay, Runnable callback, Duration interval) {
        var shouldRepeat = interval.toMillis() > Duration.ZERO.toMillis();
        var event = new TimedEvent(callback, shouldRepeat, interval);

        var executionTime = clock.millis() + delay.toMillis();
        var scheduledEvent = new ScheduledEvent(executionTime, event);
        scheduledEvents.offer(scheduledEvent);
    }

    /**
     * Executes the scheduled {@link TimedEvent}s.
     */
    private void processEvents() {
        var now = clock.millis();

        var eventsToExecute = pollEventsToExecute(now);

        for (var event : eventsToExecute) {
            event.callback().run();

            if (event.repeating()) {
                scheduleEvent(event.interval(), event.callback(), event.interval());
            }
        }
    }

    /**
     * Retrieves and removes all {@link TimedEvent} instances which should be executed at the given time.
     *
     * @param timeNow The current epoch time.
     * @return A collection of {@link TimedEvent}s which should be executed.
     */
    private Collection<TimedEvent> pollEventsToExecute(long timeNow) {
        var eventsToExecute = new ArrayList<TimedEvent>();

        while (!scheduledEvents.isEmpty()) {
            if (scheduledEvents.peek().executionTime() > timeNow) {
                break;
            }

            eventsToExecute.add(scheduledEvents.poll().event());
        }

        return eventsToExecute;
    }

    public Clock getClock() {
        return clock;
    }
}
