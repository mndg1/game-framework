package me.kap.gfw.game;

import java.util.Collection;
import java.util.Collections;

public record GameComponentConfiguration(
        Collection<StateChangeCondition> startConditions,
        Collection<StateChangeCondition> endConditions) {

    Collection<StateChangeCondition> getConditionsForStateChange(GameState state) {
        return switch (state) {
            case STARTING -> startConditions;
            case ENDING -> endConditions;
            default -> Collections.emptyList();
        };
    }
}
