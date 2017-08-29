package Motion.server;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Hashtable;
import java.util.Map;
import java.util.logging.Level;
import org.eclipse.californium.core.CaliforniumLogger;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.CoapServer;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.network.CoapEndpoint;
import org.eclipse.californium.core.network.EndpointManager;
import org.eclipse.californium.scandium.ScandiumLogger;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author CheolMin Kim
 */
public class CoapResourceServer {

	private static final Logger logger = LoggerFactory.getLogger(CoapResourceServer.class);
	/**
	 * CoapServer
	 */
	public static CoapServer coapServer;
	
	public Map<String, CoapResource> resources = new Hashtable<>();

	//static block(californium의 자체 로그 출력 금지
	static {
		CaliforniumLogger.initialize();
		CaliforniumLogger.setLevel(Level.OFF);
		ScandiumLogger.initialize();
		ScandiumLogger.setLevel(Level.OFF);
	}
/**
 * registers the sensor resources with CoapServer.
 * @throws Exception 
 */
	public CoapResourceServer() throws Exception {
		coapServer = new CoapServer();
		for (InetAddress addr : EndpointManager.getEndpointManager().getNetworkInterfaces()) {
			if (!addr.isLinkLocalAddress()) {
				coapServer.addEndpoint(new CoapEndpoint(new InetSocketAddress(addr, CoAP.DEFAULT_COAP_PORT)));
			}
		}
		
		resources.put("gyro", new GyroscopeResource());
		resources.put("ultrasonic", new UltrasonicResource());
		resources.put("ir", new IRResource());
		resources.put("button", new ButtonResource());
		resources.put("mqtt", new MQTTResource());
		

		coapServer.add(resources.get("gyro"));
		coapServer.add(resources.get("ultrasonic"));
		coapServer.add(resources.get("ir"));
		coapServer.add(resources.get("button"));
		coapServer.add(resources.get("mqtt"));
		
		
		
		/**
		 * 
		 */
		resources.get("gyro").setObservable(false);
		resources.get("ultrasonic").setObservable(false);
		resources.get("ir").setObservable(false);
		resources.get("mqtt").setObservable(true);
	}

	/**
	 * starts CoAP Server.
	 */
	public static void start() {
		coapServer.start();
	}

	/**
	 * stops CoAP Server.
	 * 
	 */
	public static void stop() throws MqttException {
		coapServer.stop();
		coapServer.destroy();
		MQTTResource.mqtt.close();
	}

}
