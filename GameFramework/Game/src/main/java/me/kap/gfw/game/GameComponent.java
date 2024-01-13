package me.kap.gfw.game;

import me.kap.gfw.game.exceptions.GameStateChangeException;

/**
 * An abstract class that allows for adding additional logic to a game.
 */
public abstract class GameComponent {

    /**
     * Allows for adding additional starting logic to a game.
     *
     * @throws GameStateChangeException If the component failed to start.
     */
    public void start() throws GameStateChangeException {
        // This method may be overridden in order to add custom starting logic.
    }

    /**
     * Allows for adding additional ending logic to a game.
     *
     * @throws GameStateChangeException If the component failed to end.
     */
    public void end() throws GameStateChangeException {
        // This method may be overridden in order to add custom ending logic.
    }
}
