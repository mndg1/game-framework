package me.kap.gfw.game;

import me.kap.gfw.arena.Arena;
import me.kap.gfw.arena.ArenaLocation;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A class that extends the {@link Game} class with Arena logic.
 * This class can be extended to create custom {@link Game} implementations that make use of an Arena.
 */
public abstract class LocalisedGame extends Game {
    private final Set<String> requiredLocationNames;
    private Arena arena;

    protected LocalisedGame(Collection<String> requiredLocationNames) {
        this.requiredLocationNames = new HashSet<>(requiredLocationNames);
    }

    @Override
    public void start() {
        if (!arenaContainsRequiredLocations()) {
            return;
        }

        super.start();
    }

    @Override
    public void end() {
        super.end();
    }

    /**
     * Checks the {@link LocalisedGame} instance's {@link Arena}
     * to see if the locations that are required for the game to be played are set.
     *
     * @return True if the {@link Arena} has all required locations set.
     */
    public boolean arenaContainsRequiredLocations() {
        Set<String> arenaLocationNames = getArena().getAllLocations().stream()
                .map(ArenaLocation::getLocationName)
                .collect(Collectors.toSet());

        return arenaLocationNames.containsAll(requiredLocationNames);
    }

    /**
     * @return The {@link Arena} that is currently set for this {@link LocalisedGame} instance.
     * If no {@link Arena} is set, a new {@link Arena} will be created and returned.
     */
    public Arena getArena() {
        if (arena == null) {
            arena = new Arena();
        }

        return arena;
    }

    /**
     * Sets the {@link Arena} for this {@link LocalisedGame} instance.
     *
     * @param arena The {@link Arena} to assign.
     */
    public void setArena(Arena arena) {
        this.arena = arena;
    }
}
