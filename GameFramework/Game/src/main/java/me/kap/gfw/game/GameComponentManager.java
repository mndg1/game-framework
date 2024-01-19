package me.kap.gfw.game;

import me.kap.gfw.game.exceptions.GameComponentNotAssignedException;
import me.kap.gfw.game.exceptions.GameStateChangeException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * A class that manages {@link GameComponent}s for {@link Game} instances.
 */
public class GameComponentManager {
    private final Map<Class<? extends GameComponent>, GameComponent> components = new HashMap<>();

    /**
     * Adds a {@link GameComponent} to the {@link GameComponentManager}.
     *
     * @param component The {@link GameComponent} to add.
     */
    public void addComponent(GameComponent component) {
        if (components.containsKey(component.getClass())) {
            return;
        }

        components.put(component.getClass(), component);
    }

    /**
     * Adds a variable amount of {@link GameComponent}s to the {@link GameComponentManager}.
     *
     * @param components The {@link GameComponent}(s) to add.
     */
    public void addComponents(GameComponent... components) {
        for (var component : components) {
            addComponent(component);
        }
    }

    /**
     * Adds a collection of {@link GameComponent}s to the {@link GameComponentManager}.
     *
     * @param components The {@link GameComponent}s to add.
     */
    public void addComponents(Collection<GameComponent> components) {
        for (var component : components) {
            addComponent(component);
        }
    }

    /**
     * Attempts to get a {@link GameComponent} from the {@link GameComponentManager}.
     *
     * @param componentClass The {@link Class} type of the {@link GameComponent}.
     * @param <T>            The type of the {@link GameComponent}.
     * @return The {@link GameComponent} of the specified type.
     * Throws an exception if this {@link GameComponent} is not added to the {@link GameComponentManager}.
     */
    @SuppressWarnings("unchecked")
    public <T extends GameComponent> T getComponent(Class<T> componentClass) {
        var component = components.get(componentClass);

        if (component == null) {
            var message = String.format("Game has no component of type %s", componentClass.getTypeName());
            throw new GameComponentNotAssignedException(message);
        }

        if (!component.getClass().isAssignableFrom(componentClass)) {
            var message = String.format("Registered component of type %s is not assignable to %s",
                    component.getClass().getTypeName(),
                    componentClass.getTypeName());
            throw new GameComponentNotAssignedException(message);
        }

        return (T) component;
    }

    /**
     * Calls the start method for each added {@link GameComponent}.
     *
     * @throws GameStateChangeException When a component failed to start.
     */
    void start() throws GameStateChangeException {
        StateChangeValidator.validateComponentStart(components.values());

        for (var component : components.values()) {
            component.start();
        }
    }

    /**
     * Calls the end method for each added {@link GameComponent}.
     *
     * @throws GameStateChangeException When a component failed to end.
     */
    void end() throws GameStateChangeException {
        StateChangeValidator.validateComponentEnd(components.values());

        for (var component : components.values()) {
            component.end();
        }
    }
}
