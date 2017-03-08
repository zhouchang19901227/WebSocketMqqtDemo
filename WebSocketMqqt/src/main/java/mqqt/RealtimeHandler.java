package mqqt;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Service("realtimeHandler")
public class RealtimeHandler extends TextWebSocketHandler  {
	
	private Realtime realtime;
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session)
			throws Exception {
		this.realtime = new Realtime(session);
		System.out.println("websocket开启");
	}

	@Override
	protected void handleTextMessage(WebSocketSession session,
			TextMessage message) throws Exception {
		String topics = message.getPayload();
		session.sendMessage(new TextMessage(topics+"14443"));
		if(topics != null && !topics.isEmpty()) {
			if(topics.startsWith("Subscribe:")) {
				this.realtime.subscribe(topics.replaceFirst("Subscribe:", ""));
			}
			else if(topics.startsWith("Unsubscribe:")) {
				this.realtime.unsubscribe(topics.replaceFirst("Unsubscribe:", ""));
			}
		}
		
	}
	
	@Override
	public void afterConnectionClosed(WebSocketSession session,
			CloseStatus status) throws Exception {
		this.realtime.close();
		System.out.println("websocket关闭");
	}


	
	
}
