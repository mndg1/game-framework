package me.kap.gfw.tagexample.commands;

import me.kap.gfw.game.exceptions.GameStateChangeException;
import me.kap.gfw.tagexample.game.TagGame;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class StartGameCommand implements CommandExecutor {
    private final TagGame game;

    public StartGameCommand(TagGame game) {
        this.game = game;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, String[] strings) {
        try {
            game.start();
        } catch (GameStateChangeException stateChangeException) {
            commandSender.sendMessage(ChatColor.RED + stateChangeException.getMessage());
        }

        return true;
    }
}
