package me.kap.gfw.game;

import me.kap.gfw.game.exceptions.GameStateChangeException;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StateChangeValidatorTest {
    private final Collection<GameComponent> componentFakes;

    StateChangeValidatorTest() throws GameStateChangeException {
        var exceptionA = new GameStateChangeException("Error 1");
        var exceptionB = new GameStateChangeException("Error 2");

        var componentFakeA = mock(GameComponent.class);
        var componentFakeB = mock(GameComponent.class);

        doThrow(exceptionA).when(componentFakeA).validateStart();
        doThrow(exceptionB).when(componentFakeB).validateStart();

        doThrow(exceptionA).when(componentFakeA).validateEnd();
        doThrow(exceptionB).when(componentFakeB).validateEnd();

        componentFakes = List.of(componentFakeA, componentFakeB);
    }

    @Test
    void whenValidateComponentStart_withMultipleExceptions_thenAllExceptionsAreAggregated() {
        // arrange
        var caughtMessage = "";

        // act
        try {
            StateChangeValidator.validateComponentStart(componentFakes);
        } catch (GameStateChangeException stateChangeException) {
            caughtMessage = stateChangeException.getMessage();
        }

        // assert
        var expectedMessage = "Failed to start game: Error 1; Error 2";
        assertEquals(expectedMessage, caughtMessage);
    }

    @Test
    void whenValidateComponentEnd_withMultipleExceptions_thenAllExceptionsAreAggregated() {
        // arrange
        var caughtMessage = "";

        // act
        try {
            StateChangeValidator.validateComponentEnd(componentFakes);
        } catch (GameStateChangeException stateChangeException) {
            caughtMessage = stateChangeException.getMessage();
        }

        // assert
        var expectedMessage = "Failed to end game: Error 1; Error 2";
        assertEquals(expectedMessage, caughtMessage);
    }
}