package me.kap.gfw.tagexample.commands;

import me.kap.gfw.tagexample.game.TagGame;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class EndGameCommand implements CommandExecutor {
    private final TagGame game;

    public EndGameCommand(TagGame game) {
        this.game = game;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        game.end();

        BaseComponent[] gameEndedMessage = new ComponentBuilder()
                .append("The game has ended!")
                .color(ChatColor.RED)
                .create();

        game.getAnnouncer().broadcast(gameEndedMessage);

        return false;
    }
}
