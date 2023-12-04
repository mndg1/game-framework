package me.kap.gfw.tagexample.events;

import me.kap.gfw.player.PlayerManager;
import me.kap.gfw.tagexample.player.TagPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

// This class adds players to the game when they join the server, and removes them when they leave.
public class PlayerLogEvents implements Listener {
    private final PlayerManager<TagPlayer> playerManager;

    public PlayerLogEvents(PlayerManager<TagPlayer> playerManager) {
        this.playerManager = playerManager;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        playerManager.addNewPlayer(event.getPlayer());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        playerManager.removePlayer(event.getPlayer().getUniqueId());
    }
}
