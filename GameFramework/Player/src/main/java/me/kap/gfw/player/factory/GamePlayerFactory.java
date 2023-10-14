package me.kap.gfw.player.factory;

import me.kap.gfw.player.GamePlayer;
import org.bukkit.entity.Player;

/**
 * The default implementation of {@link PlayerFactory} which creates {@link GamePlayer} instances.
 */
public class GamePlayerFactory implements PlayerFactory<GamePlayer> {

    @Override
    public GamePlayer createPlayer(Player bukkitPlayer) {
        return new GamePlayer(bukkitPlayer);
    }
}
