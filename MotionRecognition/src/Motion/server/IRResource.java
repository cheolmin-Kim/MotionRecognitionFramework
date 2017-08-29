package Motion.server;

import Motion.run.MotionCheck;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author CheolMin Kim
 * @author Hwasung Seo
 */
public class IRResource extends CoapResource {

	private static final Logger logger = LoggerFactory.getLogger(IRResource.class);
	
	
	private static IRResource instance;
	public static double irDistance;
	
	
	/**
	*  
	*/
	public IRResource() throws Exception {
		super("infraredray");
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
		Thread irObserverThread = new Thread(){
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
		if(observable) irObserverThread.start();
	}

	public static IRResource getInstance() {

		return instance;
	}
	/**
	* Dealing with get method from CoAP client <br>
	* Send values to client which request obsevation from this sever.
	*/
	@Override
	public void handleGET(CoapExchange exchange) {
		
		JSONObject responseJsonObject = new JSONObject();
		responseJsonObject.put("distance", String.valueOf(irDistance) );
		
		String responseJson = responseJsonObject.toString();
		exchange.respond(responseJson);
	}

	/**
	 * Methods to get data by Post method.
	 * This should be a String of Json type with two Keys and Values.
	 * The first key should be "sensor" and the Value should be "infraredray".
	 * The second key should be "distance" and Value should be the value of the distance measured by the InfraredRay Sensor..
	 * @param exchange 
	 */
	@Override
	public void handlePOST(CoapExchange exchange) {
		//{"sensor":"infraredray","distance":"100"} 이런식으로
	//{"sensor":"status"} 이런식으로 요청

		try{
		String requestJson = exchange.getRequestText();
		JSONObject requestJsonObject = new JSONObject(requestJson);
		String sensor = requestJsonObject.getString("sensor");
		if (sensor.equals("infraredray")) {
			double distance= Double.parseDouble(requestJsonObject.getString("distance"));
			irDistance=distance;
			MotionCheck.irAddData(irDistance);
			
		}else if (sensor.equals("status")) {

		}
		JSONObject responseJsonObject = new JSONObject();
		responseJsonObject.put("result", "success");
		responseJsonObject.put("distance", String.valueOf(irDistance));
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
