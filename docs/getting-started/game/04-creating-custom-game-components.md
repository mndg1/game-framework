#  Creating custom game components
As pointed out in [02 - Game components](./02-game-components.md), a game component is any class that extends `GameComponent`.
To create your own game component you can simply create a class which meets this criterium.
```java
public class ExampleComponent extends GameComponent {

}
```

## Hooking into the game's start and end methods
The `GameComponent` class defines the methods `start()` and `end()`.
When the `start()` or `end()` method of the associated game is called, the respective method of its registered components will also be called.
You can choose to override the methods defined in the `GameComponent` class.
This means that you can implement any logic that needs to be executed when the component enters the respective state.
```java
public class ExampleComponent extends GameComponent {
    private final Logger logger;

    public ExampleComponent(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void start() {
        logger.info("Entering start state!");
    }

    @Override
    public void end() {
        logger.info("Entering end state!")
    }
}
```

## Influencing whether the game can start or end
If your component is not in the desired state when the game tries to enter the next state, you can throw a `GameStateChangeException` to interrupt the state change.\
*This is a hypothetical implementation for demo purposes*
```java
public class ExampleComponent extends GameComponent {
    private final PlayerManager<ExamplePlayer> playerManager;

    public ExampleComponent(PlayerManager<ExamplePlayer> playerManager) {
        this.playerManager = playerManager;
    }

    @Override
    public void end() {
        var topPlayer = playerManager.getAllPlayers().stream()
            .max(Comparator.comparing(ExamplePlayer::getScore)).get();

    	if (!topPlayer.getBukkitPlayer().getName().Equals("TheKap27")) {
            throw new GameStateChangeException("Cannot end game because TheKap27 is losing");
        }
    }
}
```
The example above will prevent the game from ending if I do not have the highest score.
Wherever we call the game's `end()` method we can now catch this exception and log the error messagage.
```java
try {
    game.end();
}
catch(GameStateChangeException stateChangeException) {
    logger.logInfo(stateChangeException.getMessage());
}
```