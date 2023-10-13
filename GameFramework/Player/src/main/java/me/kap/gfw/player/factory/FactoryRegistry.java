package me.kap.gfw.player.factory;

import me.kap.gfw.player.GamePlayer;
import me.kap.gfw.player.exceptions.NoPlayerFactoryForTypeException;

import java.util.HashMap;
import java.util.Map;

/**
 * A registry of all the {@link PlayerFactory} implementations.
 */
public final class FactoryRegistry {
    private final Map<Class<? extends GamePlayer>, PlayerFactory<? extends GamePlayer>> factories = new HashMap<>();

    /**
     * Registers a new {@link PlayerFactory} implementation.
     *
     * @param gamePlayerClass The type of {@link GamePlayer} that the {@link PlayerFactory} creates.
     * @param factory         An instance of the {@link PlayerFactory}.
     * @param <T>             The type of {@link GamePlayer} that the {@link PlayerFactory} creates.
     */
    public <T extends GamePlayer> void registerFactory(Class<T> gamePlayerClass, PlayerFactory<T> factory) {
        factories.put(gamePlayerClass, factory);
    }

    /**
     * Attempts to get a {@link PlayerFactory} of the given type.
     *
     * @param gamePlayerClass The type of {@link GamePlayer} that the {@link PlayerFactory} creates.
     * @param <T>             The type of {@link GamePlayer} that the {@link PlayerFactory} creates.
     * @return A {@link PlayerFactory} if one is registered with the given {@link GamePlayer} type.
     */
    @SuppressWarnings("unchecked")
    public <T extends GamePlayer> PlayerFactory<T> getFactory(Class<T> gamePlayerClass) {
        if (!factories.containsKey(gamePlayerClass)) {
            String errorMessage = String.format("No factory was registered for %s", gamePlayerClass.getTypeName());
            throw new NoPlayerFactoryForTypeException(errorMessage);
        }

        return (PlayerFactory<T>) factories.get(gamePlayerClass);
    }
}
