package me.kap.gfw.tagexample;

import me.kap.gfw.arena.ArenaComponent;
import me.kap.gfw.events.timing.TimerComponent;
import me.kap.gfw.tagexample.commands.EndGameCommand;
import me.kap.gfw.tagexample.commands.SetLocationCommand;
import me.kap.gfw.tagexample.commands.StartGameCommand;
import me.kap.gfw.tagexample.events.EntityInteractEvents;
import me.kap.gfw.tagexample.events.PlayerLogEvents;
import me.kap.gfw.tagexample.game.TagGame;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class TagExample extends JavaPlugin {

    @Override
    public void onEnable() {
        var game = new TagGame(getLogger());

        // Add components to the game
        game.getComponentManager().addComponents(
                new TimerComponent(this),
                new ArenaComponent()
        );

        Bukkit.getPluginManager().registerEvents(new EntityInteractEvents(game), this);
        Bukkit.getPluginManager().registerEvents(new PlayerLogEvents(game.getPlayerManager()), this);

        getCommand("start").setExecutor(new StartGameCommand(game));
        getCommand("end").setExecutor(new EndGameCommand(game));

        ArenaComponent arenaComponent = game.getComponentManager().getComponent(ArenaComponent.class);
        getCommand("setLocation").setExecutor(new SetLocationCommand(arenaComponent.getArena()));
    }
}
