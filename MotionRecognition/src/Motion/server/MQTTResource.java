package Motion.server;

import Motion.mqtt.Distributor;
import static Motion.server.GyroscopeResource.currPitchAngle;
import static Motion.server.GyroscopeResource.currRollAngle;
import static Motion.server.GyroscopeResource.currYawAngle;
import static Motion.server.IRResource.irDistance;
import static Motion.server.UltrasonicResource.ultraDistance;
import java.util.logging.Level;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author Hwasung Seo
 */

public class MQTTResource extends CoapResource {

	private static final Logger logger = LoggerFactory.getLogger(MQTTResource.class);
	
	private static MQTTResource instance;
	/**
	 *  mqtt client from Distributor class
	 */	
	public static Distributor mqtt;
	/**
	 *  topic 
	 */
	private String mqttId;
	
	/**
	 *  Basic constructor, a thread in this class will be started by gerating this class
	 */
	public MQTTResource() throws Exception {
		super("mqtt");
		instance = this;
		
		/**
		*  If you want to check your data in our website, you would change this ID from account.
		*/
		//여기에 아이디를 수정
		mqttId = "1003619136445774";

		
		mqtt  = new Distributor(mqttId);

	}
	
	
	@Override
	public void setObservable(boolean observable) {
		/**
		*  Thread for mqtt publish <br/>
		*  This thread will publish values from the other CoAP resourses to Broker. <br/>
		*  In this case, we used three sensors (gyro, ultrasonic, infrared ray)
		*/
		Thread mqttThread = new Thread() {
			@Override
			public void run() {
				
				while (true) {
					try {
						JSONObject responseJsonObject = new JSONObject();
						responseJsonObject.put("yawAngle", String.valueOf(Math.round(currYawAngle*100)/100.));
						responseJsonObject.put("pitchAngle", String.valueOf(Math.round(currPitchAngle*100)/100.));
						responseJsonObject.put("rollAngle", String.valueOf(Math.round(currRollAngle*100)/100.));

						String responseJson = responseJsonObject.toString();
						try {
							mqtt.publish("gyro", responseJson);
						} catch (MqttException ex) {
							java.util.logging.Logger.getLogger(MQTTResource.class.getName()).log(Level.SEVERE, null, ex);
						}
						
						responseJsonObject = new JSONObject();
						responseJsonObject.put("distance", String.valueOf(ultraDistance) );
						responseJson = responseJsonObject.toString();
						try {
							mqtt.publish("ultrasonic",responseJson);
						} catch (MqttException ex) {
							java.util.logging.Logger.getLogger(GyroscopeResource.class.getName()).log(Level.SEVERE, null, ex);
						}
								
						responseJsonObject = new JSONObject();
						responseJsonObject.put("distance", String.valueOf(irDistance) );
						responseJson = responseJsonObject.toString();
						try {
							mqtt.publish("ifraredray", responseJson);
						} catch (MqttException ex) {
							java.util.logging.Logger.getLogger(GyroscopeResource.class.getName()).log(Level.SEVERE, null, ex);
						}

						Thread.sleep(500);
						
					} catch (Exception e) {
						LOGGER.info(e.toString());
					}
				}
			}

		};
		if(observable) mqttThread.start();
	}
	

	public static MQTTResource getInstance() {

		return instance;
	}
	/**
	*  Dealing with get method from CoAP client (empty)
	*/
	@Override
	public void handleGET(CoapExchange exchange) {

		
	}
	/**
	*  Dealing with post method from CoAP client<br>
	*  You can check values you send by this method <br>
	*  <b>You have to send string json like "{ "sensor" : "status" }"</b>
	*/
	@Override
	public void handlePOST(CoapExchange exchange) {
		

		String requestJson = exchange.getRequestText();
			
		JSONObject requestJsonObject = new JSONObject(requestJson);
		String sensor = requestJsonObject.getString("sensor");
		
		if (sensor.equals("status")) {
			JSONObject responseJsonObject = new JSONObject();
			responseJsonObject.put("result", "success");
			responseJsonObject.put("yawAngle", String.valueOf(currYawAngle));
			responseJsonObject.put("pitchAngle", String.valueOf(currPitchAngle));
			responseJsonObject.put("rollAngle", String.valueOf(currRollAngle));
			responseJsonObject.put("ultrasonic", String.valueOf(ultraDistance));
			responseJsonObject.put("ifraredray", String.valueOf(irDistance));
			String responseJson = responseJsonObject.toString();
			exchange.respond(responseJson);
		}
			
	}
	

}
