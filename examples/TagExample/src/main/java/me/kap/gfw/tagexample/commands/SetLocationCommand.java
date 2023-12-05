package me.kap.gfw.tagexample.commands;

import me.kap.gfw.arena.Arena;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetLocationCommand implements CommandExecutor {
    private final Arena arena;

    public SetLocationCommand(Arena arena) {
        this.arena = arena;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (args.length < 1) {
            return false;
        }

        if (!(commandSender instanceof Player)) {
            return false;
        }

        Location location = ((Player)commandSender).getLocation();
        arena.setLocation(args[0], location);

        return false;
    }
}
