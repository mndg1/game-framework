package me.kap.gfw.arena;

import me.kap.gfw.game.exceptions.GameStateChangeException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ArenaComponentTest {
    private final String requiredLocationName = "required-location";
    private final ArenaComponent arenaComponent = new ArenaComponent(List.of(requiredLocationName), new Arena("test-arena"));

    @Test
    void whenStart_withMissingRequiredLocations_thenExceptionIsThrown() {
        // assert
        assertThrows(GameStateChangeException.class, arenaComponent::validateStart);
    }

    @Test
    void whenStart_withNoMissingRequiredLocations_thenStartReturnsTrue() {
        // arrange
        var arenaLocationFake = mock(ArenaLocation.class);
        when(arenaLocationFake.locationName()).thenReturn(requiredLocationName);
        arenaComponent.getArena().setLocation(arenaLocationFake);

        // assert
        assertDoesNotThrow(arenaComponent::validateStart);
    }
}