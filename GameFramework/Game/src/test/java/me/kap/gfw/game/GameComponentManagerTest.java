package me.kap.gfw.game;

import me.kap.gfw.game.exceptions.GameStateChangeException;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameComponentManagerTest {
    private final GameComponentManager componentManager;
    private final GameComponent passingComponentFake;
    private final GameComponent failingComponentFake;

    GameComponentManagerTest() {
        var passingComponentConfiguration = new GameComponentConfiguration(
                List.of(new StateChangeCondition(() -> true, "Unknown reason")),
                List.of(new StateChangeCondition(() -> true, "Unknown reason"))
        );

        var failingComponentConfiguration = new GameComponentConfiguration(
                List.of(new StateChangeCondition(() -> false, "Unknown reason")),
                List.of(new StateChangeCondition(() -> false, "Unknown reason"))
        );

        passingComponentFake = mock(GameComponent.class);
        when(passingComponentFake.getConfiguration())
                .thenReturn(passingComponentConfiguration);

        failingComponentFake = mock(GameComponent.class);
        when(failingComponentFake.getConfiguration())
                .thenReturn(failingComponentConfiguration);

        componentManager = new GameComponentManager();
    }

    @Test
    void whenAddComponent_withNewComponent_thenComponentIsSame() {
        // arrange
        var newComponent = mock(GameComponent.class);

        // act
        componentManager.addComponent(newComponent);

        // assert
        var actualComponent = componentManager.getComponent(newComponent.getClass());

        assertSame(newComponent, actualComponent);
    }

    @Test
    void whenAddComponents_withCollection_thenAllComponentsAreAdded() {
        // arrange
        var componentsToAdd = Arrays.asList(
                new ComponentFakeA(),
                new ComponentFakeB()
        );

        // act
        componentManager.addComponents(componentsToAdd);

        // assert
        assertAll(
                () -> assertNotNull(componentManager.getComponent(ComponentFakeA.class)),
                () -> assertNotNull(componentManager.getComponent(ComponentFakeB.class))
        );
    }

    @Test
    void whenAddComponents_withVarargs_thenAllComponentsAreAdded() {
        // act
        componentManager.addComponents(
                new ComponentFakeA(),
                new ComponentFakeB()
        );

        // assert
        assertAll(
                () -> assertNotNull(componentManager.getComponent(ComponentFakeA.class)),
                () -> assertNotNull(componentManager.getComponent(ComponentFakeB.class))
        );
    }

    @Test
    void whenPerformStartingState_withPassingComponent_thenStartsSuccessfully() {
        // arrange
        componentManager.addComponent(passingComponentFake);

        // assert
        assertDoesNotThrow(() -> componentManager.performStateChange(GameState.STARTING));
    }

    @Test
    void whenPerformEndingState_withPassingComponent_thenStartsSuccessfully() {
        // arrange
        componentManager.addComponent(passingComponentFake);

        // assert
        assertDoesNotThrow(() -> componentManager.performStateChange(GameState.ENDING));
    }

    @Test
    void whenPerformStartingState_withFailingComponent_thenExceptionIsThrown() {
        // arrange
        componentManager.addComponent(failingComponentFake);

        // assert
        assertThrows(GameStateChangeException.class, () -> componentManager.performStateChange(GameState.STARTING));
    }

    @Test
    void whenPerformEndingState_withFailingComponent_thenExceptionIsThrown() {
        // arrange
        componentManager.addComponent(failingComponentFake);

        // assert
        assertThrows(GameStateChangeException.class, () -> componentManager.performStateChange(GameState.ENDING));
    }

    private static class ComponentFakeA extends GameComponent {

    }

    private static class ComponentFakeB extends GameComponent {

    }
}