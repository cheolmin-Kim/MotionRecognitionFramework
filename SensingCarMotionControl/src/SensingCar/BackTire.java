package SensingCar;

import com.pi4j.io.gpio.RaspiPin;
import hardware.motor.DCMotor;
import hardware.motor.PCA9685;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BackTire{
	
	private static final Logger logger=LoggerFactory.getLogger(BackTire.class);

	private DCMotor dcMoterLeft;
	private DCMotor dcMoterRight;
	private PCA9685 pca9685;
	private final int maxStep = 4095;
	private final int minStep = 0;
	private int currStep;
	private String currDirection;

	public BackTire() throws Exception {
		pca9685 = PCA9685.getInstance();
		dcMoterLeft = new DCMotor(RaspiPin.GPIO_00, RaspiPin.GPIO_01, pca9685, PCA9685.PWM_05);
		dcMoterRight = new DCMotor(RaspiPin.GPIO_02, RaspiPin.GPIO_03, pca9685, PCA9685.PWM_04);
		forward();
	}

	public void forward() {
		dcMoterLeft.forward();
		dcMoterRight.forward();
		currDirection = "forward";
	}

	public void backward() {
		dcMoterLeft.backward();
		dcMoterRight.backward();
		currDirection = "backward";
	}

	public void setSpeed(int step) {
		if (step < minStep) {
			step = minStep;
		}
		if (step > maxStep) {
			step = maxStep;
		}
		currStep = step;
		dcMoterLeft.setSpeed(currStep);
		dcMoterRight.setSpeed(currStep);

	}

	public void stop() {
		dcMoterLeft.stop();
		dcMoterRight.stop();
	}

}
