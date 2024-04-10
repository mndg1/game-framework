package me.kap.gfw.tagexample.game;

import me.kap.gfw.arena.ArenaComponent;
import me.kap.gfw.events.timing.TimerComponent;
import me.kap.gfw.game.Game;
import me.kap.gfw.game.exceptions.GameStateChangeException;
import me.kap.gfw.player.PlayerManager;
import me.kap.gfw.tagexample.game.components.PlayerSpawnComponent;
import me.kap.gfw.tagexample.game.components.RoleComponent;
import me.kap.gfw.tagexample.game.components.TagHandlerComponent;
import me.kap.gfw.tagexample.player.Role;
import me.kap.gfw.tagexample.player.TagPlayer;
import me.kap.gfw.tagexample.player.TagPlayerFactory;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.plugin.Plugin;

import java.time.Duration;
import java.util.logging.Logger;

public class TagGame extends Game {
    private final PlayerManager<TagPlayer> playerManager = new PlayerManager<>(new TagPlayerFactory());
    private final Logger logger;
    private ScoreTracker scoreTracker;

    public TagGame(Plugin plugin, Logger logger) {
        this.logger = logger;

        getComponentManager().addComponents(
                new TimerComponent(plugin),
                new ArenaComponent(),
                new PlayerSpawnComponent(
                        getComponentManager().getComponent(ArenaComponent.class),
                        getPlayerManager()),
                new RoleComponent(getPlayerManager()),
                new TagHandlerComponent(getPlayerManager())
        );
    }

    @Override
    protected void onStart() {
        // Create a new score tracker for this game.
        scoreTracker = new ScoreTracker();

        // Schedule the game to end after five minutes.
        var timerComponent = getComponentManager().getComponent(TimerComponent.class);
        timerComponent.getEventTimer().scheduleEvent(Duration.ofMinutes(5), () -> {
            try {
                // end() may throw a GameStateChangeException when the game is not in a valid state to end.
                end();
                getPlayerManager().getAllPlayers()
                        .forEach(player -> player.getBukkitPlayer().sendMessage(ChatColor.BLUE + "The game reached its time limit"));
            } catch (GameStateChangeException stateChangeException) {
                logger.warning(stateChangeException.getMessage());
            }
        });

        registerOnTagEvents();

        getPlayerManager().getAllPlayers()
                .forEach(player -> player.getBukkitPlayer().sendMessage(ChatColor.GREEN + "The game has started!"));
    }

    @Override
    protected void onEnd() {
        // Reset the roles.
        playerManager.getAllPlayers().forEach(player -> player.setRole(Role.UNASSIGNED));

        getPlayerManager().getAllPlayers()
                .forEach(player -> player.getBukkitPlayer().sendMessage(ChatColor.RED + "The game has ended!"));

        // Broadcast the final scores to all players.
        getPlayerManager().getAllPlayers()
                .forEach(player -> player.getBukkitPlayer().sendMessage(ChatColor.AQUA + "Final scores:"));
        getPlayerManager().getAllPlayers()
                .forEach(player -> player.getBukkitPlayer().spigot().sendMessage(getScoreTracker().getPointsAnnouncementMessage()));
    }

    private void registerOnTagEvents() {
        var tagHandlerComponent = getComponentManager().getComponent(TagHandlerComponent.class);

        // Apply five seconds of immunity to the tagger when a tagger tags a runner.
        tagHandlerComponent.addOnTagListener(((tagger, tagged) -> {
            var timerComponent = getComponentManager().getComponent(TimerComponent.class);
            PlayerStateHelper.applyImmunity(tagger, timerComponent, Duration.ofSeconds(5));

            // Broadcast a message about the immunity ending.
            getPlayerManager().getAllPlayers()
                    .forEach(player -> player.getBukkitPlayer().spigot().sendMessage(PlayerStateHelper.getImmunityEndedMessage(tagger)));
        }));

        // When a player gets tagged, all players except the tagged player earn a point.
        tagHandlerComponent.addOnTagListener(((tagger, tagged) -> {
            playerManager.getAllPlayers().stream()
                    // Tagged player does not earn points.
                    .filter(player -> player != tagged)
                    // All other players do earn a point.
                    .forEach(player -> getScoreTracker().givePoints(player.getBukkitPlayer().getName(), 1));

            // Broadcast the current points to all players.
            getPlayerManager().getAllPlayers()
                    .forEach(player -> player.getBukkitPlayer().spigot().sendMessage(getScoreTracker().getPointsAnnouncementMessage()));
        }));
    }

    public PlayerManager<TagPlayer> getPlayerManager() {
        return playerManager;
    }

    private ScoreTracker getScoreTracker() {
        return scoreTracker;
    }
}
