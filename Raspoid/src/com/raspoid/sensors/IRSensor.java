package com.raspoid.sensors;

import converter.PCF8591;



public class IRSensor {

    //Field
    private PCF8591 pcf8591;

    //Constructor
    public IRSensor(PCF8591 pcf8591) {
        this.pcf8591 = pcf8591;
    }

    //Method
    public double getValue() throws Exception {
        // analogRead(): 0 ~ 255
        int analogVal = pcf8591.analogRead();

        double voltage = 3.3 * (double) analogVal / 255;
        double distance = 13 * Math.pow(voltage, -1);

        // IR sensor Range
        if (distance >= 30) {
            distance = 30;
        } else if (distance <= 4) {
            distance = 4;
        }
        return (int) (distance * 100) / 100.00;
    }

    public static void main(String[] args) throws Exception {
        PCF8591 pcf8591 = new PCF8591(0x48, PCF8591.AIN0);
        IRSensor iRSensor = new IRSensor(pcf8591);
        while (true) {
            double distance = iRSensor.getValue();
            System.out.println("distance: " + distance);
            Thread.sleep(500);
        }
    }
}
