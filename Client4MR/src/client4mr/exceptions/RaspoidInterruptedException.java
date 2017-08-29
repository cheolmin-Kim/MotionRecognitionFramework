package com.raspoid.exceptions;

/**
 * Exception thrown by raspoid when a thread is interrupted unexpectedly
 * 
 * @author Julien Louette &amp; Ga&euml;l Wittorski
 * @version 1.0
 */
public class RaspoidInterruptedException extends RaspoidException {

    /**
     * Constructs a new interrupted exception with the specified detail message and cause.
     * @param message the detail message
     * @param cause the cause to be retrieved later
     */
    public RaspoidInterruptedException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new interrupted exception with the specified detail message.
     * @param message the detail message
     */
    public RaspoidInterruptedException(String message) {
        super(message);
    }

    /**
     * Constructs a new interrupted exception with the specified cause.
     * @param cause the cause to be retrieved later
     */
    public RaspoidInterruptedException(Throwable cause) {
        super(cause);
    }

    
    
}