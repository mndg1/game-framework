package me.kap.gfw.arena;

import me.kap.gfw.game.GameComponent;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

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
    public boolean start() {
        return hasAllRequiredLocationsSet();
    }

    /**
     * Verifies whether the {@link Arena} contains an {@link ArenaLocation}
     * for each of the set required location names.
     *
     * @return True if all required locations are set.
     */
    public boolean hasAllRequiredLocationsSet() {
        Set<String> arenaLocationNames = getArena().getAllLocations().stream()
                .map(ArenaLocation::locationName)
                .collect(Collectors.toSet());

        return arenaLocationNames.containsAll(requiredLocationNames);
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
