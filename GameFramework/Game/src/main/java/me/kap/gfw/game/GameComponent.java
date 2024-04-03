package me.kap.gfw.game;

/**
 * An abstract class that allows for adding additional logic to a game.
 */
public abstract class GameComponent {
    private final GameComponentConfiguration configuration;

    protected GameComponent() {
        var configurationBuilder = new GameComponentConfigurationBuilder();
        configure(configurationBuilder);
        configuration = configurationBuilder.build();
    }

    /**
     * Allows for adding additional starting logic to a game.
     */
    public void start() {
        // This method may be overridden in order to add custom starting logic.
    }

    /**
     * Allows for adding additional ending logic to a game.
     */
    public void end() {
        // This method may be overridden in order to add custom ending logic.
    }

    /**
     * Allows for configuring various settings of the component.
     *
     * @param configurationBuilder Configuration builder through which to configure component settings.
     */
    public void configure(GameComponentConfigurationBuilder configurationBuilder) {
        // This method may be overridden in order to configure the component.
    }

    public GameComponentConfiguration getConfiguration() {
        return configuration;
    }
}
