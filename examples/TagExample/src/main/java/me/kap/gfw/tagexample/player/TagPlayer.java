package me.kap.gfw.tagexample.player;

import me.kap.gfw.player.GamePlayer;
import org.bukkit.entity.Player;

public class TagPlayer extends GamePlayer {
    private Role role;
    private boolean hasImmunity;

    public TagPlayer(Player bukkitPlayer) {
        super(bukkitPlayer);
    }

    public boolean isTaggable() {
        return switch (getRole()) {
            // Players who do not have a role cannot be tagged.
            case UNASSIGNED -> false;
            // Taggers cannot be tagged.
            case TAGGER -> false;
            // Runners can only be tagged if they are vulnerable (have no immunity).
            case RUNNER -> !hasImmunity;
        };
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean getHasImmunity() {
        return hasImmunity;
    }

    public void setHasImmunity(boolean hasImmunity) {
        this.hasImmunity = hasImmunity;
    }
}
