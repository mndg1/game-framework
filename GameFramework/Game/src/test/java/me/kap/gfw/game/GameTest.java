package me.kap.gfw.game;

import me.kap.gfw.game.exceptions.GameStateChangeException;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class GameTest {
    private final Game game = new Game() {
    };

    @Test
    void whenStart_withNoExceptions_thenOnStartIsCalled() throws GameStateChangeException {
        // arrange
        var gameSpy = spy(game);

        // act
        gameSpy.start();

        // assert
        verify(gameSpy).onStart();
    }

    @Test
    void whenEnd_withNoExceptions_thenOnEndIsCalled() throws GameStateChangeException {
        var gameSpy = spy(game);

        // act
        gameSpy.end();

        // assert
        verify(gameSpy).onEnd();
    }

    @Test
    void whenStart_withExceptions_thenOnStartIsNotCalled() throws GameStateChangeException {
        // arrange
        game.getComponentManager().addComponents(new GameComponent() {
            @Override
            public void validateStart() throws GameStateChangeException {
                throw new GameStateChangeException("Error");
            }
        });
        var gameSpy = spy(game);

        // act
        gameSpy.start();

        // assert
        verify(gameSpy, never()).onStart();
    }

    @Test
    void whenEnd_withExceptions_thenOnEndIsNotCalled() throws GameStateChangeException {
        // arrange
        game.getComponentManager().addComponents(new GameComponent() {
            @Override
            public void validateEnd() throws GameStateChangeException {
                throw new GameStateChangeException("Error");
            }
        });
        var gameSpy = spy(game);

        // act
        gameSpy.end();

        // assert
        verify(gameSpy, never()).onEnd();
    }
}