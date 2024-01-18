# Game components
A game component is any class that extends the `GameComponent` class.
Game components offer a way of adding logic to your game in a way that is decoupled from the game itself.

Additionally game components allow you to hook into the game's `start()` and `end()` methods.
Note That these are different than the `onStart()` and `onEnd()` methods from the `Game` itself, which are called at the end of each respective method. \
*Read more about this feature [here](./04-creating-custom-game-components.md).*

## Read next
03 [Using game components](./03-using-game-components)