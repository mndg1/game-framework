package me.kap.gfw.player.factory;

import me.kap.gfw.player.GamePlayer;
import me.kap.gfw.player.exceptions.NoPlayerFactoryForTypeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerFactoryRegistryTest {
    private final PlayerFactoryRegistry playerFactoryRegistry;
    private final Class<GamePlayer> playerSubjectClass;
    private final PlayerFactory<GamePlayer> playerFactory;

    PlayerFactoryRegistryTest() {
        playerSubjectClass = GamePlayer.class;
        playerFactory = new GamePlayerFactory();

        playerFactoryRegistry = new PlayerFactoryRegistry();
    }

    @Test
    void whenRegisterFactory_thenFactoryCanBeResolved() {
        // act
        playerFactoryRegistry.registerFactory(playerSubjectClass, playerFactory);

        // assert
        assertNotNull(playerFactoryRegistry.resolveFactory(playerSubjectClass));
    }

    @Test
    void whenResolveFactory_withNonRegisteredFactory_then() {
        // assert
        assertThrows(NoPlayerFactoryForTypeException.class, () -> {
            playerFactoryRegistry.resolveFactory(playerSubjectClass);
        });
    }

    @Test
    void whenResolveFactory_withRegisteredFactory_thenResolvedFactoryIsSameInstance() {
        // arrange
        playerFactoryRegistry.registerFactory(playerSubjectClass, playerFactory);

        // act
        PlayerFactory<?> resolvedFactory = playerFactoryRegistry.resolveFactory(playerSubjectClass);

        // assert
        assertSame(playerFactory, resolvedFactory);
    }
}