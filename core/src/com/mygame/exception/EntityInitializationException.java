package com.mygame.exception;

/**
 * Exception lancée lors de l'échec de l'initialisation d'une entité.
 */
public class EntityInitializationException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = -6885717759952219191L;

	public EntityInitializationException(String message) {
        super(message);
    }

    public EntityInitializationException(String message, Throwable cause) {
        super(message, cause);
    }
}
