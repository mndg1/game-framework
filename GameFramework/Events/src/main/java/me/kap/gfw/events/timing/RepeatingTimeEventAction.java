package me.kap.gfw.events.timing;

import me.kap.gfw.events.Action;

import java.util.Date;

/**
 * A subclass of {@link TimedEventAction} which is repeatedly scheduled at a given interval.
 */
public class RepeatingTimeEventAction extends TimedEventAction {
    private final int executionInterval;

    protected RepeatingTimeEventAction(Action action, int nextExecutionTime, int executionInterval) {
        super(action, nextExecutionTime);
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
    public long calculateNextExecutionTime() {
        Date now = new Date();
        long timeNow = now.getTime();

        return timeNow + getExecutionInterval();
    }

    public int getExecutionInterval() {
        return executionInterval;
    }
}
