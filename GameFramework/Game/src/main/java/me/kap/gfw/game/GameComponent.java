package me.kap.gfw.game;

/**
 * An abstract class that allows for adding additional logic to a game.
 */
public abstract class GameComponent {

    /**
     * Allows for adding additional starting logic to a game.
     *
     * @return True if the starting logic was successful.
     */
    public boolean start() {
        return true;
    }

    /**
     * Allows for adding additional ending logic to a game.
     *
     * @return True if the ending logic was successful.
     */
    public boolean end() {
        return true;
    }
}
