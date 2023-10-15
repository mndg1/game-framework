package me.kap.gfw.arena;

import org.bukkit.Location;

/**
 * A class to represent a {@link Location} which is tied to a name.
 */
public class ArenaLocation {
    private final String locationName;
    private final Location bukkitLocation;

    public ArenaLocation(String locationName, Location bukkitLocation) {
        this.locationName = locationName;
        this.bukkitLocation = bukkitLocation;
    }

    public String getLocationName() {
        return locationName;
    }

    public Location getBukkitLocation() {
        return bukkitLocation;
    }
}
