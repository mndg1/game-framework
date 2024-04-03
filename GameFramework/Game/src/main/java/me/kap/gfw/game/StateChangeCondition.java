package me.kap.gfw.game;

import java.util.function.Supplier;

public record StateChangeCondition(Supplier<Boolean> condition, Supplier<String> errorMessageSupplier) {

    public StateChangeCondition(Supplier<Boolean> condition, String errorMessage) {
        this(condition, () -> errorMessage);
    }
}
