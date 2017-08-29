package com.mycompany.myapp.websocket;

import java.util.Date;
import java.util.Hashtable;
import java.util.Map;
import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class GyroSensor3D2Handler extends TextWebSocketHandler implements ApplicationListener {

	private static final Logger logger = LoggerFactory.getLogger(GyroSensor3D2Handler.class);

	private Map<String, WebSocketSession> map = new Hashtable<>();

	@PostConstruct
	public void init() {
		logger.info("");
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		logger.info("");
		String mid = (String) session.getAttributes().get("mid");
		map.put(mid, session);
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		logger.info("");
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		logger.info("");
		String mid = (String) session.getAttributes().get("mid");
		map.remove(mid);
	}

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
	}

	public void sendMessage(String mid, String json) {
		logger.info("");
		try {
			WebSocketSession session = map.get(mid);
			JSONObject jsonObject = new JSONObject(json);

			double yawAngle = Double.parseDouble(jsonObject.getString("yawAngle"));
			double pitchAngle = Double.parseDouble(jsonObject.getString("pitchAngle"));
			double rollAngle = Double.parseDouble(jsonObject.getString("rollAngle"));
			
			jsonObject.put("time", getUTCTime(new Date().getTime()));
			jsonObject.put("yawAngle", yawAngle);
			jsonObject.put("pitchAngle", pitchAngle);
			jsonObject.put("rollAngle", rollAngle);
			
			json = jsonObject.toString();
			System.out.println("session:::"+session);
			System.out.println("map:::"+map);
			
			session.sendMessage(new TextMessage(json));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public long getUTCTime(long localTime) {
		long utcTime = 0;
		TimeZone tz = TimeZone.getDefault();
		try {
			int offset = tz.getOffset(localTime);
			utcTime = localTime + offset;
		} catch (Exception e) {
		}
		return utcTime;
	}
}
