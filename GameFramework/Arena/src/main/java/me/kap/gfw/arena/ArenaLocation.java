package me.kap.gfw.arena;

import org.bukkit.Location;

/**
 * A class to represent a {@link Location} which is tied to a name.
 */
public record ArenaLocation(String locationName, Location bukkitLocation) {
}
