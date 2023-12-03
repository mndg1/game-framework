package me.kap.gfw.game;

import me.kap.gfw.game.exceptions.CannotBuildGameTypeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameBuilderTest {

    @Test
    void whenBuild_withComponent_thenGameComponentIsNotNull() {
        // arrange
        GameComponent componentFake = mock(GameComponent.class);
        GameBuilder<GameFake> builder = new GameBuilder<>(GameFake.class)
                .addComponents(componentFake);

        // act
        Game game = builder.build();

        // assert
        assertNotNull(game.getComponentManager().getComponent(componentFake.getClass()));
    }

    @Test
    void whenBuild_withName_thenGameHasSetName() {
        // arrange
        String gameName = "GameFake";
        GameBuilder<GameFake> builder = new GameBuilder<>(GameFake.class)
                .setGameName(gameName);

        // act
        Game game = builder.build();

        // assert
        assertEquals(gameName, game.getGameName());
    }

    @Test
    void whenBuild_withAbstractGameType_thenExceptionIsThrown() {
        // arrange
        GameBuilder<Game> gameBuilder = new GameBuilder<>(Game.class);

        // assert
        assertThrows(CannotBuildGameTypeException.class, gameBuilder::build);
    }
}