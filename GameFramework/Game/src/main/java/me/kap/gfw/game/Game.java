package me.kap.gfw.game;

public abstract class Game {

    /**
     * Attempts to start the game.
     */
    public final void start() {
        setup();
    }

    /**
     * Ends the game.
     */
    public final void end() {
        finish();
    }

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
