package me.kap.gfw.game;

import me.kap.gfw.game.exceptions.GameStateChangeException;

/**
 * An abstract class that allows for adding additional logic to a game.
 */
public abstract class GameComponent {

    /**
     * Allows for adding additional starting logic to a game.
     */
    public void start() {
        // This method may be overridden in order to add custom starting logic.
    }

    /**
     * Allows for adding additional ending logic to a game.
     */
    public void end() {
        // This method may be overridden in order to add custom ending logic.
    }

    /**
     * Validate whether the component can allow the game to start.
     *
     * @throws GameStateChangeException When the component should prevent the game from moving into the start phase.
     */
    public void validateStart() throws GameStateChangeException {
        // This method may be overridden in order to add validation logic.
    }

    /**
     * Validate whether the component can allow the game to end.
     *
     * @throws GameStateChangeException When the component should prevent the game from moving to the end phase.
     */
    public void validateEnd() throws GameStateChangeException {
        // This method may be overridden in order to add validation logic.
    }
}
