package me.kap.gfw.player;

import org.bukkit.entity.Player;

/**
 * An interface used to create instances of custom {@link GamePlayer} derived types.
 * The default implementation is the {} which creates instances of the {@link GamePlayer} type.
 *
 * @param <T> The derived {@link GamePlayer} type which this interface creates instances of.
 */
public interface PlayerFactory<T extends GamePlayer> {

    /**
     * Used to create {@link GamePlayer} derived types.
     *
     * @param bukkitPlayer The {@link Player} which the {@link GamePlayer} belongs to.
     * @return A {@link GamePlayer} derived type.
     */
    T createPlayer(Player bukkitPlayer);
}
