package me.kap.gfw.tagexample.game;

import me.kap.gfw.events.timing.TimerComponent;
import me.kap.gfw.tagexample.player.State;
import me.kap.gfw.tagexample.player.TagPlayer;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;

import java.time.Duration;
import java.util.Collection;

public class PlayerStateHelper {

    private PlayerStateHelper() {
        // Prevent instantiation of static helper class.
    }

    public static void applyImmunity(TagPlayer target, TimerComponent timerComponent, Duration duration, Collection<TagPlayer> players) {
        // Set target to be immune
        target.setState(State.IMMUNE);

        // Schedule the role to be updated back to vulnerable.
        timerComponent.getEventTimer().scheduleEvent(duration, () -> {
            target.setState(State.VULNERABLE);

            // Broadcast a message when the player's immunity ended.
            players.forEach(player -> player.getBukkitPlayer().spigot().sendMessage(getImmunityEndedMessage(target)));
        });
    }

    private static BaseComponent[] getImmunityEndedMessage(TagPlayer player) {
        return new ComponentBuilder()
                .append("Immunity ended for ").color(ChatColor.YELLOW)
                .append(player.getBukkitPlayer().getDisplayName()).color(player.getRole().getColor())
                .append(".").color(ChatColor.YELLOW)
                .create();
    }
}
