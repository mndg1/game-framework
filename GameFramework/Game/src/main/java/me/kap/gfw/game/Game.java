package me.kap.gfw.game;

/**
 * An abstract class defining what each {@link Game} implementation can do.
 * It also implements some logic that is universal to each {@link Game} implementation.
 */
public abstract class Game {
    private final GameComponentManager componentManager = new GameComponentManager();
    private String gameName;

    protected Game(String gameName) {
        this.gameName = gameName;
    }

    /**
     * Attempts to start the game.
     */
    public void start() {
        if (!componentManager.start()) {
            return;
        }

        setup();
    }

    /**
     * Attempts to end the game.
     */
    public void end() {
        if (!componentManager.end()) {
            return;
        }

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

    public GameComponentManager getComponentManager() {
        return componentManager;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }
}
