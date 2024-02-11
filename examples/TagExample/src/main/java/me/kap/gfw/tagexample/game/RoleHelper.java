package me.kap.gfw.tagexample.game;

import me.kap.gfw.tagexample.player.Role;
import me.kap.gfw.tagexample.player.TagPlayer;

import java.util.*;

public class RoleHelper {
    private static final Random random = new Random();

    private RoleHelper() {
        // Prevent instantiation.
    }

    public static void assignRandomRoles(int taggerAmount, Set<TagPlayer> players) {
        // Assign taggers at random.
        for (int i = 0; i < taggerAmount; i++) {
            var unassignedPlayers = (List<TagPlayer>) getPlayersWithRole(Role.UNASSIGNED, players);

            var index = random.nextInt(unassignedPlayers.size());
            var chosenPlayer = unassignedPlayers.get(index);

            chosenPlayer.setRole(Role.TAGGER);
        }

        // Set the remain players to be runners.
        getPlayersWithRole(Role.UNASSIGNED, players)
                .forEach(player -> player.setRole(Role.RUNNER));
    }

    // Filters players based on their role and returns a list of all players with the matching role.
    public static Collection<TagPlayer> getPlayersWithRole(Role role, Collection<TagPlayer> players) {
        return players.stream()
                .filter(x -> x.getRole() == role)
                .toList();
    }

    public static void sendRoleStatusNotification(TagPlayer player) {
        var roleMessage = player.getRole().getColor() + player.getRole().getName();
        player.getBukkitPlayer().sendTitle(roleMessage, null, 20, 30, 20);
    }
}
