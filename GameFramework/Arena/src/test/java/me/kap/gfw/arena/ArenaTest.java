package me.kap.gfw.arena;

import org.bukkit.Location;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ArenaTest {
    private final Arena arena;
    private final Location newBukkitLocationFake;
    private final Location existingBukkitLocationFake;

    ArenaTest() {
        newBukkitLocationFake = mock(Location.class);
        existingBukkitLocationFake = mock(Location.class);

        arena = new Arena();
    }

    @Test
    void whenSetLocation_withNewLocation_thenNewLocationIsSame() {
        // act
        ArenaLocation actualLocation =  arena.setLocation("newLocation", newBukkitLocationFake);

        // assert
        assertSame(newBukkitLocationFake, actualLocation.getBukkitLocation());
    }

    @Test
    void whenSetLocation_withExistingLocation_thenLocationIsSameAsNew() {
        // arrange
        String duplicateName = "duplicateLocation";
        arena.setLocation(duplicateName, existingBukkitLocationFake);

        // act
        ArenaLocation actualLocation = arena.setLocation(duplicateName, newBukkitLocationFake);

        // assert
        assertSame(newBukkitLocationFake, actualLocation.getBukkitLocation());
    }

    @Test
    void whenRemoveLocation_withExistingLocation_thenLocationIsNull() {
        // arrange
        String existingName = "existingLocation";
        arena.setLocation(existingName, existingBukkitLocationFake);

        // act
        arena.removeLocation(existingName);

        // assert
        assertNull(arena.getLocation(existingName));
    }

    @Test
    void whenGetAllLocations_withExistingLocations_thenAllLocationsAreReturned() {
        // arrange
        ArenaLocation existingArenaLocation = new ArenaLocation("existingLocation", existingBukkitLocationFake);
        arena.setLocation(existingArenaLocation);

        // act
        Set<ArenaLocation> allLocations = arena.getAllLocations();

        // assert
        Set<ArenaLocation> expectedLocations = Collections.singleton(existingArenaLocation);

        assertEquals(expectedLocations, allLocations);
    }
}