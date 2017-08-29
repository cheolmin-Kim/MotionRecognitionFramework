
package Motion;

import Motion.run.MotionCheck;
import Motion.Interfaces.ActionInterface;
import Motion.server.CoapResourceServer;
import java.util.Scanner;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 
 * @author CheolMin Kim
 */
public class Recognizer {
private static final Logger logger=LoggerFactory.getLogger(Recognizer.class);
	public static CoapResourceServer coapResourceServer;
	public MotionCheck motionCheck;
	
	/**
	 * creates coapserver and MotionCheck Class
	 * @param i
	 * provides the Class that matches the motion name passed in the form of a Map with the event to be executed.
	 * @throws Exception 
	 */
	public Recognizer(ActionInterface i) throws Exception {
		coapResourceServer=new CoapResourceServer();
		motionCheck=new MotionCheck(i);
	}
					
	/**
	 * starts a CoAPServer
	 */
	public static void start(){
		logger.info("실행");
		coapResourceServer.start();
		System.out.println("start.....");
	}
	
	/**
	 * stops a CoApServer
	 */
	public static void stop() throws MqttException{
		logger.info("실행");
		coapResourceServer.stop();
		System.out.println("stop");
	}
	
	public static void main(String[] args) throws Exception {
		
		Recognizer main=new Recognizer(new Action()); // 사용자가 action 인터페이스를 구현해서 넣어줌
		main.start();
		
		/*
		System.out.println("input command(Press q to quit)");
		Scanner scanner=new Scanner(System.in);
		String command=scanner.nextLine();
		if(command.equals("q")){
			main.stop();
		}
*/

	}
	
}
