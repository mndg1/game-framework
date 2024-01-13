# Creating a game
To start creating a custom game you must define a class which extends `Game`.
Extending the `Game` class also means you must implement the `setup()` and `finish()` methods.
```java
// The base class can be found in the 'me.kap.gfw.game' package
import me.kap.gfw.game.Game;

public class ExampleGame extends Game {

    @Override
    protected void setup() {
        // The setup method is called when the game starts.
    }

    @Override
    protected void finish() {
        // The finish method is called when the game ends.
    }
}
```

## Using the setup method
`setup()` is called when the game is starting.
As such it is useful for setting up the initial game state as it should be when the game begins.\
*The following example shows a hypothetical use case:*
```java
@Override
protected void setup() {
    // Assign roles to the players.
    roleManager.assignAllRoles(playerManager.getAllPlayers());

    // Teleport all players to random locations in the arena.
    Arena arena = getComponentManager().getComponent(ArenaComponent.class).getArena();    
    getPlayerManager().getAllPlayers().foreach(x -> LocationHelper.teleportToRandomLocation(x, arena.getAllLocations()))    
}
```

## Using the finish method
`finish()` is called when the game is ending.
This means it can be used to finalize the game.\
*The following example shows a hypothetical use case:*
```java
@Override 
protected void finish() {
    // Announce the winner.
    GamePlayer topPlayer = getScoreManager().getTopPlayer();
    String winnerAnnouncement = String.format("%s won the game!", topPlayer.getBukkitPlayer().getDisplayeName());
    getPlayerManager().getAllPlayers().foreach(x -> x.getBukkitPlayer().sendMessage(winnerAnnouncement));

    // Teleport players to lobby.
    Arena arena = getComponentManager().getComponent(ArenaComponent.class).getArena();
    ArenaLocation lobbyLocation = arena.getLocation("lobby");
    getPlayerManager().getAllPlayers().foreach(x -> x.getBukkitPlayer().teleport(lobbyLocation.bukkitLocation()));
}
```

## Read next
02: [Using game components](./02-using-game-components.md)