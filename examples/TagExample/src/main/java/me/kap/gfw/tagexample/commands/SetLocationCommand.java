package me.kap.gfw.tagexample.commands;

import me.kap.gfw.arena.Arena;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SetLocationCommand implements CommandExecutor {
    private final Arena arena;

    public SetLocationCommand(Arena arena) {
        this.arena = arena;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, String[] args) {
        if (args.length < 1) {
            return false;
        }

        if (!(commandSender instanceof Player)) {
            return false;
        }

        var location = ((Player)commandSender).getLocation();
        arena.setLocation(args[0], location);

        return true;
    }
}
