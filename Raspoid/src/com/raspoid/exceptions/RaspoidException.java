package com.raspoid.exceptions;

/**
 * General exception thrown in raspoid
 * 
 * @author Julien Louette &amp; Ga&euml;l Wittorski
 * @version 1.0
 */
public class RaspoidException extends RuntimeException {

    /**
     * Default constructor with no detailed information is private
     * to force child to use the constructor with information
     */
    private RaspoidException() {
        super();
    }

    /**
     * Constructs a new raspoid exception with the specified detail message and cause.
     * @param message the detail message
     * @param cause the cause to be retrieved later
     */
    public RaspoidException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new raspoid exception with the specified detail message.
     * @param message the detail message
     */
    public RaspoidException(String message) {
        super(message);
    }

    /**
     * Constructs a new raspoid exception with the specified cause.
     * @param cause the cause to be retrieved later
     */
    public RaspoidException(Throwable cause) {
        super(cause);
    }
    
}