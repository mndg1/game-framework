package me.kap.gfw.game;

import me.kap.gfw.player.GamePlayer;
import me.kap.gfw.player.PlayerManager;

/**
 * An abstract class defining what each {@link Game} implementation can do.
 * It also implements some logic that is universal to each {@link Game} implementation.
 */
public abstract class Game<TPlayer extends GamePlayer> {
    private final GameComponentManager componentManager = new GameComponentManager();
    private final PlayerManager<TPlayer> playerManager;
    private String gameName;

    protected Game(String gameName, PlayerManager<TPlayer> playerManager) {
        this.gameName = gameName;
        this.playerManager = playerManager;
    }

    /**
     * Attempts to start the game.
     */
    public final void start() {
        if (!componentManager.start()) {
            return;
        }

        setup();
    }

    /**
     * Attempts to end the game.
     */
    public final void end() {
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

    public PlayerManager<TPlayer> getPlayerManager() {
        return playerManager;
    }
}
