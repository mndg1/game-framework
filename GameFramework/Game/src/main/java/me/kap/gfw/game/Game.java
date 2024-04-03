package me.kap.gfw.game;

import me.kap.gfw.game.exceptions.GameStateChangeException;

/**
 * An abstract class defining what each {@link Game} implementation can do.
 * It also implements some logic that is universal to each {@link Game} implementation.
 */
public abstract class Game {
    private final GameComponentManager componentManager = new GameComponentManager();
    private GameState state = GameState.IDLE;

    /**
     * Attempts to start the game.
     */
    public final void start() throws GameStateChangeException {
        state = GameState.STARTING;

        componentManager.performStateChange(state);
        onStart();

        state = GameState.RUNNING;
    }

    /**
     * Attempts to end the game.
     */
    public final void end() throws GameStateChangeException {
        state = GameState.ENDING;

        componentManager.performStateChange(state);
        onEnd();

        state = GameState.IDLE;
    }

    /**
     * Executed at the start of a game. Used for implementing custom starting logic.
     */
    protected void onStart() {
        // This method can be optionally overridden to add starting logic.
    }

    /**
     * Executed at the end of a game. Used for implementing custom ending logic.
     */
    protected void onEnd() {
        // This method can be optionally overridden to add ending logic.
    }

    public GameComponentManager getComponentManager() {
        return componentManager;
    }

    public GameState getState() {
        return state;
    }
}
