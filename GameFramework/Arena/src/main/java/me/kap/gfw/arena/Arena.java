package me.kap.gfw.arena;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Arena {
    private final Map<String, ArenaLocation> arenaLocations = new HashMap<>();

    public Set<ArenaLocation> getAllLocations() {
        return new HashSet<>(arenaLocations.values());
    }
}
