package me.kap.gfw.events.timing;

import me.kap.gfw.events.Action;

/**
 * A subclass of {@link TimedEventAction} which is scheduled to be executed once at the given time.
 */
public class SingularTimedEventAction extends TimedEventAction {

    public SingularTimedEventAction(long executionTime, Action action) {
        super(executionTime, action);
    }
}
