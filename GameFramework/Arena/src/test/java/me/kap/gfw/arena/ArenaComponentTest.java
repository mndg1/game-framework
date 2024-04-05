package me.kap.gfw.arena;

import me.kap.gfw.game.Game;
import me.kap.gfw.game.exceptions.GameStateChangeException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ArenaComponentTest {
    private final Game game = new Game() {
    };

    @Test
    void whenStart_withMissingRequiredLocations_thenExceptionIsThrown() {
        // arrange
        var requiredLocationNames = List.of("required-location");
        var arenaComponent = new ArenaComponent(requiredLocationNames);
        game.getComponentManager().addComponents(arenaComponent);

        // assert
        assertThrows(GameStateChangeException.class, game::start);
    }

    @Test
    void whenStart_withNoMissingRequiredLocations_thenNoExceptionIsThrown() {
        // arrange
        var requiredLocationName = "required-location";
        var arenaLocationFake = mock(ArenaLocation.class);
        when(arenaLocationFake.locationName()).thenReturn(requiredLocationName);

        var arenaComponent = new ArenaComponent(List.of(requiredLocationName));
        arenaComponent.getArena().setLocation(arenaLocationFake);
        game.getComponentManager().addComponents(arenaComponent);

        // assert
        assertDoesNotThrow(game::start);
    }

    @Test
    void whenStart_withNoRequiredLocationsDefined_thenNoExceptionIsThrown() {
        // arrange
        var arenaComponent = new ArenaComponent();
        game.getComponentManager().addComponents(arenaComponent);

        // assert
        assertDoesNotThrow(game::start);
    }
}