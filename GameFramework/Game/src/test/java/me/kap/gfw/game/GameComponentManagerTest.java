package me.kap.gfw.game;

import me.kap.gfw.game.exceptions.GameStateChangeException;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameComponentManagerTest {
    private final GameComponentManager componentManager;
    private final GameComponent passingComponentFake;
    private final GameComponent failingComponentFake;

    GameComponentManagerTest() {
        passingComponentFake = mock(GameComponent.class);
        failingComponentFake = mock(GameComponent.class);
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
    void whenStart_withPassingComponent_thenStartsSuccessfully() {
        // arrange
        componentManager.addComponent(passingComponentFake);

        // assert
        assertDoesNotThrow(componentManager::start);
    }

    @Test
    void whenEnd_withPassingComponent_thenStartsSuccessfully() {
        // arrange
        componentManager.addComponent(passingComponentFake);

        // assert
        assertDoesNotThrow(componentManager::end);
    }

    @Test
    void whenStart_withFailingComponent_thenExceptionIsThrown() throws GameStateChangeException {
        // arrange
        doThrow(GameStateChangeException.class).when(failingComponentFake).end();
        componentManager.addComponent(failingComponentFake);

        // assert
        assertThrows(GameStateChangeException.class, componentManager::end);
    }

    @Test
    void whenEnd_withFailingComponent_thenExceptionIsThrown() throws GameStateChangeException {
        // arrange
        doThrow(GameStateChangeException.class).when(failingComponentFake).end();
        componentManager.addComponent(failingComponentFake);

        // assert
        assertThrows(GameStateChangeException.class, componentManager::end);
    }

    private static class ComponentFakeA extends GameComponent {

    }

    private static class ComponentFakeB extends GameComponent {

    }
}