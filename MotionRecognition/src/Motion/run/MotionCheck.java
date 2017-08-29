package Motion.run;

import Motion.Interfaces.ActionInterface;
import Motion.Interfaces.GyroMotionInterface;
import Motion.gyroMotionInterfaceImpl.GyroMotionImpl_PitchRotation;
import Motion.gyroMotionInterfaceImpl.GyroMotionImpl_V;
import Motion.gyroMotionInterfaceImpl.GyroMotionImpl_ZigZag;
import Motion.gyroMotionInterfaceImpl.GyroMotionImpl_Left;
import Motion.gyroMotionInterfaceImpl.GyroMotionImpl_Down;
import Motion.gyroMotionInterfaceImpl.GyroMotionImpl_N;
import Motion.gyroMotionInterfaceImpl.GyroMotionImpl_Right;
import Motion.gyroMotionInterfaceImpl.GyroMotionImpl_Up;
import Motion.Interfaces.TriggerMotionInterface;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author CheolMin Kim
 */
public class MotionCheck {

	/**
	 * Gyroscope Sensor와 관련된 작업을 하는 Thread
	 */
	private static Thread gyroCheckThread;
	/**
	 *@deprecated 
	 */
	private static Thread ultraCheckThread;
	/**
	 *@deprecated 
	 */
	private static Thread irCheckThread;
	/**
	 * @deprecated 
	 */
	private static Thread buttonCheckThread;
	
	/**
	 * Current Status of Button.  
	 * It can be "ready" , "on" , "off".
	 */
	
	public static String buttonStatus = "ready";
	
	/**
	 * Current Distance Value Received from an infrared sensor.
	 */
	public static double irDistance=1000000;
	
	/**
	 * Current Distance Value Received from an Ultrasonic sensor.
	 */
	public static double ultrasonicDistance;
	
	/**
	 * execution status of Motion recognition. 
	 * '0' means ready step of motion recognition , '1' means Collecting step collecting Value of gyroscope Sensor for motion recognition , '2' means Execution step for motion recognition.
	 */
	public static int motionOn = 0;
	
	/**
	 *Name of final Motion recognized from recognizer.
	 */
	public static String finalMotion="";

	/**
	 *List having The maximum and minimum angular ranges of the yaw, roll, and pitch axes you want to obtain for motion recognition 
	 * and the minimum and maximum ranges of the angular difference values to be obtained within the range.
	 */
	public static List yawRollPitchRangeList = new ArrayList<>();
	/**
	 * List containing arrays(difference[]) having angular difference values at  index numbers that are equal to the index number of yawRollPitchRangeList.
	 * difference[0] is Information about the order in which values were sent. 
	 * difference[1] is Information on the yaw axis difference value.
	 * difference[2] is Information on the roll axis difference value.
	 * difference[3] is Information on the pitch axis difference value.
	 */
	public static List<List> differenceResultList = new ArrayList<>();
	
	/**
	 * List with classes that implement gyromotionInterface.
	 */
	public static List<GyroMotionInterface> gyroMotionList = new ArrayList<>();
	
	/**
	 * Map with motion names of String type as Keys , counts of Integer type as Values.
	 */
	public static Map<String, Integer> motionMap = new HashMap<String, Integer>();
	
	/**
	 * List with classes that implement TriggerMotionInterface to turn on motion trigger.
	 * 
	 */
	public static List<TriggerMotionInterface> triggerOnMotionList = new ArrayList<>();
	
	/**
	 * List with classes that implement TriggerMotionInterface to turn off motion trigger.
	 */
	public static List<TriggerMotionInterface> triggerOffMotionList = new ArrayList<>();
	
	/**
	 * the Class provided from Recognizer Class that matches the motion name passed in the form of a Map with the event to be executed.
	 */
	public ActionInterface actionInterfaceImpl;

	//[yaw min(0), yaw max(1), roll min(2) , roll max(3),pitch min(4),pitch max(5),
	// yaw Gap Min(6),yaw Gap Max(7),roll GapMin (8),roll Gap Max (9),pitch Gap Min (10), pitch Gap Max(11)] , 고려하지 않을 경우 max와 min에 각각 0을 넣어줌,
	//해당각의 Gap을 고려하지 않을경우 0값을 넣어줌

	private GyroMotions gyroMotions;

	/**
	 * calls gyroCheckThreadStart() method.
	 * @param action 
	 * the Class provided from Recognizer Class that matches the motion name passed in the form of a Map with the event to be executed.
	 */
	public MotionCheck(ActionInterface action) {
		actionInterfaceImpl=action;

		gyroCheckThreadStart();
		/*
		ultraCheckThreadStart();
		irCheckThreadStart();
		buttonCheckThreadStart();
*/
		gyroMotions= new GyroMotions();
		
		double[] left={180,270,165,195,0,0,4,80,0,0.8,0,0};
		double[] right={90,180,165,195,0,0,4,80,0,0.8,0,0};
		double[] up={140,220,90,270,0,0,0,3,2,80,0,0};
		double[] down={140,220,90,270,0,0,0,3,2,80,0,0};
		double[] zigzag={90,190,150,270,0,0,4,80,0.5,80,0,0};
		double[] pitchRotation={140,220,140,220,80,360,0,3,0,3,3,80};
		double[] v={160,270,90,270,0,0,4,80,2,80,0,0};
		double[] n={90,190,90,190,0,0,4,80,0.8,80,0,0};
		//double[] n;
	//	double[] plusYawMinusRoll={180,360,90,180,0,0,1,80,1,80,0,0};
		
	
		yawRollPitchRangeList.add(left); //0번
		yawRollPitchRangeList.add(right);//1번
		yawRollPitchRangeList.add(up); //2번
		yawRollPitchRangeList.add(down); //3번
		yawRollPitchRangeList.add(zigzag); //4번
		yawRollPitchRangeList.add(pitchRotation); //5번
		yawRollPitchRangeList.add(v); //6번
		yawRollPitchRangeList.add(n); //7번
		
		gyroMotionList.add(new GyroMotionImpl_Up());
		gyroMotionList.add(new GyroMotionImpl_Left());
		gyroMotionList.add(new GyroMotionImpl_Right());
		gyroMotionList.add(new GyroMotionImpl_Down());
		gyroMotionList.add(new GyroMotionImpl_ZigZag());
		//gyroMotionList.add(new GyroMotionImpl_Circle());
		gyroMotionList.add(new GyroMotionImpl_N());
	//	gyroMotionList.add(new GyroMotionImpl_LeftDown());
		gyroMotionList.add(new GyroMotionImpl_PitchRotation());
	//	gyroMotionList.add(new GyroMotionImpl_LeftUp());
		gyroMotionList.add(new GyroMotionImpl_V());
		
		triggerOnMotionList.add(gyroMotions);
		triggerOffMotionList.add(gyroMotions);
	}

	/**
	 * updates button status input from button sensor to the latest .
	 * this data is received from CoAP Server.
	 * @param status 
	 * changed button status provided from CoAP Server
	 */
	public static void buttonAddData(String status) {
		buttonStatus = status;
	}
	
	/**
	 * updates distance value Measured from infraredRay Sensor to the latest.
	 * @param distance 
	 * changed distance value provided from CoAP Server
	 */

	public static void irAddData(double distance) {
		irDistance = distance;
	}
	
	/**
	 * updates distance value Measured from Ultrasonic Sensor to the latest.
	 * @param distance 
	 * changed distance value provided from CoAP Server
	 */

	public static void ultrasonicAddData(double distance) {
		ultrasonicDistance = distance;
	}
	
	/**
	 * Changes the motion recognition step.
	 * @param status 
	 * '0' means ready step of motion recognition , 
	 * '1' means Collecting step collecting Value of gyroscope Sensor for motion recognition , 
	 * '2' means Execution step for motion recognition.
	 */

	public static void MotionRecognitionStatus(int status) {
		motionOn = status;
	}
	
	/**
	 * starts gyroCheckThread.
	 * Here , while loop is executed and The methods for motion recognition using gyroscope Sensor are executed according to the motion recognition step.
	 */

	private void gyroCheckThreadStart() {

		gyroCheckThread = new Thread() {
			@Override
			public void run() {

				while (true) {
					if (motionOn == 0) {
						
						System.out.println("모션준비 1단계"); //나중에 삭제각
						for(TriggerMotionInterface trigger: triggerOnMotionList){
							trigger.triggerMotion(0);
							trigger.triggerButton(0,buttonStatus);
							trigger.triggerIR(0,irDistance);
						}
						
						try {
							Thread.sleep(500);
						} catch (Exception e) {
						}
					}
					
					else if(motionOn == 1){
						finalMotion="";
						try {
							Thread.sleep(500);
						} catch (Exception e) {
						}
					System.out.println("모션을 취하는중 2단계"); //나중에 삭제각
						for(TriggerMotionInterface trigger: triggerOffMotionList){
						//	trigger.triggerMotion(1);
							trigger.triggerButton(1,buttonStatus);
							trigger.triggerIR(1, irDistance);
						}
						
					}else {
						System.out.println("모션분석중........."); //나중에 삭제각
						motionMap.clear();
						
						//범위안의 변화요소 뽑아내는 부분
						if (!yawRollPitchRangeList.isEmpty()) {
							differenceResultList = gyroMotions.Range(yawRollPitchRangeList);

							//해당 모션체크부분
							if (!differenceResultList.isEmpty()) {
								for (GyroMotionInterface motion : gyroMotionList) {
									motion.gyroMotion(differenceResultList,motionMap);
								}
								if(!motionMap.isEmpty()){
									finalMotion=gyroMotions.motionDecision(motionMap);
									actionInterfaceImpl.action(finalMotion);
								}
								
							}
							
							motionOn=0;
						}else{
							motionOn=0;
							System.out.println("Please, Add your Motion in List");
						}
					}
				}

			}
		};
		gyroCheckThread.start();

	}
/*
	private void ultraCheckThreadStart() {
		ultraCheckThread = new Thread() {

		};
		ultraCheckThread.start();
	}

	private void irCheckThreadStart() {
		irCheckThread = new Thread() {

		};
		irCheckThread.start();
	}

	private void buttonCheckThreadStart() {
		buttonCheckThread = new Thread() {

		};
		buttonCheckThread.start();
	}
*/
}
