package Motion.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * 
 * @author HwaSung Seo
 */

/**
 *  This class is for mqtt publisher.
 * It will be publish each value from sensor you connect this sever.
*/
public class Distributor {
	/**
	 *  url, this broker is made by mosquitto
	 */
	private String url = "tcp://106.253.56.122:1883";
	/**
	 *  mqttId, it will be used when mqtt client is generated.
	 */
	private String clientId;
	/**
	 *  subscribe topic string, it will be used when mqtt client is generated.
	 */
	private String request;
	/**
	 *  publish topic string, it will be used when mqtt client is generated.
	 */
	private String response;
	
	/**
	 *  mqttId, it will be used when mqtt client is generated.
	 */
	private String sensor;
	
	private String mqttId;	
	private int qos = 1;
	
	/**
	 *  MQTT client for publishing and subscribing 
	 */
	private MqttClient mqttClient;
	
	/**
	 *  MqttCallback is for handling after connection. <br>
	 *  
	 */
	private MqttCallback callback = new MqttCallback(){
		/**
		*  this method will be loaded when delivery completion
		*/
		@Override
		public void deliveryComplete(IMqttDeliveryToken imdt) {
			
		}
		/**
		*   this method will be loaded when mqtt connetion lost
		*/
		@Override
		public void messageArrived(String string, MqttMessage mm) throws Exception {
			publish(sensor, mm.toString());			
		}
		/**
		*  this method will be loaded when mqtt connetion lost
		*/
		@Override
		public void connectionLost(Throwable thrwbl) {
			try {
				close();
			} catch (MqttException ex) {
				ex.printStackTrace();
			}
		}
		
	};
	/**
	 * Constructor by parameter. It will be used for client for MQTT.
	 * @param clientId It is for mqtt id and topic
	 * @throws MqttException
	 */
	public Distributor(String clientId) throws MqttException {
		
		this.clientId =  MqttClient.generateClientId();
		
		this.mqttId = clientId;
		
		mqttClient = new MqttClient(url, this.clientId);
		
		mqttClient.setCallback(callback);
		
		mqttClient.connect();
	}
	
	/**
	 * Close mqtt client
	 * @throws MqttException
	 */
	public void close() throws MqttException{
		if(mqttClient !=null){
			mqttClient.disconnect();
			mqttClient.close();
			mqttClient = null;
		}
	}
	/**
	 *  This method subscribe from broker by topic name
	 * @param sensor sensor name for topic to subscribe
	 * @throws MqttException
	 */
	public void subscribe(String sensor) throws MqttException{
		this.sensor = sensor;
		this.request = "/"+mqttId+"/"+sensor+"/request";
		mqttClient.subscribe(request);
	}
	/**
	 * This method publish json to our broker as mqtt client.
	 * @param sensor sensor name for topic to publish
	 * @param json content for publish to broker
	 * @throws MqttException
	 */
	public void publish(String sensor, String json) throws MqttException{
		this.sensor = sensor;
		this.response ="/"+mqttId+"/"+ sensor+"/response";
		MqttMessage mqttMessage = new MqttMessage(json.getBytes());
		mqttMessage.setQos(qos);
		mqttClient.publish(response, mqttMessage);
	}
}
