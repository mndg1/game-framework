package me.kap.gfw.events;

public abstract class EventAction {
    private final Action action;

    protected EventAction(Action action) {
        this.action = action;
    }

    public void execute() {
        action.execute();
    }
}
