package me.kap.gfw.game;

/**
 * A local contract describing which methods each {@link GameBase} implementation has.
 */
abstract class GameBase {

    /**
     * Attempts to start the game.
     */
    public abstract void start();

    /**
     * Ends the game.
     */
    public abstract void end();

    /**
     * Executed at the start of a game. Used for implementing custom starting logic.
     */
    protected abstract void setup();

    /**
     * Executed at each game tick. Used for implementing repeating logic.
     */
    protected abstract void update();

    /**
     * Executed at the end of a game. Used for implementing custom ending logic.
     */
    protected abstract void finish();
}
