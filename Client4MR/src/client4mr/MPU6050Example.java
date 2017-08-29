package client4mr;

import com.raspoid.sensors.TouchSwitch;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.i2c.I2CFactory;
import com.raspoid.sensors.IRSensor;
import com.raspoid.sensors.UltrasonicSensor;
import converter.PCF8591;
import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.json.JSONObject;
public class MPU6050Example {

	public static String ipAdress = "192.168.3.113";
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

	
	private MPU6050Example() {
		coapClient = new CoapClient();

	}

	
	public static void main(String[] args) throws I2CFactory.UnsupportedBusNumberException {
		MPU6050 mpu6050 = new MPU6050();

		ts = new TouchSwitch(RaspiPin.GPIO_06);

		ultrasonic = new UltrasonicSensor(RaspiPin.GPIO_04, RaspiPin.GPIO_05);
		pcf8591 = new PCF8591(0x48, PCF8591.AIN0);
		iRSensor = new IRSensor(pcf8591);

		mpu6050.startUpdatingThread();
		time = System.currentTimeMillis();

//		ts.setGpioPinListenerDigital(new GpioPinListenerDigital() {
//			@Override
//			public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
//				if(event.getState() == PinState.HIGH) {
//					System.out.println("off");
//				} else {
//					System.out.println("on");
//				}
//
//				jsonObject = new JSONObject();
//				jsonObject.put("sensor", "button");
//				jsonObject.put("status", ts.getStatus());
//				json = jsonObject.toString();
//
//				coapClient = new CoapClient();
//				coapClient.setURI("coap://" + ipAdress + "/button");
//				coapResponse = coapClient.post(json, MediaTypeRegistry.APPLICATION_JSON);
//				coapClient.shutdown();
//			}
//		});

		while (true) {

			Tools.log("-----------------------------------------------------");

			Tools.log("Filtered angles:");
			double[] filteredAngles = mpu6050.getFilteredAngles();
			

			Tools.log("\t" + MPU6050.xyzValuesToString(MPU6050.angleToString(filteredAngles[0]),
							MPU6050.angleToString(filteredAngles[1]), MPU6050.angleToString(filteredAngles[2])));
			
			axisTrasmission(filteredAngles[0], filteredAngles[1], filteredAngles[2]);
			

			button();
			
			try {
				distance("ultrasonic");
				distance("infraredray");
			} catch (Exception ex) {	ex.printStackTrace();}
			
			Tools.sleepMilliseconds(100);
			
		}

	}

	public static void axisTrasmission(double x, double y, double z) {
		double roll = x;
		double pitch = y;
		double yaw = z;

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

		} else if (sensor.equals("infraredray")) {

			jsonObject.put("distance", String.valueOf(iRSensor.getValue()));
			json = jsonObject.toString();

		}

		coapClient = new CoapClient();
		coapClient.setURI("coap://" + ipAdress + "/" + sensor);
		coapResponse = coapClient.post(json, MediaTypeRegistry.APPLICATION_JSON);
		coapClient.shutdown();
	}

}
