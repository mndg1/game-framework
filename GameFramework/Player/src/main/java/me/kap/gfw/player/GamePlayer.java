package me.kap.gfw.player;

import org.bukkit.entity.Player;

/**
 * The default {@link GamePlayer} type which represents players in a game.
 * This type can be extended in order to add additional fields and logic
 * if this is needed for a game implementation.
 */
public class GamePlayer {
    private final Player bukkitPlayer;

    public GamePlayer(Player bukkitPlayer) {
        this.bukkitPlayer = bukkitPlayer;
    }

    public Player getBukkitPlayer() {
        return bukkitPlayer;
    }
}
