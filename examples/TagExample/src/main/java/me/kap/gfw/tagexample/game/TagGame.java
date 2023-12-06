package me.kap.gfw.tagexample.game;

import me.kap.gfw.arena.ArenaComponent;
import me.kap.gfw.events.timing.TimerComponent;
import me.kap.gfw.game.Game;
import me.kap.gfw.player.PlayerManager;
import me.kap.gfw.tagexample.player.Role;
import me.kap.gfw.tagexample.player.State;
import me.kap.gfw.tagexample.player.TagPlayer;
import me.kap.gfw.tagexample.player.TagPlayerFactory;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;

import java.util.Date;

public class TagGame extends Game {
    private final PlayerManager<TagPlayer> playerManager = new PlayerManager<>(new TagPlayerFactory());
    private final Announcer announcer = new Announcer(playerManager);

    public TagGame(String gameName) {
        super(gameName);
    }

    @Override
    protected void setup() {
        // Assign roles to players.
        RoleManager roleManager = new RoleManager();
        roleManager.assignRandomRoles(1, playerManager.getAllPlayers());

        for (TagPlayer player : playerManager.getAllPlayers()) {
            // Make sure the player is vulnerable.
            player.setState(State.VULNERABLE);

            // Display the role to the player.
            player.sendRoleStatusNotification();
        }

        // Teleport players to the arena.
        ArenaComponent arenaComponent = getComponentManager().getComponent(ArenaComponent.class);
        LocationManager locationManager = new LocationManager(arenaComponent.getArena());
        locationManager.teleportPlayersToArena(playerManager.getAllPlayers());
    }

    @Override
    protected void finish() {
        // Reset the roles
        for (TagPlayer player : playerManager.getAllPlayers()) {
            player.setRole(Role.UNASSIGNED);
        }
    }

    public void performTag(TagPlayer tagger, TagPlayer runner) {
        // Only taggers can actually tag people.
        if (tagger.getRole() != Role.TAGGER) {
            return;
        }

        // There are various reasons why the tagged player might not be able to be tagged.
        // For example:
        //  - The 'runner' themselves are a tagger.
        //  - The runner has immunity.
        if (!runner.isTaggable()) {
            return;
        }

        // Grant five seconds of immunity to the tagger.
        tagger.setState(State.IMMUNE);
        final int immunityDuration = 5_000;
        long executionTime = new Date().getTime() + immunityDuration;
        TimerComponent timer = getComponentManager().getComponent(TimerComponent.class);

        // Schedule the end of the immunity.
        timer.getEventTimer().scheduleSingleEvent(executionTime, () ->  {
            // Update state
            tagger.setState(State.VULNERABLE);

            // Send a message to all players that immunity has ended.
            BaseComponent[] immunityEndedMessage = new ComponentBuilder()
                    .append("Immunity ended for ").color(ChatColor.YELLOW)
                    .append(tagger.getBukkitPlayer().getDisplayName()).color(tagger.getRole().getColor())
                    .append(".").color(ChatColor.YELLOW)
                    .create();
            announcer.broadcast(immunityEndedMessage);
        });

        // Send a message about the event to all players.
        BaseComponent[] tagOccurredMessage = new ComponentBuilder()
                .append(tagger.getBukkitPlayer().getDisplayName()).color(tagger.getRole().getColor())
                .append(" has tagged ").color(ChatColor.YELLOW)
                .append(runner.getBukkitPlayer().getDisplayName()).color(runner.getRole().getColor())
                .append(".").color(ChatColor.YELLOW)
                .create();
        announcer.broadcast(tagOccurredMessage);

        // Set new roles.
        tagger.setRole(Role.RUNNER);
        runner.setRole(Role.TAGGER);
    }

    public PlayerManager<TagPlayer> getPlayerManager() {
        return playerManager;
    }

    public Announcer getAnnouncer() {
        return announcer;
    }
}
