package SensingCar;

import com.pi4j.io.gpio.RaspiPin;
import hardware.buzzer.ActiveBuzzer;
import org.eclipse.californium.core.CoapResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Buzzer extends CoapResource {
private static final Logger logger=LoggerFactory.getLogger(Buzzer.class);
	private ActiveBuzzer buzzer;
	private String currstatus;
	private static Buzzer instance;
	


	public Buzzer() throws Exception {
		super("buzzer");
		instance=this;
		buzzer=new ActiveBuzzer(RaspiPin.GPIO_24);
		off();
		
	}
	
	public static Buzzer getInstance(){
		
		return instance;
	}
	
	public void on(){
		buzzer.on();
		currstatus="on";
	}
		public void off(){
		buzzer.off();
		currstatus="off";
	}

}
