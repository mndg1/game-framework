package me.kap.gfw.tagexample.player;

import net.md_5.bungee.api.ChatColor;

public enum Role {
    UNASSIGNED("UNASSIGNED", ChatColor.GRAY),
    TAGGER("TAGGER", ChatColor.RED),
    RUNNER("RUNNER", ChatColor.GREEN);

    private final String name;
    private final ChatColor color;

    Role(String name, ChatColor color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public ChatColor getColor() {
        return color;
    }
}
