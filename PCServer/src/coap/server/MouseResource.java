package coap.server;

import com.pi4j.io.gpio.RaspiPin;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Robot;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MouseResource extends CoapResource {

	private static final Logger logger = LoggerFactory.getLogger(MouseResource.class);
	
	private String currstatus;
	private static MouseResource instance;
	private Robot r;
	int currAxisX;
	int currAxisY;
	int pointX;
	int pointY;
	PointerInfo a;
	Point b;
	int preAxisX;
	int preAxisY;

	public MouseResource() throws Exception {
		super("mouse");
		instance = this;
		
		
		
		
			a = MouseInfo.getPointerInfo();
			b = a.getLocation();
		pointX = (int) b.getX();
		pointY = (int) b.getY();
		System.out.print(pointY + "jjjjjjjjj");
		System.out.println(pointX);
		r = new Robot();
		//r.mouseMove(pointX, pointY - 50); //지워야됨
		Thread.sleep(1000);
		

	}

	public static MouseResource getInstance() {

		return instance;
	}

	public void move() {

	}

	@Override
	public void handleGET(CoapExchange exchange) {

	}

	@Override
	public void handlePOST(CoapExchange exchange) {
		//{"command":"change","axisX":"100","axisY":"100","blue":"100"} 이런식으로
	//{"command":"status"} 이런식으로 요청
	a = MouseInfo.getPointerInfo();
	b = a.getLocation();
	pointX = (int) b.getX();
		pointY = (int) b.getY();
	
		try{
		String requestJson = exchange.getRequestText();
		JSONObject requestJsonObject = new JSONObject(requestJson);
		String command = requestJsonObject.getString("command");
		if (command.equals("change")) {
			int axisX= Integer.parseInt(requestJsonObject.getString("axisX"));
			int axisY= Integer.parseInt(requestJsonObject.getString("axisY"));
			
			if(axisX<-30){
				int x=5000/360;
				currAxisX=pointX+x;
			}else if(axisX>30){
				int x1=5000/360*(-1);
				currAxisX=pointX+x1;
			}
			
			if(160<axisY&&axisY<200){
				
			}
			
			if(axisY<160){
				int y=5000/360;
				currAxisY=pointY+y;
			}else if(axisY>200){
				int y1=5000/360*(-1);
				currAxisY=pointY+y1;
			}
			
			r.mouseMove(currAxisX, currAxisY);
			
		}else if (command.equals("status")) {

		}
		JSONObject responseJsonObject = new JSONObject();
		responseJsonObject.put("result", "success");
		responseJsonObject.put("axisX", String.valueOf(currAxisX));
		responseJsonObject.put("axisY", String.valueOf(currAxisY));
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
