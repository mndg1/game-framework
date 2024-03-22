package me.kap.gfw.tagexample.commands;

import me.kap.gfw.game.exceptions.GameStateChangeException;
import me.kap.gfw.tagexample.game.TagGame;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class EndGameCommand implements CommandExecutor {
    private final TagGame game;

    public EndGameCommand(TagGame game) {
        this.game = game;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, String[] strings) {
        try {
            game.end();
        } catch (GameStateChangeException stateChangeException) {
            commandSender.sendMessage(stateChangeException.getMessage());
        }

        var gameEndedMessage = new ComponentBuilder()
                .append("The game has ended!")
                .color(ChatColor.RED)
                .create();

        game.getAnnouncer().broadcast(gameEndedMessage);

        return true;
    }
}
