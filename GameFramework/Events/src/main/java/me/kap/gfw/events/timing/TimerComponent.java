package me.kap.gfw.events.timing;

import me.kap.gfw.game.GameComponent;
import org.bukkit.plugin.Plugin;

/**
 * A {@link GameComponent} which adds functionality for scheduling events.
 */
public class TimerComponent extends GameComponent {
    private final Plugin plugin;
    private final EventTimer eventTimer = new EventTimer();

    public TimerComponent(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void start() {
        eventTimer.startTimer(plugin);
    }

    @Override
    public void end() {
        eventTimer.stopTimer();
    }

    public EventTimer getEventTimer() {
        return eventTimer;
    }
}
