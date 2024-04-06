package me.kap.gfw.game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Supplier;

public class GameComponentConfigurationBuilder {
    private final Collection<StateChangeCondition> startConditions = new ArrayList<>();
    private final Collection<StateChangeCondition> endConditions = new ArrayList<>();

    /**
     * Configure a condition which needs to be met before the component can allow the game to start.
     *
     * @param condition The condition which needs to be met.
     */
    public GameComponentConfigurationBuilder addStartCondition(Supplier<Boolean> condition) {
        return addStartCondition(condition, "Unknown reason");
    }

    /**
     * Configure a condition which needs to be met before the component can allow the game to start.
     *
     * @param condition    The condition which needs to be met.
     * @param errorMessage The reason why the condition would have failed if the condition is not met.
     */
    public GameComponentConfigurationBuilder addStartCondition(Supplier<Boolean> condition, String errorMessage) {
        startConditions.add(new StateChangeCondition(condition, errorMessage));
        return this;
    }

    /**
     * Configure a condition which needs to be met before the component can allow the game to start.
     *
     * @param condition            The condition which needs to be met.
     * @param errorMessageSupplier The reason why the condition would have failed if the condition is not met.
     */
    public GameComponentConfigurationBuilder addStartCondition(Supplier<Boolean> condition, Supplier<String> errorMessageSupplier) {
        startConditions.add(new StateChangeCondition(condition, errorMessageSupplier));
        return this;
    }

    /**
     * Configure a condition which needs to be met before the component can allow the game to end.
     *
     * @param condition The condition which needs to be met.
     */
    public GameComponentConfigurationBuilder addEndCondition(Supplier<Boolean> condition) {
        return addEndCondition(condition, "Unknown reason");
    }

    /**
     * Configure a condition which needs to be met before the component can allow the game to end.
     *
     * @param condition    The condition which needs to be met.
     * @param errorMessage The reason why the condition would have failed if the condition is not met.
     */
    public GameComponentConfigurationBuilder addEndCondition(Supplier<Boolean> condition, String errorMessage) {
        endConditions.add(new StateChangeCondition(condition, errorMessage));
        return this;
    }

    /**
     * Configure a condition which needs to be met before the component can allow the game to end.
     *
     * @param condition            The condition which needs to be met.
     * @param errorMessageSupplier The reason why the condition would have failed if the condition is not met.
     */
    public GameComponentConfigurationBuilder addEndCondition(Supplier<Boolean> condition, Supplier<String> errorMessageSupplier) {
        endConditions.add(new StateChangeCondition(condition, errorMessageSupplier));
        return this;
    }

    /**
     * @return A {@link GameComponentConfiguration} with the values configured through this builder.
     */
    GameComponentConfiguration build() {
        return new GameComponentConfiguration(startConditions, endConditions);
    }
}
