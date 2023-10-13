package me.kap.gfw.player;

import me.kap.gfw.player.exceptions.InvalidPlayerFactoryException;
import me.kap.gfw.player.factory.FactoryRegistry;
import me.kap.gfw.player.factory.PlayerFactory;
import org.bukkit.entity.Player;

import java.util.*;

/**
 * A container for players that are a part of a game.
 * The players can be a {@link GamePlayer} or any of it's subtypes.
 *
 * @param <T> The {@link GamePlayer} type that this {@link PlayerManager} contains.
 */
public class PlayerManager<T extends GamePlayer> {
    private final PlayerFactory<T> playerFactory;
    private final Map<UUID, T> players = new HashMap<>();

    public PlayerManager(Class<T> gamePlayerClass, FactoryRegistry factoryRegistry) {
        playerFactory = factoryRegistry.getFactory(gamePlayerClass);

        if (playerFactory == null) {
            throw new InvalidPlayerFactoryException("Class has no valid PlayerFactory");
        }
    }

    /**
     * Adds and creates a new {@link GamePlayer} (or subtype) to this {@link PlayerManager}.
     *
     * @param bukkitPlayer The {@link Player} which the {@link GamePlayer} type will be based on.
     */
    public void addNewPlayer(Player bukkitPlayer) {
        T player = playerFactory.createPlayer(bukkitPlayer);
        players.put(player.getBukkitPlayer().getUniqueId(), player);
    }

    /**
     * Removes a player with the given {@link UUID} from this {@link PlayerManager}.
     *
     * @param uuid The {@link UUID} of the player you wish to remove.
     */
    public void removePlayer(UUID uuid) {
        players.remove(uuid);
    }

    /**
     * @param uuid The {@link UUID} of the {@link GamePlayer} (or subtype) that you want to look up.
     * @return A {@link GamePlayer} (or subtype) with the given {@link UUID}
     * if the {@link PlayerManager} contains such a player
     */
    public T getPlayer(UUID uuid) {
        return players.get(uuid);
    }

    /**
     * @return A set of all {@link GamePlayer} (or subtype) instances that are contained by this {@link PlayerManager}.
     * This Set does not reference the original collection of players that are contained by this {@link PlayerManager}
     * so modifying this list will not result in changes for the {@link PlayerManager}.
     */
    public Set<T> getAllPlayers() {
        return new HashSet<>(players.values());
    }
}
