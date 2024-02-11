package me.kap.gfw.arena;

import me.kap.gfw.game.exceptions.GameStateChangeException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ArenaComponentTest {

    @Test
    void whenValidateStart_withMissingRequiredLocations_thenExceptionIsThrown() {
        // arrange
        var requiredLocationNames = List.of("required-location");
        var arenaComponent = new ArenaComponent(requiredLocationNames);

        // assert
        assertThrows(GameStateChangeException.class, arenaComponent::validateStart);
    }

    @Test
    void whenValidateStart_withNoMissingRequiredLocations_thenNoExceptionIsThrown() {
        // arrange
        var requiredLocationName = "required-location";
        var arenaLocationFake = mock(ArenaLocation.class);
        when(arenaLocationFake.locationName()).thenReturn(requiredLocationName);

// act
        var arenaComponent = new ArenaComponent(List.of(requiredLocationName));
        arenaComponent.getArena().setLocation(arenaLocationFake);

        // assert
        assertDoesNotThrow(arenaComponent::validateStart);
    }

    @Test
    void whenValidateStart_withNoRequiredLocationsDefined_thenNoExceptionIsThrown() {
        // arrange
        var arenaComponent = new ArenaComponent();

        // assert
        assertDoesNotThrow(arenaComponent::validateStart);
    }
}