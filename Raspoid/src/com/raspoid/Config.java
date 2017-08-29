package com.raspoid;

/**
 * <b>This class contains some useful information regarding configuration properties
 * needed to deal with some specific aspects of the utilization of the framework.</b>
 * 
 * <p>As an example, an important difference for the framework is regarding the i2c bus number.
 * Between the model B Rev 1 and Rev 2 versions of the Pi, they changed the signals
 * that where sent to Pin 3 and Pin 5 on the GPIO header. This changed the device 
 * number that needs to be used with i2c from 0 to 1.
 * By setting the the correct model of Raspberry Pi executing the program (with the 
 * {@link Config#setRaspberryPiModel(RaspberryPiModel)} method), you can easily avoid any problem
 * regarding this selection of the right i2c bus number for your Pi.</p>
 * 
 * @author Julien Louette &amp; Ga&euml;l Wittorski
 * @version 1.0
 */
public class Config {
    
    /**
     * The Raspberry Pi model executing the framework.
     * 
     * <p>The default value for this Raspberry Pi model is the Raspberry Pi 2.</p>
     * 
     * @see Config#getRaspberryPiModel()
     * @see Config#setRaspberryPiModel(RaspberryPiModel)
     */
    private static RaspberryPiModel raspberryPiModel = RaspberryPiModel.PI2;
    
    /**
     * Private constructor to hide the implicit public one.
     */
    private Config() {
    }
    
    /**
     * This method allows you to retrieve the selected Raspberry Pi model executing the program.
     * @return the selected Raspberry Pi model on which the program is executing.
     */
    public static RaspberryPiModel getRaspberryPiModel() {
        return raspberryPiModel;
    }
    
    /**
     * This method allows you to set the selected Raspberry Pi model executing the program.
     * @param model the selected Raspberry Pi model.
     */
    public static void setRaspberryPiModel(RaspberryPiModel model) {
        raspberryPiModel = model;
    }
}