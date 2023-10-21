package me.kap.gfw.events.timing;

import me.kap.gfw.events.Action;
import me.kap.gfw.events.EventAction;

public abstract class TimedEventAction extends EventAction {
    private long nextExecutionTime;

    protected TimedEventAction(Action action, long nextExecutionTime) {
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
