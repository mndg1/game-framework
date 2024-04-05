package me.kap.gfw.game;

import java.util.function.Supplier;

/**
 * Represents a condition that must be met before the game can be advanced to the next state.
 * @param condition The condition which needs to be met.
 *                  A condition is met when it evaluates to 'true'.
 * @param errorMessageSupplier The reason why a condition was not met, if that is the case.
 */
record StateChangeCondition(Supplier<Boolean> condition, Supplier<String> errorMessageSupplier) {

    /**
     * Represents a condition that must be met before the game can be advanced to the next state.
     * @param condition The condition which needs to be met.
     *                  A condition is met when it evaluates to 'true'.
     * @param errorMessage The reason why a condition was not met, if that is the case.
     */
    public StateChangeCondition(Supplier<Boolean> condition, String errorMessage) {
        this(condition, () -> errorMessage);
    }
}
