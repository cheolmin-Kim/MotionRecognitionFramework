package SensingCar;

import hardware.motor.PCA9685;
import hardware.motor.SG90ServoPCA9685Duration;
import org.eclipse.californium.core.CoapResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Camera extends CoapResource {
private static final Logger logger=LoggerFactory.getLogger(Camera.class);
	private SG90ServoPCA9685Duration leftRightMotor;
	private SG90ServoPCA9685Duration upDownMotor;
	private PCA9685 pca9685;
	private final int minLeftRight=10;
	private final int maxLeftRight=170;
	private final int minUpDown=0;
	private final int maxUpDown=100;
	private int currLeftRight;
	private int currUpDown;
	
	
	public Camera() throws Exception {
		super("camera");
		pca9685=PCA9685.getInstance();
		leftRightMotor=new SG90ServoPCA9685Duration(pca9685, PCA9685.PWM_14);
		upDownMotor=new SG90ServoPCA9685Duration(pca9685, PCA9685.PWM_15);
		turnLeftRight(90);
        turnUpDown(10);
	
		
	}
	
	public void turnLeftRight(int angle){
		if(angle<minLeftRight)angle=minLeftRight;
		if(angle>maxLeftRight)angle=maxLeftRight;
		leftRightMotor.setAngle(angle);
		currLeftRight=angle;
	}
	
	public void turnUpDown(int angle){
				if(angle<minUpDown)angle=minUpDown;
		if(angle>maxUpDown)angle=maxUpDown;
		upDownMotor.setAngle(angle);
		currUpDown=angle;
	}

}
