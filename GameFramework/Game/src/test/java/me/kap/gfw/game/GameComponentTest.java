package me.kap.gfw.game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameComponentTest {

    @Test
    void whenConfigure_withStartConditions_thenConditionsShouldBeConfigured() {
        // act
        var componentFake = new GameComponent() {
            @Override
            public void configure(GameComponentConfigurationBuilder configurationBuilder) {
                configurationBuilder.addStartCondition(() -> true);
            }
        };

        // assert
        assertEquals(1, componentFake.getConfiguration().startConditions().size());
    }
}
