package com.mygame.exception;

/**
 * Exception lancée lors de l'échec du chargement des ressources.
 */
public class ResourceLoadingException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 4520486234777307638L;

	public ResourceLoadingException(String message) {
        super(message);
    }

    public ResourceLoadingException(String message, Throwable cause) {
        super(message, cause);
    }
}
