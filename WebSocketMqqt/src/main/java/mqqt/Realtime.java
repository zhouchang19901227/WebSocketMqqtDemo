package mqqt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import org.fusesource.hawtbuf.Buffer;
import org.fusesource.hawtbuf.UTF8Buffer;
import org.fusesource.mqtt.client.Callback;
import org.fusesource.mqtt.client.CallbackConnection;
import org.fusesource.mqtt.client.Listener;
import org.fusesource.mqtt.client.QoS;
import org.fusesource.mqtt.client.Topic;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

public class Realtime {
	
	private WebSocketSession session;
	private MqttHandler mqtt;
	
	
	public Realtime(WebSocketSession s) {
		this.session = s;
		this.mqtt = new MqttHandler(new Callback<Void>() {
			@Override
			public void onSuccess(Void value) {
			}
			@Override
			public void onFailure(Throwable value) {
			}
		});
		this.mqtt.connect().listener(new Listener() {
			@Override
			public void onPublish(UTF8Buffer topic, Buffer buffer, Runnable run) {
				try {
					session.sendMessage(new TextMessage(buffer.toString()));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			@Override
			public void onFailure(Throwable arg0) {
			}
			@Override
			public void onDisconnected() {
			}
			@Override
			public void onConnected() {
			}
		});
	}
	
	/**
	 * 订阅消息，订阅串用";"号隔开
	 * @param topic
	 */
	public void subscribe(String topicsStr) {
		System.out.println(topicsStr);
		if(topicsStr != null && !topicsStr.isEmpty() && topicsStr.startsWith("OBD/RT")) {
			final ArrayList<Topic> topics = new ArrayList<>();
			for(String str : topicsStr.split(";")) {
				topics.add(new Topic(str, QoS.AT_LEAST_ONCE));
			}
			this.mqtt.connect().subscribe(topics.toArray(new Topic[]{}), null);
		}
	}
	
	/**
	 * 退订消息，订阅串用";"号隔开
	 * @param topicsStr
	 */
	public void unsubscribe(String topicsStr) {
		if(topicsStr != null && !topicsStr.isEmpty() && topicsStr.startsWith("OBD/RT")) {
			final ArrayList<UTF8Buffer> buffers = new ArrayList<>();
			for(String str : topicsStr.split(";")) {
				buffers.add(new UTF8Buffer(str.getBytes()));
			}
			this.mqtt.connect().unsubscribe(buffers.toArray(new UTF8Buffer[]{}), null);
		}
	}
	
	/**
	 * 退订所有消息
	 * @param topicsStr
	 */
	public void unsubscribeAll() {
		this.mqtt.connect().unsubscribe(new UTF8Buffer[]{new UTF8Buffer("OBD/RT/#")}, null);
	}
	
	/**
	 * 关闭mqtt连接
	 */
	public void close() {
		unsubscribeAll();
		this.mqtt.close();
	}

}
