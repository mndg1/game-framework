package me.kap.gfw.player;

import org.bukkit.entity.Player;

public class GamePlayer {
    private final Player bukkitPlayer;

    public GamePlayer(Player bukkitPlayer) {
        this.bukkitPlayer = bukkitPlayer;
    }

    public Player getBukkitPlayer() {
        return bukkitPlayer;
    }
}
