package me.kap.gfw.arena;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ArenaComponentTest {

    @Test
    void whenValidateStart_withMissingRequiredLocations_thenStartConditionsAreNotMet() {
        // arrange
        var requiredLocationNames = List.of("required-location");
        var arenaComponent = new ArenaComponent(requiredLocationNames);

        // act
        var failingConditions = arenaComponent.getConfiguration().startConditions().stream()
                .filter(x -> !x.condition().get())
                .toList();

        // assert
        assertEquals(1, failingConditions.size());
    }

    @Test
    void whenValidateStart_withNoMissingRequiredLocations_thenStartConditionsAreMet() {
        // arrange
        var requiredLocationName = "required-location";
        var arenaLocationFake = mock(ArenaLocation.class);
        when(arenaLocationFake.locationName()).thenReturn(requiredLocationName);

        var arenaComponent = new ArenaComponent(List.of(requiredLocationName));
        arenaComponent.getArena().setLocation(arenaLocationFake);

        // act
        var failingConditions = arenaComponent.getConfiguration().startConditions().stream()
                .filter(x -> !x.condition().get())
                .toList();

        // assert
        assertEquals(0, failingConditions.size());
    }

    @Test
    void whenValidateStart_withNoRequiredLocationsDefined_thenNoExceptionIsThrown() {
        // arrange
        var arenaComponent = new ArenaComponent();

        // act
        var failingConditions = arenaComponent.getConfiguration().startConditions().stream()
                .filter(x -> !x.condition().get())
                .toList();

        // assert
        assertEquals(0, failingConditions.size());
    }
}