package me.kap.gfw.player.factory;

import me.kap.gfw.player.GamePlayer;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlayerFactoryGamePlayerTest {
    private final PlayerFactory<GamePlayer> playerFactory;
    private final Player bukkitPlayerFake;

    PlayerFactoryGamePlayerTest() {
        var playerId = UUID.fromString("00000000-0000-0000-0000-000000000000");
        bukkitPlayerFake = mock(Player.class);

        when(bukkitPlayerFake.getUniqueId()).thenReturn(playerId);

        playerFactory = new GamePlayerFactory();
    }

    @Test
    void whenCreatePlayer_withBukkitPlayer_thenGamePlayerIsCreated() {
        // act
        var createdPlayer = playerFactory.createPlayer(bukkitPlayerFake);

        // assert
        assertNotNull(createdPlayer);
    }
}