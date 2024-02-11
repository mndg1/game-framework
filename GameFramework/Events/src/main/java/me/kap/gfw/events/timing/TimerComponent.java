package me.kap.gfw.events.timing;

import me.kap.gfw.game.GameComponent;
import org.bukkit.plugin.Plugin;

/**
 * A {@link GameComponent} which adds functionality for scheduling events.
 */
public class TimerComponent extends GameComponent {
    private final Plugin plugin;
    private final EventTimer eventTimer;

    public  TimerComponent(Plugin plugin) {
        this(plugin, new EventTimer());
    }

    public TimerComponent(Plugin plugin, EventTimer eventTimer) {
        this.plugin = plugin;
        this.eventTimer = eventTimer;
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
