package com.mycompany.myapp.controller;

import javax.servlet.http.HttpSession;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mycompany.myapp.websocket.GyroSensor3DHandler;
import com.mycompany.myapp.websocket.GyroSensorHandler;
import com.mycompany.myapp.websocket.IfraredraySensorHandler;
import com.mycompany.myapp.websocket.UltrasonicSensorHandler;
@Controller
public class GyroCotroller {
	private static final Logger logger = LoggerFactory.getLogger(GyroCotroller.class);

	@Autowired
	private ApplicationContext applicationContext;
	
	@RequestMapping("/gyroTest")
	public String gyroTest(HttpSession session) throws MqttException{
		// Mqtt
		String url = "tcp://106.253.56.122:1883";
		String myClientId = MqttClient.generateClientId();
		MqttClient mqttClient = new MqttClient(url, myClientId);
		String mid = (String) session.getAttribute("mid");		
		mqttClient.setCallback(new MqttCallback() {
			@Override
			public void deliveryComplete(IMqttDeliveryToken token) {
				logger.info("");
			}
			@Override
			public void messageArrived(String topic, MqttMessage mm) throws Exception {	
				logger.info("");						
				String mid = (String) session.getAttribute("mid");					
				String json = new String(mm.getPayload());					
				
				GyroSensorHandler gyroSensorHandler = (GyroSensorHandler) applicationContext.getBean("gyroSensorHandler");
				GyroSensor3DHandler gyroSensor3DHandler = (GyroSensor3DHandler) applicationContext.getBean("gyroSensor3DHandler");				
				IfraredraySensorHandler ifraredraySensorHandler = (IfraredraySensorHandler) applicationContext.getBean("ifraredraySensorHandler");
				UltrasonicSensorHandler ultrasonicSensorHandler = (UltrasonicSensorHandler) applicationContext.getBean("ultrasonicSensorHandler");

				if(topic.indexOf("gyro")>0){
					gyroSensorHandler.sendMessage(mid, json);
					gyroSensor3DHandler.sendMessage(mid, json);					
				} else if(topic.indexOf("ifraredray")>0){
					ifraredraySensorHandler.sendMessage(mid, json);
				}else if(topic.indexOf("ultrasonic")>0){
					ultrasonicSensorHandler.sendMessage(mid, json);
				}
			}
			
			@Override
			public void connectionLost(Throwable cause) {
				logger.info("");
				try {
					if(mqttClient != null) {
						mqttClient.disconnect();
						mqttClient.close();						
					}
				} catch (MqttException ex) {
					ex.printStackTrace();
				}
			}
		});
		mqttClient.connect();
		mqttClient.subscribe("/" + mid + "/#");
		session.setAttribute("mqttClient", mqttClient);
		return "gyroTest";
	}

}
