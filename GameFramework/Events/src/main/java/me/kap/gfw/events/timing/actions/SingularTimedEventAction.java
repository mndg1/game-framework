package me.kap.gfw.events.timing.actions;

/**
 * A subclass of {@link TimedEventAction} which is scheduled to be executed once at the given time.
 */
public class SingularTimedEventAction extends TimedEventAction {

    public SingularTimedEventAction(long executionTime, Runnable callback) {
        super(executionTime, callback);
    }
}
