package me.kap.gfw.player;

import me.kap.gfw.player.factory.PlayerFactory;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.*;
import java.util.function.Consumer;

/**
 * A container for players that are a part of a game.
 * The players can be a {@link GamePlayer} or any of its subtypes.
 *
 * @param <T> The {@link GamePlayer} type that this {@link PlayerManager} contains.
 */
public class PlayerManager<T extends GamePlayer> {
    private final PlayerFactory<T> playerFactory;
    private final Map<UUID, T> players = new HashMap<>();
    private final Collection<Consumer<T>> playerAddCallbacks = new ArrayList<>();
    private final Collection<Consumer<T>> playerRemoveCallbacks = new ArrayList<>();

    public PlayerManager(@NonNull PlayerFactory<T> playerFactory) {
        this.playerFactory = playerFactory;
    }

    /**
     * Adds and creates a new {@link GamePlayer} (or subtype) to this {@link PlayerManager}.
     *
     * @param bukkitPlayer The {@link Player} which the {@link GamePlayer} type will be based on.
     */
    public void addNewPlayer(Player bukkitPlayer) {
        if (players.containsKey(bukkitPlayer.getUniqueId())) {
            return;
        }

        T player = playerFactory.createPlayer(bukkitPlayer);
        players.put(player.getBukkitPlayer().getUniqueId(), player);

        playerAddCallbacks.forEach(x -> x.accept(player));
    }

    /**
     * Removes a player with the given {@link UUID} from this {@link PlayerManager}.
     *
     * @param uuid The {@link UUID} of the player you wish to remove.
     */
    public void removePlayer(UUID uuid) {
        var player = getPlayer(uuid);

        if (player == null) {
            return;
        }

        players.remove(uuid);

        playerRemoveCallbacks.forEach(x -> x.accept(player));
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

    /**
     * Adds a callback function to execute when a player is added to this {@link PlayerManager}
     *
     * @param callback The function to execute when a player is added.
     *                 The callback function takes a player as a parameter.
     */
    public void addPlayerAddCallback(Consumer<T> callback) {
        playerAddCallbacks.add(callback);
    }

    /**
     * Adds a callback function to execute when a player is removed from this {@link PlayerManager}
     *
     * @param callback The function to execute when a player is removed.
     *                 The callback function takes a player as a parameter.
     */
    public void addPlayerRemoveCallback(Consumer<T> callback) {
        playerRemoveCallbacks.add(callback);
    }
}
