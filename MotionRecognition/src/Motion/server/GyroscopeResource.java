package Motion.server;

import Motion.Action;
import Motion.run.GyroMotions;
import Motion.run.MotionCheck;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * GyroscopeResource
 * @author CheolMin Kim
 * @author Hwasung Seo
 */

public class GyroscopeResource extends CoapResource {

	private static final Logger logger = LoggerFactory.getLogger(GyroscopeResource.class);

	private static GyroscopeResource instance;
	public static double currYawAngle;
	public static double currRollAngle;
	public static double currPitchAngle;
	
	private Thread gyroObserverThread;
	

	public GyroscopeResource() throws Exception {
		super("gyroscope");
		instance = this;		

	}
	
	@Override
	public void setObservable(boolean observable) {
		/**
		*  set observalbe from CoAP client.
		*/
		super.setObservable(observable);
		
		getAttributes().setObservable();
		/**
		*  oberve type is NON message type.
		*/
		setObserveType(CoAP.Type.NON);
		
		/**
		*  This thread send message by hanleGet every 0.5 second.<br>
		*  You can use this thread if you check the values from this sever.
		*/
		Thread gyroObserverThread = new Thread(){
			@Override
			public void run() {
				while(true){
					try{
						changed();
						Thread.sleep(500);
					}catch(Exception e){
						LOGGER.info(e.toString());
					}
				}
			}
			
		};
		if(observable) gyroObserverThread.start();
	}	

	public static GyroscopeResource getInstance() {

		return instance;
	}

	/**
	*  Dealing with get method from CoAP client <br>
	* Send values to client which request obsevation from this sever.
	*/
	@Override
	public void handleGET(CoapExchange exchange) {
		//System.out.println("Get방식");

		JSONObject responseJsonObject = new JSONObject();
		responseJsonObject.put("yawAngle", String.valueOf(Math.round(currYawAngle*100)/100.));
		responseJsonObject.put("pitchAngle", String.valueOf(Math.round(currPitchAngle*100)/100.));
		responseJsonObject.put("rollAngle", String.valueOf(Math.round(currRollAngle*100)/100.));

		String responseJson = responseJsonObject.toString();
		exchange.respond(responseJson);
	}

	/**
	 * Methods to get data by Post method.
	 * This should be a String of Json type with four Keys and Values.
	 * the first key should be "sensor" and the Value should be "gyroscope".
	 * the second key should be "yawAngle" and the Value should be the value of the yaw axis measured by the gyroscope sensor.
	 * the third key is "pitchAngle" and the Value must be the value of the pitch axis measured by the gyroscope sensor.
	 * the fourth key should be "rollAngle" and the Value should be the value of the roll axis measured by the gyroscope sensor.
	 * It can also receive data in the form of a query string.
	 * The first key should be "sensor" and the Value should be "gyroscope".
	 * the second key should be "yawAngle" and the Value should be the value of the yaw axis measured by the gyroscope sensor.
	 * the third key is "pitchAngle" and the Value must be the value of the pitch axis measured by the gyroscope sensor.
	 * the fourth key should be "rollAngle" and the Value should be the value of the roll axis measured by the gyroscope sensor.
	 * @param exchange 
	 */
	@Override
	public void handlePOST(CoapExchange exchange) {
		//System.out.println("post 방식");
		//{"sensor":"gyroscope","yawAngle":"100","pitchAngle":"100","rollAngle":"100"} 이런식으로
		//{"sensor":"status"} 이런식으로 요청
		// coap://192.168.3.133:5683/gyroscope?sensor=gyroscope&yawAngle=yaw&pitchAngle=pitch&rollAngle=roll 이런식으로 요청

		try {
			String requestJson = exchange.getRequestText();
			if (requestJson.equals("")) {
				String sensor1 = exchange.getRequestOptions().getUriQuery().get(0).split("=")[1];
				String yawAngle1="";
				String pitchAngle1="";
				String rollAngle1="";
				//System.out.println("개이득    :"+exchange.getRequestOptions().getURIQueryCount()); //삭제각
				
				if(exchange.getRequestOptions().getURIQueryCount()>1){
					yawAngle1 = exchange.getRequestOptions().getUriQuery().get(1).split("=")[1];
				pitchAngle1 = exchange.getRequestOptions().getUriQuery().get(2).split("=")[1];
				 rollAngle1 = exchange.getRequestOptions().getUriQuery().get(3).split("=")[1];
				}
				
				//System.out.println("key1 :" + key1);
				//System.out.println("key2 :" + key2);

				if (sensor1.equals("gyroscope")) {
					currYawAngle = Double.parseDouble(yawAngle1);
					currPitchAngle = Double.parseDouble(pitchAngle1);
					currRollAngle = Double.parseDouble(rollAngle1);
					GyroMotions.gyroAddData(currYawAngle, currPitchAngle, currRollAngle);
					
					exchange.respond(String.valueOf(MotionCheck.finalMotion));
				} else if (sensor1.equals("status")) {
					
					//exchange.respond(String.valueOf(Action.motionResult));

				} else {
					exchange.respond("fail");
				}
			} else {
				JSONObject requestJsonObject = new JSONObject(requestJson);
				String sensor = requestJsonObject.getString("sensor");
				if (sensor.equals("gyroscope")) {
					double yawAngle = Double.parseDouble(requestJsonObject.getString("yawAngle"));
					double pitchAngle = Double.parseDouble(requestJsonObject.getString("pitchAngle"));
					double rollAngle = Double.parseDouble(requestJsonObject.getString("rollAngle"));
					currYawAngle = yawAngle;
					currPitchAngle = pitchAngle;
					currRollAngle = rollAngle;
					GyroMotions.gyroAddData(currYawAngle, currPitchAngle, currRollAngle);
				} else if (sensor.equals("status")) {

				}
				JSONObject responseJsonObject = new JSONObject();
				responseJsonObject.put("result", "success");
				responseJsonObject.put("yawAngle", String.valueOf(currYawAngle));
				responseJsonObject.put("pitchAngle", String.valueOf(currPitchAngle));
				responseJsonObject.put("rollAngle", String.valueOf(currRollAngle));
				String responseJson = responseJsonObject.toString();
				exchange.respond(responseJson);
			}

		} catch (Exception e) {
			JSONObject responseJsonObject = new JSONObject();
			responseJsonObject.put("result", "fail");
			String responseJson = responseJsonObject.toString();
			exchange.respond(responseJson);
		}

	}

}
