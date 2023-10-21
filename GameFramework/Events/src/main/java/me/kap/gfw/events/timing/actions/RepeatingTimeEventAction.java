package me.kap.gfw.events.timing.actions;

import me.kap.gfw.events.Action;

import java.time.Clock;

/**
 * A subclass of {@link TimedEventAction} which is repeatedly scheduled at a given interval.
 */
public class RepeatingTimeEventAction extends TimedEventAction {
    private final Clock clock;
    private final int executionInterval;

    public RepeatingTimeEventAction(Clock clock,
                                    long nextExecutionTime,
                                    int executionInterval,
                                    Action action) {
        super(nextExecutionTime, action);
        this.clock = clock;
        this.executionInterval = executionInterval;
    }

    @Override
    public void execute() {
        super.execute();
        setNextExecutionTime(calculateNextExecutionTime());
    }

    /**
     * @return The epoch time value for when this action should next execute.
     */
    private long calculateNextExecutionTime() {
        long timeNow = clock.millis();

        return timeNow + getExecutionInterval();
    }

    public int getExecutionInterval() {
        return executionInterval;
    }
}
