package me.kap.gfw.events.timing.comparators;

import me.kap.gfw.events.timing.actions.TimedEventAction;

import java.util.Comparator;

public class TimedEventComparator implements Comparator<TimedEventAction> {

    @Override
    public int compare(TimedEventAction o1, TimedEventAction o2) {
        return (int) (o1.getNextExecutionTime() - o2.getNextExecutionTime());
    }
}
