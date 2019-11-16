package com.acme.shop.exception;

/**
 * Used to manage request handling errors
 */
public class RequestHandlingException extends RuntimeException {

    /**
     * @see RuntimeException#RuntimeException(String)
     */
    public RequestHandlingException(String message) {
        super(message);
    }

    /**
     * @see RuntimeException#RuntimeException(Throwable)
     */
    public RequestHandlingException(Throwable cause) {
        super(cause);
    }

    /**
     * @see RuntimeException#RuntimeException(String, Throwable)
     */
    public RequestHandlingException(String message, Throwable cause) {
        super(message, cause);
    }

}
