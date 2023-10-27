package me.kap.gfw.game;

import me.kap.gfw.player.GamePlayer;
import me.kap.gfw.player.PlayerManager;

public class GameFake extends Game {

    public GameFake(String gameName, PlayerManager<? extends GamePlayer> playerManager) {
        super(gameName, playerManager);
    }

    @Override
    protected void setup() {

    }

    @Override
    protected void update() {

    }

    @Override
    protected void finish() {

    }
}
