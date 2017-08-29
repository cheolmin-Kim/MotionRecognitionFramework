package com.raspoid;

import com.pi4j.io.gpio.PinState;
import com.raspoid.sensors.TouchSwitch;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import com.pi4j.io.i2c.I2CFactory;
import com.raspoid.Tools;
import com.raspoid.MPU6050;
import com.raspoid.sensors.IRSensor;
import com.raspoid.sensors.UltrasonicSensor;
import converter.PCF8591;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.json.JSONObject;

/**
 * Example of use of an MPU6050.
 *
 * @see MPU6050
 *
 * @author Julien Louette &amp; Ga&euml;l Wittorski
 * @version 1.0
 */
public class MPU6050Example {

	public static String ipAdress = "192.168.3.109";
	public static CoapClient coapClient;
	public static CoapResponse coapResponse;
	public static JSONObject jsonObject;
	public static String json;
	public static TouchSwitch ts;
	public static UltrasonicSensor ultrasonic;
	public static PCF8591 pcf8591;
	public static IRSensor iRSensor;

	public static long time = 0;
	public static long currtime = 0;
	public static int value = 0;

	/**
	 * Private constructor to hide the implicit public one.
	 */
	private MPU6050Example() {
		coapClient = new CoapClient();

	}

	/**
	 * Command-line interface.
	 *
	 * @param args unused here.
	 */
	public static void main(String[] args) throws I2CFactory.UnsupportedBusNumberException {
		MPU6050 mpu6050 = new MPU6050();

		ts = new TouchSwitch(RaspiPin.GPIO_06);

		ultrasonic = new UltrasonicSensor(RaspiPin.GPIO_04, RaspiPin.GPIO_05);
		pcf8591 = new PCF8591(0x48, PCF8591.AIN0);
		iRSensor = new IRSensor(pcf8591);

		mpu6050.startUpdatingThread();
		time = System.currentTimeMillis();

		ts.setGpioPinListenerDigital(new GpioPinListenerDigital() {
			@Override
			public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
//				if(event.getState() == PinState.HIGH) {
//					System.out.println("off");
//				} else {
//					System.out.println("on");
//				}

				jsonObject = new JSONObject();
				jsonObject.put("sensor", "button");
				jsonObject.put("status", ts.getStatus());
				json = jsonObject.toString();

				coapClient = new CoapClient();
				coapClient.setURI("coap://" + ipAdress + "/button");
				coapResponse = coapClient.post(json, MediaTypeRegistry.APPLICATION_JSON);
				coapClient.shutdown();
			}
		});

		while (true) {

			Tools.log("-----------------------------------------------------");
//
//			// Accelerometer
//			Tools.log("Accelerometer:");
//			double[] accelAngles = mpu6050.getAccelAngles();
//			Tools.log("\t" + MPU6050.xyzValuesToString(MPU6050.angleToString(accelAngles[0]),
//							MPU6050.angleToString(accelAngles[1]), MPU6050.angleToString(accelAngles[2])));
//
//			double[] accelAccelerations = mpu6050.getAccelAccelerations();
//			Tools.log("\tAccelerations: " + MPU6050.xyzValuesToString(MPU6050.accelToString(accelAccelerations[0]),
//							MPU6050.accelToString(accelAccelerations[1]), MPU6050.accelToString(accelAccelerations[2])));
//
//			// Gyroscope
//			Tools.log("Gyroscope:");
//			double[] gyroAngles = mpu6050.getGyroAngles();
//			Tools.log("\t" + MPU6050.xyzValuesToString(MPU6050.angleToString(gyroAngles[0]),
//							MPU6050.angleToString(gyroAngles[1]), MPU6050.angleToString(gyroAngles[2])));
//
//			double[] gyroAngularSpeeds = mpu6050.getGyroAngularSpeeds();
//			Tools.log("\t" + MPU6050.xyzValuesToString(MPU6050.angularSpeedToString(gyroAngularSpeeds[0]),
//							MPU6050.angularSpeedToString(gyroAngularSpeeds[1]), MPU6050.angularSpeedToString(gyroAngularSpeeds[2])));
//
//			// Filtered angles
			Tools.log("Filtered angles:");
			double[] filteredAngles = mpu6050.getFilteredAngles();
			if (filteredAngles[2] < 0) {
				filteredAngles[2] = 180 + filteredAngles[2]%360;
			} else if (filteredAngles[2] == 0) {
				filteredAngles[2] = 180;
			} else {
				filteredAngles[2] = 180 + filteredAngles[2]%360;
			}

			Tools.log("\t" + MPU6050.xyzValuesToString(MPU6050.angleToString(filteredAngles[0]),
							MPU6050.angleToString(filteredAngles[1]), MPU6050.angleToString(filteredAngles[2])));
			mouseMove(filteredAngles[0], filteredAngles[1], filteredAngles[2]);
			
			try {
				distance("ifraredray");
				distance("ultrasonic");
			} catch (Exception ex) {
				Logger.getLogger(MPU6050Example.class.getName()).log(Level.SEVERE, null, ex);
			}

//			button();
//			
//			try {
//				distance("ultrasonic");
//				distance("ifraredray");
//			} catch (Exception ex) {	ex.printStackTrace();}
//			
			Tools.sleepMilliseconds(100);
			/*
			currtime = System.currentTimeMillis();
			System.out.println(currtime-time);
			if((currtime-time)>=60000){
				try {
					mpu6050.stopUpdatingThread();
				} catch (InterruptedException ex) {
					Logger.getLogger(MPU6050Example.class.getName()).log(Level.SEVERE, null, ex);
				}
				mpu6050=new MPU6050();
				mpu6050.startUpdatingThread();
				time = System.currentTimeMillis();
			}

			 */
		}

	}

	public static void mouseMove(double x, double y, double z) {
		double roll = x;
		double pitch = y;
		double yaw = z;
//		
//		if(yaw<0){
//			yaw=360+yaw;
//		}
//        
		jsonObject = new JSONObject();
		jsonObject.put("sensor", "gyroscope");
		jsonObject.put("yawAngle", String.valueOf(yaw));
		jsonObject.put("rollAngle", String.valueOf(roll));
		jsonObject.put("pitchAngle", String.valueOf(pitch));
		json = jsonObject.toString();

		coapClient = new CoapClient();
		coapClient.setURI("coap://" + ipAdress + "/gyroscope");
		coapResponse = coapClient.post(json, MediaTypeRegistry.APPLICATION_JSON);
		coapClient.shutdown();
	}

	public static void button() {

		jsonObject = new JSONObject();
		jsonObject.put("sensor", "button");
		jsonObject.put("status", ts.getStatus());
		json = jsonObject.toString();

		coapClient = new CoapClient();
		coapClient.setURI("coap://" + ipAdress + "/button");
		coapResponse = coapClient.post(json, MediaTypeRegistry.APPLICATION_JSON);
		coapClient.shutdown();
	}

	public static void distance(String sensor) throws Exception {

		jsonObject = new JSONObject();
		jsonObject.put("sensor", sensor);

		if (sensor.equals("ultrasonic")) {

			jsonObject.put("distance", String.valueOf(ultrasonic.getDistance()));
			json = jsonObject.toString();

		} else if (sensor.equals("ifraredray")) {

			jsonObject.put("distance", String.valueOf(iRSensor.getValue()));
			json = jsonObject.toString();

		}

		coapClient = new CoapClient();
		coapClient.setURI("coap://" + ipAdress + "/" + sensor);
		coapResponse = coapClient.post(json, MediaTypeRegistry.APPLICATION_JSON);
		coapClient.shutdown();
	}

}
