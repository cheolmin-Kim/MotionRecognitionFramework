package SensingCar;

import hardware.motor.PCA9685;
import hardware.motor.SG90ServoPCA9685Duration;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FrontTire extends CoapResource {
private static final Logger logger=LoggerFactory.getLogger(FrontTire.class);
	private PCA9685 pca9685;
	private SG90ServoPCA9685Duration servoMotor;
	private final int maxAngle =120;
	private final int minAngle =45;
	private int currAngle;


	public FrontTire() throws Exception {
		super("fronttire");
		pca9685=PCA9685.getInstance();
		servoMotor = new SG90ServoPCA9685Duration(pca9685, PCA9685.PWM_00);
		setAngle(90);
	}
	public void setAngle(int angle){
		if(angle<minAngle){
			angle=minAngle;
		}
		if(angle>maxAngle){
			angle=maxAngle;
		}
		servoMotor.setAngle(angle);
		currAngle=angle;
	}
	
	



}
