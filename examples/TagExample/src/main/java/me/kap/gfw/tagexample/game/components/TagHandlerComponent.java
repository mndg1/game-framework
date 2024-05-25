package me.kap.gfw.tagexample.game.components;

import me.kap.gfw.game.GameComponent;
import me.kap.gfw.player.PlayerManager;
import me.kap.gfw.tagexample.game.OnTag;
import me.kap.gfw.tagexample.player.Role;
import me.kap.gfw.tagexample.player.TagPlayer;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;

import java.util.ArrayList;
import java.util.Collection;

// This component handles the tagging logic.
public class TagHandlerComponent extends GameComponent {
    private final PlayerManager<TagPlayer> playerManager;
    private final Collection<OnTag> tagEventSubscribers = new ArrayList<>();

    public TagHandlerComponent(PlayerManager<TagPlayer> playerManager) {
        this.playerManager = playerManager;
    }

    @Override
    public void start() {
        playerManager.getAllPlayers().forEach(player -> player.setImmunity(false));
    }

    public void performTag(TagPlayer tagger, TagPlayer tagged) {
        // Only taggers can actually tag people.
        if (tagger.getRole() != Role.TAGGER) {
            return;
        }

        // There are various reasons why the tagged player might not be able to be tagged.
        // For example:
        //  - The 'runner' themselves are a tagger.
        //  - The runner has immunity.
        if (!tagged.isTaggable()) {
            return;
        }

        // Execute methods that are subscribed to the onTag event.
        tagEventSubscribers.forEach(x -> x.callback(tagger, tagged));

        broadcastTagOccurredMessage(tagger, tagged);

        // Update involved player roles.
        tagged.setRole(Role.TAGGER);
        tagger.setRole(Role.RUNNER);
    }

    public void addOnTagListener(OnTag callback) {
        tagEventSubscribers.add(callback);
    }

    private void broadcastTagOccurredMessage(TagPlayer tagger, TagPlayer tagged) {
        var tagOccurredMessage = new ComponentBuilder()
                .append(tagger.getBukkitPlayer().getDisplayName()).color(tagger.getRole().getColor())
                .append(" has tagged ").color(ChatColor.YELLOW)
                .append(tagged.getBukkitPlayer().getDisplayName()).color(tagged.getRole().getColor())
                .append(".").color(ChatColor.YELLOW)
                .create();
        playerManager.getAllPlayers().forEach(player -> player.getBukkitPlayer().spigot().sendMessage(tagOccurredMessage));
    }
}
