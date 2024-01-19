package me.kap.gfw.game;

import me.kap.gfw.game.exceptions.GameStateChangeException;

import java.util.ArrayList;
import java.util.Collection;

class StateChangeValidator {

    private StateChangeValidator() {
        // Prevent instantiation.
    }

    /**
     * Runs each given component's start method.
     * An aggregated exception is built if one or more exceptions were caught.
     *
     * @param components The components to validate.
     * @throws GameStateChangeException An aggregated exception built from all caught exceptions.
     */
    static void validateComponentStart(Collection<GameComponent> components)
            throws GameStateChangeException {
        var caughtExceptionMessages = new ArrayList<String>();

        for (var component : components) {
            try {
                component.validateStart();
            } catch (GameStateChangeException stateChangeException) {
                caughtExceptionMessages.add(stateChangeException.getMessage());
            }
        }

        if (!caughtExceptionMessages.isEmpty()) {
            var aggregatedErrorMessage = String.join("; ", caughtExceptionMessages);
            var errorMessage = String.format("Failed to start game: %s", aggregatedErrorMessage);
            throw new GameStateChangeException(errorMessage);
        }
    }

    /**
     * Runs each given component's end method.
     * An aggregated exception is built if one or more exceptions were caught.
     *
     * @param components The components to validate.
     * @throws GameStateChangeException An aggregated exception built from all caught exceptions.
     */
    static void validateComponentEnd(Collection<GameComponent> components)
            throws GameStateChangeException {
        var caughtExceptionMessages = new ArrayList<String>();

        for (var component : components) {
            try {
                component.validateEnd();
            } catch (GameStateChangeException stateChangeException) {
                caughtExceptionMessages.add(stateChangeException.getMessage());
            }
        }

        if (!caughtExceptionMessages.isEmpty()) {
            var aggregatedErrorMessage = String.join("; ", caughtExceptionMessages);
            var errorMessage = String.format("Failed to end game: %s", aggregatedErrorMessage);
            throw new GameStateChangeException(errorMessage);
        }
    }
}
