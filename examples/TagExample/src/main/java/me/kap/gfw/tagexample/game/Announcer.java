package me.kap.gfw.tagexample.game;

import me.kap.gfw.player.GamePlayer;
import me.kap.gfw.player.PlayerManager;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;

public class Announcer {
    private final PlayerManager<? extends GamePlayer> playerManager;

    public Announcer(PlayerManager<? extends GamePlayer> playerManager) {
        this.playerManager = playerManager;
    }

    public void broadcast(BaseComponent[] message) {
        for (GamePlayer player : playerManager.getAllPlayers()) {
            player.getBukkitPlayer().spigot().sendMessage(message);
        }
    }

    public void broadcast(String message) {
        broadcast(new ComponentBuilder(message).create());
    }
}
