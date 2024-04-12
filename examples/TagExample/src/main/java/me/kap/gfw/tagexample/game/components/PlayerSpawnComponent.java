package me.kap.gfw.tagexample.game.components;

import me.kap.gfw.arena.Arena;
import me.kap.gfw.arena.ArenaComponent;
import me.kap.gfw.arena.ArenaLocation;
import me.kap.gfw.game.GameComponent;
import me.kap.gfw.game.GameComponentConfigurationBuilder;
import me.kap.gfw.player.GamePlayer;
import me.kap.gfw.player.PlayerManager;

import java.util.List;
import java.util.Random;

// This component handles the teleportation of players to the arena when the game starts.
public class PlayerSpawnComponent extends GameComponent {
    private final ArenaComponent arenaComponent;
    private final PlayerManager<? extends GamePlayer> playerManager;
    private final Random random = new Random();

    public PlayerSpawnComponent(ArenaComponent arenaComponent, PlayerManager<? extends GamePlayer> playerManager) {
        this.arenaComponent = arenaComponent;
        this.playerManager = playerManager;
    }

    @Override
    public void configure(GameComponentConfigurationBuilder configurationBuilder) {
        // There must be at least one location for players to teleport to for this component to work.
        configurationBuilder.addStartCondition(
                () -> !getPossibleSpawnLocations(arenaComponent.getArena()).isEmpty(),
                "No locations have been set which players can be teleported to");
    }

    @Override
    public void start() {
        var arena = arenaComponent.getArena();

        playerManager.getAllPlayers().forEach(player -> {
            var locationIndex = random.nextInt(getPossibleSpawnLocations(arena).size());
            var location = getPossibleSpawnLocations(arena).get(locationIndex);
            player.getBukkitPlayer().teleport(location.bukkitLocation());
        });
    }

    // In this example, any location that is set is a spawn location.
    private static List<ArenaLocation> getPossibleSpawnLocations(Arena arena) {
        return arena.getAllLocations().stream().toList();
    }
}
