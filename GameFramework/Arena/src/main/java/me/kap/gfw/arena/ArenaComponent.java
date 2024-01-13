package me.kap.gfw.arena;

import me.kap.gfw.game.GameComponent;
import me.kap.gfw.game.exceptions.GameStateChangeException;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * A {@link GameComponent} that adds {@link Arena} functionality for location dependent games.
 */
public class ArenaComponent extends GameComponent {
    private final Set<String> requiredLocationNames;
    private Arena arena;

    public ArenaComponent() {
        this(Collections.emptyList(), new Arena());
    }

    public ArenaComponent(Collection<String> requiredLocationNames) {
        this(requiredLocationNames, new Arena());
    }

    public ArenaComponent(Arena arena) {
        this(Collections.emptyList(), arena);
    }

    public ArenaComponent(Collection<String> requiredLocationNames, Arena arena) {
        this.requiredLocationNames = new HashSet<>(requiredLocationNames);
        this.arena = arena;
    }

    @Override
    public void start() throws GameStateChangeException {
        var missingLocationNames = getMissingRequiredLocationNames();

        if (!missingLocationNames.isEmpty()) {
            var errorMessage = "Arena is not in a valid state because it is missing the following locations: " +
                    String.join(", ", missingLocationNames);

            throw new GameStateChangeException(errorMessage);
        }
    }

    /**
     * Verifies whether the {@link Arena} contains an {@link ArenaLocation}
     * for each of the set required location names.
     *
     * @return True if all required locations are set.
     */
    private Collection<String> getMissingRequiredLocationNames() {
        return getRequiredLocationNames().stream()
                .filter(x -> getArena().getAllLocations().stream()
                        .map(ArenaLocation::locationName)
                        .noneMatch(x::equals))
                .toList();
    }

    public Set<String> getRequiredLocationNames() {
        return requiredLocationNames;
    }

    public Arena getArena() {
        return arena;
    }

    public void setArena(Arena arena) {
        this.arena = arena;
    }
}
