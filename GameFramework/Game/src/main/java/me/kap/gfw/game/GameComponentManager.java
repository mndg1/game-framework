package me.kap.gfw.game;

import me.kap.gfw.game.exceptions.GameComponentNotAssignedException;

import java.util.HashMap;
import java.util.Map;

/**
 * A class that manages {@link GameComponent}s for {@link Game} instances.
 */
public class GameComponentManager {
    private final Map<Class<? extends GameComponent>, GameComponent> components = new HashMap<>();

    /**
     * Calls the start method for each added {@link GameComponent}.
     *
     * @return True if all components started successfully.
     */
    boolean start() {
        for (GameComponent component : components.values()) {
            if (!component.start()) {
                return false;
            }
        }

        return true;
    }

    /**
     * Calls the end method for each added {@link GameComponent}.
     *
     * @return True if all components ended successfully.
     */
    boolean end() {
        for (GameComponent component : components.values()) {
            if (!component.end()) {
                return false;
            }
        }

        return true;
    }

    /**
     * Adds a {@link GameComponent} to the {@link Game}.
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
     * Attempts to get a {@link GameComponent} from the {@link Game}.
     *
     * @param componentClass The {@link Class} type of the {@link GameComponent}.
     * @param <T>            The type of the {@link GameComponent}.
     * @return The {@link GameComponent} of the specified type.
     * Throws an exception if this {@link GameComponent} is not added to the {@link Game}.
     */
    @SuppressWarnings("unchecked")
    public <T extends GameComponent> T getComponent(Class<T> componentClass) {
        T component = (T) components.get(componentClass);

        if (component == null) {
            String message = String.format("Game has no component of type %s", componentClass.getTypeName());
            throw new GameComponentNotAssignedException(message);
        }

        return component;
    }
}
