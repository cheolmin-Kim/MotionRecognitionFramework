package com.raspoid;

/**
 * Parent interface used to represent an hardware component implemented in the Raspoid framework.
 * 
 * <p>This interface is implemented by the {@link GPIOComponent}, {@link PWMComponent},
 * {@link I2CComponent} and {@link AnalogComponent} parent classes.</p>
 * 
 * @author Julien Louette &amp; Ga&euml;l Wittorski
 * @version 1.0
 */
@FunctionalInterface
public interface Component {
    
    /**
     * Get the String representation of the type of the component.
     * @return the String representation of the type of the component.
     */
    public String getType();
}