package me.kap.gfw.events;

/**
 * A functional interface which is used to register actions to be executed when an event occurs.
 */
@FunctionalInterface
public interface Action {

    /**
     * The action to execute when the event occurs.
     */
    void execute();
}
