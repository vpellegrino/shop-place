package com.acme.shop.exception;

/**
 * Gets thrown when a resource is expected but not found
 */
public class ResourceNotFoundException extends RuntimeException {

    /**
     * @see RuntimeException#RuntimeException(String)
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }

    /**
     * @see RuntimeException#RuntimeException(Throwable)
     */
    public ResourceNotFoundException(Throwable cause) {
        super(cause);
    }

    /**
     * @see RuntimeException#RuntimeException(String, Throwable)
     */
    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
