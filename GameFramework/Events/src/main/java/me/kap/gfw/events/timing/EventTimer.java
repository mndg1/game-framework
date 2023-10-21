package me.kap.gfw.events.timing;

import me.kap.gfw.events.timing.comparators.TimedEventComparator;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

/**
 * A class that is responsible for scheduling {@link TimedEventAction}s.
 */
public class EventTimer {
    private final Queue<TimedEventAction> actions = new PriorityQueue<>(new TimedEventComparator());

    private BukkitRunnable timer;

    /**
     * Starts the timer.
     *
     * @param plugin The {@link Plugin} that runs the timer.
     */
    public void startTimer(Plugin plugin) {
        timer = new BukkitRunnable() {
            @Override
            public void run() {
                processActions();
            }
        };

        timer.runTaskTimer(plugin, 0, 20);
    }

    /**
     * Stops the timer.
     */
    public void stopTimer() {
        timer.cancel();
    }

    /**
     * Adds a {@link TimedEventAction} to the timer.
     *
     * @param action The {@link TimedEventAction} to schedule.
     */
    public void addAction(TimedEventAction action) {
        actions.offer(action);
    }

    /**
     * Executes the scheduled {@link TimedEventAction}s.
     */
    private void processActions() {
        Date now = new Date();

        Collection<TimedEventAction> actionsToExecute = pollActionsToExecute(now.getTime());

        for (TimedEventAction action : actionsToExecute) {
            action.execute();

            // Repeating actions will have updated their execution time.
            if (action.getNextExecutionTime() > now.getTime()) {
                actions.offer(action);
            }
        }
    }

    /**
     * Retrieves and removes all {@link TimedEventAction} instances which should be executed at the given time.
     *
     * @param timeNow The current epoch time.
     * @return A collection of {@link TimedEventAction}s which should be executed.
     */
    private Collection<TimedEventAction> pollActionsToExecute(long timeNow) {
        Collection<TimedEventAction> actionsToExecute = new ArrayList<>();

        while (!actions.isEmpty()) {
            if (actions.peek().getNextExecutionTime() > timeNow) {
                break;
            }

            actionsToExecute.add(actions.poll());
        }

        return actionsToExecute;
    }
}
