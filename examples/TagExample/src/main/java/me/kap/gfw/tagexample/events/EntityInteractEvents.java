package me.kap.gfw.tagexample.events;

import me.kap.gfw.game.GameState;
import me.kap.gfw.tagexample.game.TagGame;
import me.kap.gfw.tagexample.game.components.TagHandlerComponent;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityInteractEvents implements Listener {
    private final TagGame tagGame;

    public EntityInteractEvents(TagGame tagGame) {
        this.tagGame = tagGame;
    }

    @EventHandler
    public void onEntityDamageEntity(EntityDamageByEntityEvent event) {
        // If the game is not running, no tagging logic needs to be executed.
        if (tagGame.getState() != GameState.RUNNING) {
            return;
        }

        // Try to get the player's that where involved in the event.
        var damager = getEntityAsPlayer(event.getDamager());
        var damaged = getEntityAsPlayer(event.getEntity());

        // If either one of the entities involved is not a player, the method will return.
        if (damager == null || damaged == null) {
            return;
        }

        // Check if the players involved are part of the tag game.
        var tagger = tagGame.getPlayerManager().getPlayer(damager.getUniqueId());
        var runner = tagGame.getPlayerManager().getPlayer(damaged.getUniqueId());

        // If either one of the players is not in the game, the method will return.
        if (tagger == null || runner == null) {
            return;
        }

        // Perform the tagging logic.
        var tagHandlerComponent = tagGame.getComponentManager().getComponent(TagHandlerComponent.class);
        tagHandlerComponent.performTag(tagger, runner);

        // Cancel the event so that no damage is applied.
        event.setCancelled(true);
    }

    // This method attempts to cast the entity to a Player. If it fails, it returns null
    private Player getEntityAsPlayer(Entity entity) {
        if (!(entity instanceof Player)) {
            return null;
        }

        return (Player) entity;
    }
}
