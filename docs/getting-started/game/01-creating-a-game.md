# Creating a game
To start creating a custom game you must define a class which extends `Game`.
```java
// The base class can be found in the 'me.kap.gfw.game' package
import me.kap.gfw.game.Game;

public class ExampleGame extends Game {
    
}
```
Once you have created your game class you may choose to override the `onStart()` and/or `onEnd()` method(s).
```java
public class ExampleGame extends Game {
    
    @Override
    protected void onStart() {
        // Called at the end of the game's start() method.
    }

    @Override
    protected void onEnd() {
        // Called at the end of the game's end() method.
    }
}
```

## Using the onStart method
`onStart()` is called when the game is starting.
As such it is useful for setting up the initial game state as it should be when the game begins.\
*The following example shows a hypothetical use case:*
```java
@Override
protected void onStart() {
    // Assign roles to the players.
    roleManager.assignAllRoles(playerManager.getAllPlayers());

    // Teleport all players to random locations in the arena.
    Arena arena = getComponentManager().getComponent(ArenaComponent.class).getArena();    
    getPlayerManager().getAllPlayers().foreach(x -> LocationHelper.teleportToRandomLocation(x, arena.getAllLocations()))    
}
```

## Using the onEnd method
`onEnd()` is called when the game is ending.
This means it can be used to finalize the game.\
*The following example shows a hypothetical use case:*
```java
@Override 
protected void onEnd() {
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
02: [Game components](./02-game-components.md)\
03: [Using game components](./03-using-game-components)