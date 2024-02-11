package me.kap.gfw.arena;

import org.bukkit.Location;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ArenaTest {
    private final Arena arena = new Arena();

    @Test
    void whenSetLocation_withNewLocation_thenAddedBukkitLocationIsSame() {
        // arrange
        var newBukkitLocation = mock(Location.class);

        // act
        var actualLocation = arena.setLocation("newLocation", newBukkitLocation);

        // assert
        assertSame(newBukkitLocation, actualLocation.bukkitLocation());
    }

    @Test
    void whenSetLocation_withDuplicateLocationName_thenExistingLocationIsUpdated() {
        // arrange
        var duplicateName = "Location";
        var existingBukkitLocation = mock(Location.class);
        var newBukkitLocation = mock(Location.class);
        arena.setLocation(duplicateName, existingBukkitLocation);

        // act
        var actualLocation = arena.setLocation(duplicateName, newBukkitLocation);

        // assert
        assertSame(newBukkitLocation, actualLocation.bukkitLocation());
    }

    @Test
    void whenRemoveLocation_withExistingLocation_thenLocationIsNull() {
        // arrange
        var location = mock(ArenaLocation.class);
        arena.setLocation(location);

        // act
        arena.removeLocation(location.locationName());

        // assert
        assertNull(arena.getLocation(location.locationName()));
    }

    @Test
    void whenGetAllLocations_withUniqueLocations_thenAllLocationsAreReturned() {
        // arrange
        var uniqueLocations = List.of(mock(ArenaLocation.class));
        uniqueLocations.forEach(arena::setLocation);

        // act
        var allLocations = arena.getAllLocations();

        // assert
        assertIterableEquals(uniqueLocations, allLocations);
    }

    @Test
    void whenGetAllLocations_withDuplicateLocations_thenAllUniqueLocationsAreReturned() {
        // arrange
        var location = mock(ArenaLocation.class);
        var locationsToAdd = List.of(location, location);
        locationsToAdd.forEach(arena::setLocation);

        // act
        var allLocations = arena.getAllLocations();

        // assert
        assertEquals(1, allLocations.size());
    }

    @Test
    void whenGetAllLocations_thenReturnsImmutableCollection() {
        // arrange
        var location = mock(ArenaLocation.class);
        arena.setLocation(location);

        // act
        var retrievedLocations = arena.getAllLocations();
        retrievedLocations.clear();

        // assert
        assertEquals(1, arena.getAllLocations().size());
    }
}