package me.kap.gfw.game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameComponentManagerTest {
    private final GameComponentManager componentManager;
    private final GameComponent passingComponentFake;
    private final GameComponent failingComponentFake;

    GameComponentManagerTest() {
        passingComponentFake = mock(GameComponent.class);
        when(passingComponentFake.start()).thenReturn(true);
        when(passingComponentFake.end()).thenReturn(true);

        failingComponentFake = mock(GameComponent.class);
        when(failingComponentFake.start()).thenReturn(false);
        when(failingComponentFake.end()).thenReturn(false);

        componentManager = new GameComponentManager();
    }

    @Test
    void whenAddComponent_withNewComponent_thenComponentIsSame() {
        // arrange
        GameComponent newComponent = mock(GameComponent.class);

        // act
        componentManager.addComponent(newComponent);

        // assert
        GameComponent actualComponent = componentManager.getComponent(newComponent.getClass());

        assertSame(newComponent, actualComponent);
    }

    @Test
    void whenStart_withPassingComponent_thenStartsSuccessfully() {
        // arrange
        componentManager.addComponent(passingComponentFake);

        // act
        boolean startResult = componentManager.start();

        // assert
        assertTrue(startResult);
    }

    @Test
    void whenStart_withFailingComponent_thenStartsUnsuccessfully() {
        // arrange
        componentManager.addComponent(failingComponentFake);

        // act
        boolean startResult = componentManager.start();

        // assert
        assertFalse(startResult);
    }
}