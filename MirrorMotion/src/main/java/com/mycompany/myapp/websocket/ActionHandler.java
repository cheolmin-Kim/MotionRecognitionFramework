package com.mycompany.myapp.websocket;

import java.util.List;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class ActionHandler extends TextWebSocketHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(ActionHandler.class);
	public static List<WebSocketSession> list = new Vector<>();
	
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		LOGGER.info("");
		list.add(session);
	}
	
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		/*LOGGER.info("");
		String strMessage = message.getPayload();
		TextMessage textMessage = new TextMessage(strMessage);
		session.sendMessage(textMessage);*/
	}
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		LOGGER.info("");
		list.remove(session);		
	}
	
	
}