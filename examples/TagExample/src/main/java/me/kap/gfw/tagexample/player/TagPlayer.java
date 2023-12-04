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
        switch (getRole()) {
            case UNASSIGNED:
                // Players who do not have a role cannot be tagged.
                return false;
            case TAGGER:
                // Taggers cannot be tagged.
                return false;
            case RUNNER:
                // Runners can only be tagged if they are vulnerable (have no immunity).
                return getState() == State.VULNERABLE;
            default:
                // This should not happen. Just returning false.
                return false;
        }
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
