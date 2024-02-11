package me.kap.gfw.tagexample.game;

import me.kap.gfw.tagexample.player.TagPlayer;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ScoreTracker {
    private final Map<String, Integer> scores = new HashMap<>();

    public void givePoints(String playerName, int points) {
        var newScore = points;

        if (scores.containsKey(playerName)) {
            newScore = scores.get(playerName) + points;
        }

        scores.put(playerName, newScore);
    }

    public void broadcastPoints(Announcer announcer, Set<TagPlayer> players) {
        players.forEach(player -> {
            var currentScore = getPoints(player.getBukkitPlayer().getName());
            var playerScoreMessage = new ComponentBuilder()
                    .append(player.getBukkitPlayer().getDisplayName()).color(ChatColor.AQUA)
                    .append(" has ").color(ChatColor.YELLOW)
                    .append(Integer.toString(currentScore)).color(ChatColor.DARK_PURPLE)
                    .append(" points.").color(ChatColor.YELLOW)
                    .create();
            announcer.broadcast(playerScoreMessage);
        });
    }

    private int getPoints(String playerName) {
        return scores.get(playerName);
    }
}
