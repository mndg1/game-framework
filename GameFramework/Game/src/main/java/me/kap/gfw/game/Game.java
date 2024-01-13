package me.kap.gfw.game;

import me.kap.gfw.game.exceptions.GameStateChangeException;

/**
 * An abstract class defining what each {@link Game} implementation can do.
 * It also implements some logic that is universal to each {@link Game} implementation.
 */
public abstract class Game {
    private final GameComponentManager componentManager = new GameComponentManager();

    /**
     * Attempts to start the game.
     */
    public final void start() throws GameStateChangeException {
        componentManager.start();
        onStart();
    }

    /**
     * Attempts to end the game.
     */
    public final void end() throws GameStateChangeException {
        componentManager.end();
        onEnd();
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
}
