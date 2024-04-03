package me.kap.gfw.game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Supplier;

public class GameComponentConfigurationBuilder {
    private final Collection<StateChangeCondition> startConditions = new ArrayList<>();
    private final Collection<StateChangeCondition> endConditions = new ArrayList<>();

    public GameComponentConfigurationBuilder addStartCondition(Supplier<Boolean> condition) {
        return addStartCondition(condition, "Unknown reason");
    }

    public GameComponentConfigurationBuilder addStartCondition(Supplier<Boolean> condition, String errorMessage) {
        startConditions.add(new StateChangeCondition(condition, errorMessage));
        return this;
    }

    public GameComponentConfigurationBuilder addStartCondition(Supplier<Boolean> condition, Supplier<String> errorMessageSupplier) {
        startConditions.add(new StateChangeCondition(condition, errorMessageSupplier));
        return this;
    }

    public GameComponentConfigurationBuilder addEndCondition(Supplier<Boolean> condition) {
        return addEndCondition(condition, "Unknown reason");
    }

    public GameComponentConfigurationBuilder addEndCondition(Supplier<Boolean> condition, String errorMessage) {
        endConditions.add(new StateChangeCondition(condition, errorMessage));
        return this;
    }

    public GameComponentConfigurationBuilder addEndCondition(Supplier<Boolean> condition, Supplier<String> errorMessageSupplier) {
        endConditions.add(new StateChangeCondition(condition, errorMessageSupplier));
        return this;
    }

    GameComponentConfiguration build() {
        return new GameComponentConfiguration(startConditions, endConditions);
    }
}
