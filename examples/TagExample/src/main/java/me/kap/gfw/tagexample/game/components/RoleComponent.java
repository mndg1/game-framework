package me.kap.gfw.tagexample.game.components;

import me.kap.gfw.game.GameComponent;
import me.kap.gfw.player.PlayerManager;
import me.kap.gfw.tagexample.player.Role;
import me.kap.gfw.tagexample.player.TagPlayer;

import java.util.*;

public class RoleComponent extends GameComponent {
    private final PlayerManager<TagPlayer> playerManager;
    private final int taggerAmount;
    private final Random random = new Random();

    public RoleComponent(PlayerManager<TagPlayer> playerManager) {
        this(playerManager, 1);
    }

    public RoleComponent(PlayerManager<TagPlayer> playerManager, int taggerAmount) {
        this.playerManager = playerManager;
        this.taggerAmount = taggerAmount;
    }

    @Override
    public void start() {
        // Pick taggers
        for (var i = 0; i < taggerAmount; i++) {
            var unassignedPlayers = getPlayersWithRole(Role.UNASSIGNED).stream().toList();
            var playerIndex = random.nextInt(unassignedPlayers.size());
            var chosenTagger = unassignedPlayers.get(playerIndex);

            chosenTagger.setRole(Role.TAGGER);
            sendRoleStatusNotification(chosenTagger);
        }

        // Assign runner to remaining players
        getPlayersWithRole(Role.UNASSIGNED)
                .forEach(player -> {
                    player.setRole(Role.RUNNER);
                    sendRoleStatusNotification(player);
                });
    }

    // Filters players based on their role and returns a list of all players with the matching role.
    public Collection<TagPlayer> getPlayersWithRole(Role role) {
        return playerManager.getAllPlayers().stream()
                .filter(x -> x.getRole() == role)
                .toList();
    }

    public static void sendRoleStatusNotification(TagPlayer player) {
        var roleMessage = player.getRole().getColor() + player.getRole().getName();
        player.getBukkitPlayer().sendTitle(roleMessage, null, 20, 30, 20);
    }
}
