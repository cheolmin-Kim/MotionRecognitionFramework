package com.raspoid.sensors;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import java.io.IOException;


public class TouchSwitch {
	
	private GpioPinDigitalInput gpioPinDigitalInput;
	private String state="off";
	
	public TouchSwitch(Pin TouchPinNo){
		GpioController gpioController = GpioFactory.getInstance();
		gpioPinDigitalInput = gpioController.provisionDigitalInputPin(TouchPinNo);
		gpioPinDigitalInput.setShutdownOptions(true);
	}
	
	public void setGpioPinListenerDigital(GpioPinListenerDigital listener) {
		gpioPinDigitalInput.addListener(listener);
	}
	
	public String getStatus(){
		
		if(gpioPinDigitalInput.getState()==PinState.HIGH){
			state = "off";
		}else state="on";
		
		return state;
	}
	

	//Method
	public static void main(String[] args) throws IOException {
		TouchSwitch button = new TouchSwitch(RaspiPin.GPIO_05);
		button.setGpioPinListenerDigital(new GpioPinListenerDigital() {
			@Override
			public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
				if(event.getState() == PinState.HIGH) {
					System.out.println("off");
				} else {
					System.out.println("on");
				}
			}
		});
		
		System.out.println("Ready...");
		System.in.read();
	}
	
}
