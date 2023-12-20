package me.kap.gfw.tagexample.player;

import me.kap.gfw.player.GamePlayer;
import org.bukkit.entity.Player;

public class TagPlayer extends GamePlayer {
    private Role role;
    private State state;

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
            case RUNNER -> getState() == State.VULNERABLE;
        };
    }

    public void sendRoleStatusNotification() {
        String roleMessage = getRole().getColor() + getRole().getName();

        getBukkitPlayer().sendTitle(roleMessage, null, 20, 30, 20);
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
