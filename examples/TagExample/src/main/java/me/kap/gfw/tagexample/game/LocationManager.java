package me.kap.gfw.tagexample.game;

import me.kap.gfw.arena.Arena;
import me.kap.gfw.arena.ArenaLocation;
import me.kap.gfw.tagexample.player.TagPlayer;

import java.util.Collection;
import java.util.List;
import java.util.Random;

public class LocationManager {
    private final Arena arena;
    private final Random random = new Random();

    public LocationManager(Arena arena) {
        this.arena = arena;
    }

    public void teleportPlayersToArena(Collection<TagPlayer> players) {
        for (TagPlayer player : players) {
            int index = random.nextInt(getPossibleSpawnLocations().size());
            ArenaLocation location = getPossibleSpawnLocations().get(index);

            // Teleport the player to the Bukkit location that the ArenaLocation represents.
            player.getBukkitPlayer().teleport(location.getBukkitLocation());
        }
    }

    // In this example, any location that is set is a spawn location.
    private List<ArenaLocation> getPossibleSpawnLocations() {
        return arena.getAllLocations().stream().toList();
    }
}
