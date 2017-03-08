package mqqt;

import java.net.URISyntaxException;

import org.apache.log4j.Logger;
import org.fusesource.hawtbuf.Buffer;
import org.fusesource.hawtbuf.UTF8Buffer;
import org.fusesource.mqtt.client.Callback;
import org.fusesource.mqtt.client.CallbackConnection;
import org.fusesource.mqtt.client.Listener;
import org.fusesource.mqtt.client.MQTT;
import org.fusesource.mqtt.client.QoS;


public class MqttHandler {
	
	private static final String IP = "127.0.0.1";
	private static final int PORT =1883;
	private static final int KEEP_ALIVE = 1;
	private MQTT mqtt;
	private Callback<Void> callback;
	private CallbackConnection connection;
	
	//private Logger log = Logger.getLogger(MqttHandler.class);
	
	/**
	 * 创建mqtt连接
	 * @return
	 */
	public MqttHandler(Callback<Void> callback) {
		if(mqtt == null) {
			mqtt = new MQTT();
			try {
				mqtt.setHost(IP, PORT);
				mqtt.setKeepAlive((short)KEEP_ALIVE);
				mqtt.setCleanSession(true);
				this.callback = callback;
				//log.info("mqtt服务器连接初始化...");
			} catch (Exception e) {
				close();
			}
		}
	}
	
	/**
	 * 回调
	 */
	public CallbackConnection connect() {
		try {
			if(this.connection == null) { 
				this.connection = mqtt.callbackConnection();
				this.connection.connect(this.callback);
				//log.info("mqtt服务器连接开�?...");
			}
		} catch (Exception e) {
			close();
		}

		return this.connection;
	}
	
	/**
	 * 关闭连接
	 */
	public void close() {
		if(this.connection != null) {
			this.connection.disconnect(null);
			//log.info("mqtt服务器连接关�?...");
			System.out.println("mqtt连接关闭");
		}
	}
}
