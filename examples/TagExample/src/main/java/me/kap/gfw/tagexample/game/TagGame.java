package me.kap.gfw.tagexample.game;

import me.kap.gfw.arena.ArenaComponent;
import me.kap.gfw.events.timing.TimerComponent;
import me.kap.gfw.game.Game;
import me.kap.gfw.game.exceptions.GameStateChangeException;
import me.kap.gfw.player.PlayerManager;
import me.kap.gfw.tagexample.game.components.PlayerSpawnComponent;
import me.kap.gfw.tagexample.game.components.RoleComponent;
import me.kap.gfw.tagexample.game.components.TagHandlerComponent;
import me.kap.gfw.tagexample.player.TagPlayer;
import me.kap.gfw.tagexample.player.TagPlayerFactory;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.plugin.Plugin;

import java.time.Duration;
import java.util.logging.Logger;

public class TagGame extends Game {
    private final PlayerManager<TagPlayer> playerManager = new PlayerManager<>(new TagPlayerFactory());
    private final Logger logger;
    private ScoreTracker scoreTracker;

    public TagGame(Plugin plugin, Logger logger) {
        this.logger = logger;

        // Instantiate components.
        var timerComponent = new TimerComponent(plugin);
        var arenaComponent = new ArenaComponent();
        var playerSpawnComponent = new PlayerSpawnComponent(arenaComponent, getPlayerManager());
        var roleComponent = new RoleComponent(getPlayerManager());
        var tagHandlerComponent = new TagHandlerComponent(getPlayerManager());

        // Register components.
        getComponentManager().addComponents(timerComponent, arenaComponent, playerSpawnComponent, roleComponent, tagHandlerComponent);

        registerPlayerManagerMessages();
    }

    @Override
    protected void onStart() {
        // Create a new score tracker for this game.
        scoreTracker = new ScoreTracker();

        // Schedule the game to end after five minutes.
        var timerComponent = getComponentManager().getComponent(TimerComponent.class);
        timerComponent.getEventTimer().scheduleEvent(Duration.ofMinutes(5), () -> {
            try {
                // end() may throw a GameStateChangeException when the game's components are not in a valid state to end.
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
            PlayerStateHelper.applyImmunity(tagger, timerComponent, Duration.ofSeconds(5), getPlayerManager().getAllPlayers());
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

    private void registerPlayerManagerMessages() {
        // Broadcast a message when a player was added.
        getPlayerManager().addPlayerAddCallback(addedPlayer -> {
            var playerAddedMessage = new ComponentBuilder()
                    .append(addedPlayer.getBukkitPlayer().getName()).color(ChatColor.AQUA)
                    .append(" was added to the tag game.").color(ChatColor.GREEN)
                    .create();

            getPlayerManager().getAllPlayers().forEach(player -> player.getBukkitPlayer().spigot().sendMessage(playerAddedMessage));
        });

        // Broadcast a message when a player was removed.
        getPlayerManager().addPlayerRemoveCallback(removedPlayer -> {
            var playerRemovedMessage = new ComponentBuilder()
                    .append(removedPlayer.getBukkitPlayer().getName()).color(ChatColor.AQUA)
                    .append(" was removed from the tag game.").color(ChatColor.RED)
                    .create();

            getPlayerManager().getAllPlayers().forEach(player -> player.getBukkitPlayer().spigot().sendMessage(playerRemovedMessage));
        });
    }

    public PlayerManager<TagPlayer> getPlayerManager() {
        return playerManager;
    }

    private ScoreTracker getScoreTracker() {
        return scoreTracker;
    }
}
