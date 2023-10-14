package me.kap.gfw.game;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * A builder that enables building of different {@link Game} instances.
 *
 * @param <T> The type of {@link Game} to build.
 */
public class GameBuilder<T extends Game> {
    private final Class<T> gameTypeClass;
    private final Collection<GameComponent> components = new ArrayList<>();
    private String gameName;

    public GameBuilder(Class<T> gameTypeClass) {
        this.gameTypeClass = gameTypeClass;

        gameName = "New Game";
    }

    /**
     * Configures a {@link GameComponent} to be added to the {@link Game}.
     *
     * @param component The {@link GameComponent} to add.
     */
    public GameBuilder<T> addComponent(GameComponent component) {
        components.add(component);

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

    /**
     * @return An {@link Game} containing all configured properties.
     */
    @SuppressWarnings("java:S112")
    public T build() {
        T game;

        try {
            game = gameTypeClass.getConstructor(String.class).newInstance(gameName);
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
