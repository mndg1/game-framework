# Using game components
The `Game` class contains a `GameComponentManager`.
The `GameComponentManager` is a class that holds all of the game components that are registered to the containing game.
It is accessible via the `getComponentManager()` method defined in the `Game` class.

## Adding components
components can be added to a `GameComponentManager` using its `addComponent()` method to add a single component, or its `addComponents()` method to add a collection or a variable amount of components.

```java
// Adding a single component
game.getComponentManager().addComponent(new FooComponent());
```
```java
// Adding a collection of components
var components = List.of(new FooComponent(), new BarComponent());
game.getComponentManager().addComponents(components);
```
```java
// Adding a variable amount of components
game.getComponentManager().addComponents(
    new FooComponent(),
    new BarComponent()
);
```

Added components are stored in a `Map` using the component's type as a key.
This means that you can currently only have one component of its type registered in the `ComponentManager`.

## Retrieving components
As components are stored using their type as a key, retrieving is done based on the component's type.
```java
var component = game.getComponentManager().getComponent<FooComponent>(FooComponent.class);
```
This method may thrown a `GameComponentNotAssignedException` runtime exception when a component of the requested type cannot be resolved.
This may mean that the component was not registered when trying to retrieve the component.

## Read next
04: [Creating custom game components](./04-creating-custom-game-components.md)