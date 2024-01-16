package me.kap.gfw.events.timing;

import me.kap.gfw.game.GameComponent;
import org.bukkit.plugin.Plugin;

import java.time.Clock;

/**
 * A {@link GameComponent} which adds functionality for scheduling events.
 */
public class TimerComponent extends GameComponent {
    private final Plugin plugin;
    private final EventTimer eventTimer = new EventTimer();
    private final Clock clock;

    public TimerComponent(Plugin plugin) {
        this(plugin, Clock.systemUTC());
    }

    public TimerComponent(Plugin plugin, Clock clock) {
        this.plugin = plugin;
        this.clock = clock;
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

    public Clock getClock() {
        return clock;
    }
}
