package me.kap.gfw.tagexample.game;

import me.kap.gfw.tagexample.player.Role;
import me.kap.gfw.tagexample.player.TagPlayer;

import java.util.*;
import java.util.stream.Collectors;

public class RoleManager {
    private final Random random = new Random();

    public void assignRandomRoles(int taggerAmount, Set<TagPlayer> players) {
        // Assign random taggers.
        for (int i = 0; i < taggerAmount; i++) {
            List<TagPlayer> unassignedPlayers = (ArrayList<TagPlayer>) getPlayersWithRole(Role.UNASSIGNED, players);

            int index = random.nextInt(unassignedPlayers.size());
            TagPlayer chosenPlayer = unassignedPlayers.get(index);

            chosenPlayer.setRole(Role.TAGGER);
        }

        // Set the remain players to be runners.
        Collection<TagPlayer> unassignedPlayers = getPlayersWithRole(Role.UNASSIGNED, players);

        for (TagPlayer player : unassignedPlayers) {
            player.setRole(Role.RUNNER);
        }
    }

    // Filters players based on their role and returns a list of all players with the matching role.
    public Collection<TagPlayer> getPlayersWithRole(Role role, Collection<TagPlayer> players) {
        return players.stream()
                .filter(x -> x.getRole() == role)
                .collect(Collectors.toList());
    }
}
