package me.kap.gfw.tagexample.player;

import me.kap.gfw.player.factory.PlayerFactory;
import org.bukkit.entity.Player;

public class TagPlayerFactory implements PlayerFactory<TagPlayer> {

    // This implementation of the PlayerFactory interface creates a new TagPlayer
    // and ensures that they are vulnerable and their role is 'unassigned'.
    @Override
    public TagPlayer createPlayer(Player player) {
        var tagPlayer = new TagPlayer(player);

        tagPlayer.setRole(Role.UNASSIGNED);
        tagPlayer.setHasImmunity(false);

        return tagPlayer;
    }
}
