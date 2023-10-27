package me.kap.gfw.player;

import me.kap.gfw.player.factory.GamePlayerFactory;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.Test;

import java.util.*;

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
        GamePlayer addedPlayer = playerManager.getPlayer(playerId);
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
        Set<GamePlayer> allPlayers = playerManager.getAllPlayers();

        // assert
        Set<GamePlayer> expectedPlayers = new HashSet<>(Collections.singletonList(gamePlayerFake));

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
}