package com.mygame.exception;

/**
 * Exception lancée lors de l'échec de l'initialisation du jeu.
 */
public class GameInitializationException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = -7880730941218696796L;

	public GameInitializationException(String message) {
        super(message);
    }

    public GameInitializationException(String message, Throwable cause) {
        super(message, cause);
    }
}
