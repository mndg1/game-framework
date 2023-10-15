package me.kap.gfw.arena;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ArenaComponentTest {

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"required-location"})
    void whenHasAllRequiredLocations_withRequiredLocationsSet_thenReturnTrue(String requiredLocationName) {
        // arrange
        ArenaComponent arenaComponent = new ArenaComponent(Collections.singleton(requiredLocationName));

        ArenaLocation arenaLocationFake = mock(ArenaLocation.class);
        Arena arenaFake = mock(Arena.class);

        when(arenaLocationFake.getLocationName()).thenReturn(requiredLocationName);
        when(arenaFake.getAllLocations()).thenReturn(Collections.singleton(arenaLocationFake));

        arenaComponent.setArena(arenaFake);

        // act
        boolean hasAllRequiredLocationsSet = arenaComponent.hasAllRequiredLocationsSet();

        // assert
        assertTrue(hasAllRequiredLocationsSet);
    }

    @Test
    void whenHasAllRequiredLocations_withInvalidRequiredLocationsSet_thenReturnsFalse() {
        // arrange
        ArenaComponent arenaComponent = new ArenaComponent(Collections.singleton("required-location"));

        // act
        boolean hasAllRequiredLocationsSet = arenaComponent.hasAllRequiredLocationsSet();

        // assert
        assertFalse(hasAllRequiredLocationsSet);
    }

    @Test
    void whenStart_withNoMissingRequiredLocations_thenStartReturnsTrue() {
        // arrange
        ArenaComponent arenaComponent = new ArenaComponent();

        // act
        boolean startedSuccessfully = arenaComponent.start();

        // assert
        assertTrue(startedSuccessfully);
    }
}