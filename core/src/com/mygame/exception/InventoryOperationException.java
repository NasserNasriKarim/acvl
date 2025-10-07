package com.mygame.exception;

/**
 * Exception lancée lors d'une opération invalide sur l'inventaire.
 */
public class InventoryOperationException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 4188402703016814795L;

	public InventoryOperationException(String message) {
        super(message);
    }

    public InventoryOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}
