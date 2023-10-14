package me.kap.gfw.player.exceptions;

/**
 * An exception indicating that no valid player factory is present.
 */
public class InvalidPlayerFactoryException extends RuntimeException {

    public InvalidPlayerFactoryException(String message) {
        super(message);
    }
}
