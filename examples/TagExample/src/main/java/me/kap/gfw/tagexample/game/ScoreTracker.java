package me.kap.gfw.tagexample.game;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;

import java.util.HashMap;
import java.util.Map;

public class ScoreTracker {
    private final Map<String, Integer> scores = new HashMap<>();

    public void givePoints(String playerName, int points) {
        var newScore = points;

        if (scores.containsKey(playerName)) {
            newScore = scores.get(playerName) + points;
        }

        scores.put(playerName, newScore);
    }

    public BaseComponent[] getPointsAnnouncementMessage() {
        var aggregatedMessage = new ComponentBuilder();

        scores.forEach((playerName, score) -> aggregatedMessage
                .append(playerName).color(ChatColor.AQUA)
                .append(" has ").color(ChatColor.YELLOW)
                .append(Integer.toString(score)).color(ChatColor.DARK_PURPLE)
                .append(" points.").color(ChatColor.YELLOW)
                .append("\n"));

        return aggregatedMessage.create();
    }
}
