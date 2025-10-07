package com.mygame.exception;

/**
 * Exception lancée lors de l'échec de l'initialisation d'un niveau.
 */
public class LevelInitializationException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = -6278373253659472677L;

	public LevelInitializationException(String message) {
        super(message);
    }

    public LevelInitializationException(String message, Throwable cause) {
        super(message, cause);
    }
}
