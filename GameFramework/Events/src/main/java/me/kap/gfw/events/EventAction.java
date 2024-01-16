package me.kap.gfw.events;

/**
 * An abstract class which holds a {@link Runnable} that can be executed at a given point.
 */
public abstract class EventAction {
    private final Runnable callback;

    protected EventAction(Runnable callback) {
        this.callback = callback;
    }

    /**
     * Executes the event callback.
     */
    public void execute() {
        callback.run();
    }
}
