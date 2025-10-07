package com.mygame.exception;

/**
 * Exception lancée lors de l'échec de la génération de la carte.
 */
public class MapGenerationException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = -9069440603105654623L;

	public MapGenerationException(String message) {
        super(message);
    }

    public MapGenerationException(String message, Throwable cause) {
        super(message, cause);
    }
}
