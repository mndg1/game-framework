package me.kap.gfw.tagexample.game;

import me.kap.gfw.tagexample.player.TagPlayer;

@FunctionalInterface
public interface OnTag {
    void callback(TagPlayer tagger, TagPlayer tagged);
}
