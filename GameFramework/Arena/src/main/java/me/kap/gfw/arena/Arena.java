package me.kap.gfw.arena;

import org.bukkit.Location;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * A class that keeps track of {@link Location}s represented by {@link ArenaLocation}s.
 * An {@link Arena} can used to include {@link Location} dependent logic to a game.
 */
public class Arena {
    private final Map<String, ArenaLocation> arenaLocations = new HashMap<>();

    private String arenaName;

    public Arena() {
        this("NewArena");
    }

    public Arena(String arenaName) {
        this.arenaName = arenaName;
    }

    /**
     * Add a new {@link ArenaLocation} to this {@link Arena} or overwrite an existing {@link ArenaLocation}.
     *
     * @param locationName   The name of the {@link ArenaLocation}.
     * @param bukkitLocation The {@link Location} representing the in-game location.
     * @return The set {@link ArenaLocation}.
     */
    public ArenaLocation setLocation(String locationName, Location bukkitLocation) {
        ArenaLocation location = new ArenaLocation(locationName, bukkitLocation);

        return setLocation(location);
    }

    /**
     * Add a new {@link ArenaLocation} to this {@link Arena} or overwrite an existing {@link ArenaLocation}.
     *
     * @param location The {@link ArenaLocation} to set.
     * @return The set {@link ArenaLocation}.
     */
    public ArenaLocation setLocation(ArenaLocation location) {
        arenaLocations.put(location.locationName(), location);

        return location;
    }

    /**
     * Removes an {@link ArenaLocation} from the {@link Arena}.
     *
     * @param locationName The name of the {@link ArenaLocation} to remove.
     */
    public void removeLocation(String locationName) {
        arenaLocations.remove(locationName);
    }

    /**
     * Attempts to get an {@link ArenaLocation} from the {@link Arena}.
     *
     * @param locationName The name of the {@link ArenaLocation} to get.
     * @return An {@link ArenaLocation} with the given name.
     * Null if no {@link ArenaLocation} with that name exists.
     */
    public ArenaLocation getLocation(String locationName) {
        return arenaLocations.get(locationName);
    }

    /**
     * @return All {@link ArenaLocation}s contained within this {@link Arena}.
     */
    public Set<ArenaLocation> getAllLocations() {
        return new HashSet<>(arenaLocations.values());
    }

    public String getArenaName() {
        return arenaName;
    }

    public void setArenaName(String arenaName) {
        this.arenaName = arenaName;
    }
}
