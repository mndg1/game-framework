package me.kap.gfw.events;

/**
 * An abstract class which represents an {@link Action} that can be executed at a given point.
 */
public abstract class EventAction {
    private final Action action;

    protected EventAction(Action action) {
        this.action = action;
    }

    /**
     * Executes the {@link Action}.
     */
    public void execute() {
        action.execute();
    }
}
