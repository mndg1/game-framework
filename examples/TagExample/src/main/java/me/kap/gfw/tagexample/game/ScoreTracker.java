package me.kap.gfw.tagexample.game;

import java.util.HashMap;
import java.util.Map;

public class ScoreTracker {
    private Map<String, Integer> scores = new HashMap<>();

    public void givePoints(String playerName, int points) {
        int newScore = points;

        if (scores.containsKey(playerName)) {
            newScore = scores.get(playerName) + points;
        }

        scores.put(playerName, newScore);
    }

    public int getPoints(String playerName) {
        return scores.get(playerName);
    }
}
