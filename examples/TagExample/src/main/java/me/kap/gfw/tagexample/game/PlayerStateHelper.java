package me.kap.gfw.tagexample.game;

import me.kap.gfw.events.timing.TimerComponent;
import me.kap.gfw.tagexample.player.State;
import me.kap.gfw.tagexample.player.TagPlayer;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;

import java.time.Duration;

public class PlayerStateHelper {

    private PlayerStateHelper() {
        // Prevent instantiation of static helper class.
    }

    public static void applyImmunity(TagPlayer player, TimerComponent timerComponent, Duration duration) {
        // Set player to be immune
        player.setState(State.IMMUNE);

        // Schedule the role to be updated back to vulnerable.
        timerComponent.getEventTimer().scheduleEvent(duration, () -> player.setState(State.VULNERABLE));
    }

    public static BaseComponent[] getImmunityEndedMessage(TagPlayer player) {
        return new ComponentBuilder()
                .append("Immunity ended for ").color(ChatColor.YELLOW)
                .append(player.getBukkitPlayer().getDisplayName()).color(player.getRole().getColor())
                .append(".").color(ChatColor.YELLOW)
                .create();
    }
}
