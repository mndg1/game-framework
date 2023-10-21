package me.kap.gfw.events.timing.actions;

import me.kap.gfw.events.Action;
import me.kap.gfw.events.EventAction;

/**
 * An abstract base class for scheduled {@link EventAction}s.
 */
public abstract class TimedEventAction extends EventAction {
    private long nextExecutionTime;

    protected TimedEventAction(long nextExecutionTime, Action action) {
        super(action);
        this.nextExecutionTime = nextExecutionTime;
    }

    public long getNextExecutionTime() {
        return nextExecutionTime;
    }

    protected void setNextExecutionTime(long nextExecutionTime) {
        this.nextExecutionTime = nextExecutionTime;
    }
}
