package me.kap.gfw.game;

import java.util.Collection;
import java.util.Collections;

record GameComponentConfiguration(
        Collection<StateChangeCondition> startConditions,
        Collection<StateChangeCondition> endConditions) {

    /**
     * Gets the conditions that are associated with the given state.
     * The returned conditions must all be met before the component can move on to the next state.
     *
     * @param state The state which the conditions are associated with.
     * @return The conditions associated with the given state.
     */
    Collection<StateChangeCondition> getConditionsForStateChange(GameState state) {
        return switch (state) {
            case STARTING -> startConditions;
            case ENDING -> endConditions;
            default -> Collections.emptyList();
        };
    }
}
