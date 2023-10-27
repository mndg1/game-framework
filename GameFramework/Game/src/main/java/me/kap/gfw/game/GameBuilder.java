package me.kap.gfw.game;

import me.kap.gfw.game.exceptions.CannotBuildGameTypeException;
import me.kap.gfw.player.GamePlayer;
import me.kap.gfw.player.PlayerManager;
import me.kap.gfw.player.factory.GamePlayerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A builder that enables building of different {@link Game} instances.
 *
 * @param <T> The type of {@link Game} to build.
 */
public class GameBuilder<T extends Game> {
    private final Class<T> gameTypeClass;
    private final Collection<GameComponent> components = new ArrayList<>();
    private PlayerManager<? extends GamePlayer> playerManager;
    private String gameName;

    public GameBuilder(Class<T> gameTypeClass) {
        this.gameTypeClass = gameTypeClass;

        playerManager = new PlayerManager<>(new GamePlayerFactory());
        gameName = "New Game";
    }

    /**
     * Configures a {@link GameComponent} to be added to the {@link Game}.
     *
     * @param components The {@link GameComponent}(s) to add.
     */
    public GameBuilder<T> addComponent(GameComponent... components) {
        this.components.addAll(List.of(components));

        return this;
    }

    /**
     * Configures a custom name to be set for the {@link Game}.
     *
     * @param gameName The name that the {@link Game should have}.
     */
    public GameBuilder<T> setGameName(String gameName) {
        this.gameName = gameName;

        return this;
    }

    public GameBuilder<T> setPlayerManager(PlayerManager<? extends GamePlayer> playerManager) {
        this.playerManager = playerManager;

        return this;
    }

    /**
     * @return An {@link Game} containing all configured properties.
     */
    @SuppressWarnings("java:S112")
    public T build() {
        T game;

        if (gameTypeClass == Game.class) {
            String message = String.format("Cannot build games of type %s", gameTypeClass.getTypeName());
            throw new CannotBuildGameTypeException(message);
        }

        try {
            game = gameTypeClass.getConstructor(String.class, PlayerManager.class).newInstance(gameName, playerManager);
        } catch (InstantiationException |
                 IllegalAccessException |
                 InvocationTargetException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        for (GameComponent component : components) {
            game.getComponentManager().addComponent(component);
        }

        return game;
    }
}
