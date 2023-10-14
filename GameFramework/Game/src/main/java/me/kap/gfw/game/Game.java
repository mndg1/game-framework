package me.kap.gfw.game;

/**
 * A class implementing the very basic logic of a Game as defined in {@link GameBase}.
 * This class can be extended to create custom {@link Game} implementations.
 */
public abstract class Game extends GameBase {

    @Override
    public void start() {
        setup();
    }

    @Override
    public void end() {
        finish();
    }
}
