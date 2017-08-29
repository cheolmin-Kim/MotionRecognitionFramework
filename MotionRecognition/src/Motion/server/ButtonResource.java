package Motion.server;

import Motion.run.MotionCheck;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author CheolMin Kim
 */
public class ButtonResource extends CoapResource {

	private static final Logger logger = LoggerFactory.getLogger(ButtonResource.class);
	
	
	private static ButtonResource instance;
	public static String currStatus;
	/**
	 * registers the resource name
	 * @throws Exception 
	 */
	public ButtonResource() throws Exception {
		super("button");
		instance = this;
		
	}

	public static ButtonResource getInstance() {

		return instance;
	}

	/**
	 * Methods to get data by Get method.
	 * It should have a query string type with two parameters for passing values in the get method.
	 * The value of the first parameter is "button" and the value of the second parameter is "on" or "off".
	 * @param exchange 
	 */
	
	
	@Override
	public void handleGET(CoapExchange exchange) {

		System.out.println("버튼 클릭");
		String sensor = exchange.getRequestOptions().getUriQuery().get(0).split("=")[1];
		String status = exchange.getRequestOptions().getUriQuery().get(1).split("=")[1];
		System.out.println("버튼 상태   "+status);
		
		if (sensor.equals("button")) {
			currStatus=status;
			MotionCheck.buttonAddData(currStatus);
		} else if  (sensor.equals("status")){
			
		}else{
			exchange.respond("fail");
		}
	}
	
	/**
	 * Methods to get data by Post method.
	 * This should be a String of Json type with two Keys and Values.
	 * The first key should be "sensor" and the Value should be "button".
	 * The second key should be "status" and the Value should be "on" or "off".
	 * @param exchange 
	 */

	@Override
	public void handlePOST(CoapExchange exchange) {
		//{"sensor":"button","status":"on"} 이런식으로
		//{"sensor":"status"} 이런식으로 요청

		try{
		String requestJson = exchange.getRequestText();
		JSONObject requestJsonObject = new JSONObject(requestJson);
		String sensor = requestJsonObject.getString("sensor");
		if (sensor.equals("button")) {
			String status= requestJsonObject.getString("status");
			currStatus=status;
			MotionCheck.buttonAddData(currStatus);
			
		}else if (sensor.equals("status")) {

		}
		JSONObject responseJsonObject = new JSONObject();
		responseJsonObject.put("result", "success");
		responseJsonObject.put("status", String.valueOf(currStatus));
		String responseJson = responseJsonObject.toString();
		exchange.respond(responseJson);
		}catch(Exception e){
		JSONObject responseJsonObject = new JSONObject();
		responseJsonObject.put("result", "fail");
		String responseJson = responseJsonObject.toString();
		exchange.respond(responseJson);
		}

	}

}
