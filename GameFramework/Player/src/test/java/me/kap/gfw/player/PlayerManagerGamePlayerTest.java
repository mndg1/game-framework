package me.kap.gfw.player;

import me.kap.gfw.player.factory.GamePlayerFactory;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlayerManagerGamePlayerTest {
    private final PlayerManager<GamePlayer> playerManager;

    private final GamePlayerFactory gamePlayerFactoryFake;
    private final UUID playerId;
    private final Player bukkitPlayerFake;
    private final GamePlayer gamePlayerFake;

    PlayerManagerGamePlayerTest() {
        playerId = UUID.fromString("00000000-0000-0000-0000-000000000000");

        bukkitPlayerFake = mock(Player.class);
        gamePlayerFake = mock(GamePlayer.class);
        gamePlayerFactoryFake = mock(GamePlayerFactory.class);

        when(bukkitPlayerFake.getUniqueId()).thenReturn(playerId);
        when(gamePlayerFake.getBukkitPlayer()).thenReturn(bukkitPlayerFake);
        when(gamePlayerFactoryFake.createPlayer(any(Player.class))).thenReturn(gamePlayerFake);

        playerManager = new PlayerManager<>(gamePlayerFactoryFake);
    }

    @Test
    void whenAddNewPlayer_withNewBukkitPlayer_thenPlayerCanBeFound() {
        // act
        playerManager.addNewPlayer(bukkitPlayerFake);

        // assert
        var addedPlayer = playerManager.getPlayer(playerId);
        assertNotNull(addedPlayer);
    }

    @Test
    void whenAddNewPlayer_withSamePlayerTwice_thenFactoryCreateOnlyCalledOnce() {
        // act
        playerManager.addNewPlayer(bukkitPlayerFake);
        playerManager.addNewPlayer(bukkitPlayerFake);

        // assert
        verify(gamePlayerFactoryFake, atMostOnce()).createPlayer(bukkitPlayerFake);
    }

    @Test
    void whenGetAllPlayers_withExistingPlayer_thenReturnAllAddedPlayers() {
        // arrange
        playerManager.addNewPlayer(bukkitPlayerFake);

        // act
        var allPlayers = playerManager.getAllPlayers();

        // assert
        var expectedPlayers = new HashSet<>(Collections.singletonList(gamePlayerFake));

        assertEquals(allPlayers, expectedPlayers);
    }

    @Test
    void whenRemovePlayer_withExistingPlayer_thenPlayerCannotBeFound() {
        // arrange
        playerManager.addNewPlayer(bukkitPlayerFake);

        // act
        playerManager.removePlayer(playerId);

        // assert
        assertNull(playerManager.getPlayer(playerId));
    }

    @Test
    @SuppressWarnings("unchecked")
    void whenAddPlayer_withCallback_thenCallbackIsExecuted() {
        // arrange
        var callbackMock = (Consumer<GamePlayer>) mock(Consumer.class);
        playerManager.addPlayerAddCallback(callbackMock);

        // act
        playerManager.addNewPlayer(bukkitPlayerFake);

        // assert
        verify(callbackMock).accept(any(GamePlayer.class));
    }

    @Test
    @SuppressWarnings("unchecked")
    void whenRemovePlayer_withCallback_thenCallbackIsExecuted() {
        // arrange
        var callbackMock = (Consumer<GamePlayer>) mock(Consumer.class);
        playerManager.addNewPlayer(bukkitPlayerFake);
        playerManager.addPlayerRemoveCallback(callbackMock);

        // act
        playerManager.removePlayer(playerId);

        // assert
        verify(callbackMock).accept(any(GamePlayer.class));
    }
}