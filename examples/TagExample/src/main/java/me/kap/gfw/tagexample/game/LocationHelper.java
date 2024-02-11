package me.kap.gfw.tagexample.game;

import me.kap.gfw.arena.Arena;
import me.kap.gfw.arena.ArenaLocation;
import me.kap.gfw.tagexample.player.TagPlayer;

import java.util.Collection;
import java.util.List;
import java.util.Random;

public class LocationHelper {
    private static final Random random = new Random();

    private LocationHelper() {
        // Prevent instantiation.
    }

    public static void teleportPlayersToArena(Arena arena, Collection<TagPlayer> players) {
        // Teleport the player to the Bukkit location that the ArenaLocation represents.
        players.forEach(player -> {
            var index = random.nextInt(getPossibleSpawnLocations(arena).size());
            var location = getPossibleSpawnLocations(arena).get(index);
            player.getBukkitPlayer().teleport(location.bukkitLocation());
        });
    }

    // In this example, any location that is set is a spawn location.
    private static List<ArenaLocation> getPossibleSpawnLocations(Arena arena) {
        return arena.getAllLocations().stream().toList();
    }
}
