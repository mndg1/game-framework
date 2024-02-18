package me.kap.gfw.tagexample.game;

import me.kap.gfw.arena.ArenaComponent;
import me.kap.gfw.events.timing.TimerComponent;
import me.kap.gfw.game.Game;
import me.kap.gfw.game.exceptions.GameStateChangeException;
import me.kap.gfw.player.PlayerManager;
import me.kap.gfw.tagexample.player.Role;
import me.kap.gfw.tagexample.player.State;
import me.kap.gfw.tagexample.player.TagPlayer;
import me.kap.gfw.tagexample.player.TagPlayerFactory;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;

import java.time.Duration;
import java.util.logging.Logger;

public class TagGame extends Game {
    private final PlayerManager<TagPlayer> playerManager = new PlayerManager<>(new TagPlayerFactory());
    private final Announcer announcer = new Announcer(playerManager);
    private final Logger logger;
    private ScoreTracker scoreTracker;

    public TagGame(Logger logger) {
        this.logger = logger;
    }

    @Override
    protected void onStart() {
        // Create a new score tracker for this game.
        scoreTracker = new ScoreTracker();

        // Teleport players to the arena.
        var arenaComponent = getComponentManager().getComponent(ArenaComponent.class);
        LocationHelper.teleportPlayersToArena(arenaComponent.getArena(), playerManager.getAllPlayers());

        // Assign roles to players.
        RoleHelper.assignRandomRoles(1, playerManager.getAllPlayers());

        playerManager.getAllPlayers().forEach(player -> {
            // Make sure the player is vulnerable.
            player.setState(State.VULNERABLE);
            // Display the role to the player.
            RoleHelper.sendRoleStatusNotification(player);
        });

        // Schedule the game to end after five minutes.
        var timerComponent = getComponentManager().getComponent(TimerComponent.class);
        timerComponent.getEventTimer().scheduleEvent(Duration.ofMinutes(5), () -> {
            try {
                // end() may throw a GameStateChangeException when the game is not in a valid state to end.
                end();
                getAnnouncer().broadcast(ChatColor.BLUE + "The game reached its time limit");
            } catch (GameStateChangeException stateChangeException) {
                logger.warning(stateChangeException.getMessage());
            }
        });

        getAnnouncer().broadcast(ChatColor.GREEN + "The game has started!");
    }

    @Override
    protected void onEnd() {
        // Reset the roles.
        playerManager.getAllPlayers().forEach(player -> player.setRole(Role.UNASSIGNED));

        getAnnouncer().broadcast(ChatColor.RED + "The game has ended!");
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
        applyImmunity(tagger, Duration.ofSeconds(5));

        // Send a message about the event to all players.
        var tagOccurredMessage = new ComponentBuilder()
                .append(tagger.getBukkitPlayer().getDisplayName()).color(tagger.getRole().getColor())
                .append(" has tagged ").color(ChatColor.YELLOW)
                .append(runner.getBukkitPlayer().getDisplayName()).color(runner.getRole().getColor())
                .append(".").color(ChatColor.YELLOW)
                .create();
        announcer.broadcast(tagOccurredMessage);

        // Set new roles.
        tagger.setRole(Role.RUNNER);
        runner.setRole(Role.TAGGER);

        playerManager.getAllPlayers().stream()
                // Tagged player does not earn points.
                .filter(player -> player != runner)
                // All other players do earn a point.
                .forEach(player -> scoreTracker.givePoints(player.getBukkitPlayer().getName(), 1));

        scoreTracker.broadcastPoints(announcer, playerManager.getAllPlayers());
    }

    private void applyImmunity(TagPlayer player, Duration duration) {
        player.setState(State.IMMUNE);
        var timer = getComponentManager().getComponent(TimerComponent.class);

        // Schedule the end of the immunity.
        timer.getEventTimer().scheduleEvent(duration, () -> {
            // Update state
            player.setState(State.VULNERABLE);

            // Send a message to all players that immunity has ended.
            var immunityEndedMessage = new ComponentBuilder()
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
