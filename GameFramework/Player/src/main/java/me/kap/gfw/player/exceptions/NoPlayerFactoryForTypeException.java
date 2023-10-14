package me.kap.gfw.player.exceptions;

/**
 * An exception indicating that no player factory was found for the given player type.
 */
public class NoPlayerFactoryForTypeException extends RuntimeException {

    public NoPlayerFactoryForTypeException(String message) {
        super(message);
    }
}
