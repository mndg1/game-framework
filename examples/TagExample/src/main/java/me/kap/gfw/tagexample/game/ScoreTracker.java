package me.kap.gfw.tagexample.game;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;

import java.util.HashMap;
import java.util.Iterator;
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
        var playerScoreMessage = new ComponentBuilder();

        for (Iterator<Map.Entry<String, Integer>> iterator = scores.entrySet().iterator(); iterator.hasNext(); ) {
            Map.Entry<String, Integer> entry = iterator.next();
            String playerName = entry.getKey();
            Integer score = entry.getValue();

            playerScoreMessage
                    .append(playerName).color(ChatColor.AQUA)
                    .append(" has ").color(ChatColor.YELLOW)
                    .append(Integer.toString(score)).color(ChatColor.DARK_PURPLE)
                    .append(" points.").color(ChatColor.YELLOW);

            if (iterator.hasNext()) {
                playerScoreMessage.append("\n");
            }
        }

        return playerScoreMessage.create();
    }
}
