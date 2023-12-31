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
import java.util.concurrent.TimeUnit;

public class TagGame extends Game {
    private final PlayerManager<TagPlayer> playerManager = new PlayerManager<>(new TagPlayerFactory());
    private final Announcer announcer = new Announcer(playerManager);
    private ScoreTracker scoreTracker;

    public TagGame(String gameName) {
        super(gameName);
    }

    @Override
    protected void setup() {
        // Create a new score tracker for this game.
        scoreTracker = new ScoreTracker();

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

        // Schedule the game to end after five minutes.
        long endTime = new Date().getTime() + TimeUnit.MINUTES.toMillis(5);
        TimerComponent timerComponent = getComponentManager().getComponent(TimerComponent.class);
        timerComponent.getEventTimer().scheduleSingleEvent(endTime, () -> {
            end();
            getAnnouncer().broadcast(ChatColor.BLUE + "The game has ended due to reaching the time limit");
        });
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
        applyImmunity(tagger, TimeUnit.SECONDS.toMillis(5));

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

        // Assign points
        for (TagPlayer player : playerManager.getAllPlayers()) {
            if (player == runner) {
                // Tagged player does not get points
                continue;
            }

            // Give player a point
            scoreTracker.givePoints(player.getBukkitPlayer().getName(), 1);

            // Announce score
            int currentScore = scoreTracker.getPoints(player.getBukkitPlayer().getName());
            BaseComponent[] playerScoreMessage = new ComponentBuilder()
                    .append(player.getBukkitPlayer().getDisplayName()).color(ChatColor.AQUA)
                    .append(" has ").color(ChatColor.YELLOW)
                    .append(Integer.toString(currentScore)).color(ChatColor.DARK_PURPLE)
                    .append(" points.").color(ChatColor.YELLOW)
                    .create();
            announcer.broadcast(playerScoreMessage);
        }
    }

    private void applyImmunity(TagPlayer player, long duration) {
        player.setState(State.IMMUNE);
        long executionTime = new Date().getTime() + duration;
        TimerComponent timer = getComponentManager().getComponent(TimerComponent.class);

        // Schedule the end of the immunity.
        timer.getEventTimer().scheduleSingleEvent(executionTime, () -> {
            // Update state
            player.setState(State.VULNERABLE);

            // Send a message to all players that immunity has ended.
            BaseComponent[] immunityEndedMessage = new ComponentBuilder()
                    .append("Immunity ended for ").color(ChatColor.YELLOW)
                    .append(player.getBukkitPlayer().getDisplayName()).color(player.getRole().getColor())
                    .append(".").color(ChatColor.YELLOW)
                    .create();
            announcer.broadcast(immunityEndedMessage);
        });
    }

    public PlayerManager<TagPlayer> getPlayerManager() {
        return playerManager;
    }

    public Announcer getAnnouncer() {
        return announcer;
    }
}
