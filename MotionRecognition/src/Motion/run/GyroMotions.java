package Motion.run;

import static Motion.run.MotionCheck.buttonAddData;
import Motion.Interfaces.TriggerMotionInterface;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
/**
 * 
 * @author CheolMin Kim
 */
public class GyroMotions implements TriggerMotionInterface {

	/**
	 * Status that determines whether to collect sensor values for motion recognition.
	 */
	public static boolean MotionListCollecting = false;
	/**
	 * The state of whether the offset value was measured.
	 */
	public static boolean adjustingYawAxis = false;
	/**
	 * Offset Value.
	 * This is used to calculate the yaw axis to maintain 180 degrees. 
	 * This calculation process prevents inaccurate motion recognition problems caused by drift phenomenon of yaw axis.
	 */
	public static double YawAxisValueForAdjusting = 0.0;

	/**
	 * List that stores gyro sensor values of yaw axis's angle transmitted from client
	 */
	public static List<Double> listYawAngles = new Vector<>();
	
	/**
	 * List that stores gyro sensor values of roll axis's angle transmitted from client
	 */
	public static List<Double> listRollAngles = new Vector<>();
	/**
	 * List that stores gyro sensor values of pitch axis's angle transmitted from client
	 */
	public static List<Double> listPitchAngles = new Vector<>();
	
	/**
	 * List that stores the amount of change in the yaw axis's angle
	 */
	public static List<Double> listYawDifferences = new Vector<>();
	/**
	 * List that stores the amount of change in the roll axis's angle
	 */
	public static List<Double> listRollDifferences = new Vector<>();
	/**
	 * List that stores the amount of change in the pitch axis's angle
	 */
	public static List<Double> listPitchDifferences = new Vector<>();
	
	/**
	 * List that stores gyro sensor values of yaw axis's angle to be used for motion recognition
	 */
	public static List<Double> listYawAngle = new Vector<>();
	
	/**
	 * List that stores gyro sensor values of roll axis's angle to be used for motion recognition
	 */
	public static List<Double> listRollAngle = new Vector<>();
	/**
	 * List that stores gyro sensor values of pitch axis's angle to be used for motion recognition
	 */
	public static List<Double> listPitchAngle = new Vector<>();
	/**
	 * List that stores the amount of change in the yaw axis's angle to be used for motion recognition
	 */
	public static List<Double> listYawDifference = new Vector<>();
	/**
	 * List that stores the amount of change in the roll axis's angle to be used for motion recognition
	 */
	public static List<Double> listRollDifference = new Vector<>();
	/**
	 * List that stores the amount of change in the pitch axis's angle to be used for motion recognition
	 */
	public static List<Double> listPitchDifference = new Vector<>();
	/**
	 * Length of list that stores gyro sensor values of angles transmitted from client
	 */
	public static int listLength = 10;
	/**
	 * Initial value
	 */
	public static double initialValue = 0.0;

	

	public static List<Double> getListYawAngles() {
		return listYawAngles;
	}

	public static List<Double> getListRollAngles() {
		return listRollAngles;
	}

	public static List<Double> getListPitchAngles() {
		return listPitchAngles;
	}

	public static List<Double> getListYawDifferences() {
		return listYawDifferences;
	}

	public static List<Double> getListRollDifferences() {
		return listRollDifferences;
	}

	public static List<Double> getListPitchDifferences() {
		return listPitchDifferences;
	}

	public static List<Double> getListYawAngle() {
		return listYawAngle;
	}

	public static List<Double> getListRollAngle() {
		return listRollAngle;
	}

	public static List<Double> getListPitchAngle() {
		return listPitchAngle;
	}

	public static List<Double> getListYawDifference() {
		return listYawDifference;
	}

	public static List<Double> getListRollDifference() {
		return listRollDifference;
	}

	public static List<Double> getListPitchDifference() {
		return listPitchDifference;
	}

	public static int getListLength() {
		return listLength;
	}

	public static double getInitialValue() {
		return initialValue;
	}

	public GyroMotions() {

		listYawDifferences.add(initialValue);
		listRollDifferences.add(initialValue);
		listPitchDifferences.add(initialValue);
	}

	//Gyro3축 값을 받음 , listLength 만큼의 길이의 리스트에 값을 넣는다.
	
	
	/**
	 * updates Values of the three axes Measured from Gyroscope Sensor to the latest.
	 * In this method, It receives the gyro sensor values from the CoAP Server and puts them into the lists 
	 * that are listYawAngles, listRollAngles, listPitchAngles, listYawDifferences, listRollDifferences and listPitchDifferences.
	 * If the motion trigger to collect the gyro sensor values for motion recognition is turned on, It puts gyrosensor values into the lists 
	 * that are listYawAngle, listRollAngle, listPitchAngle and  the amount of changes in the three axes's angle into  listYawDifference, listRollDifference and listPitchDifference.
	 * These lists are unlimited in length. The on and off points of the motion trigger affect the list length.
	 * Also, the offset value is used to calculate the value of the yaw axis at 180 degrees for motion recognition even if a drift phenomenon occurs.
	 * @param yaw
	 * changed yaw axis's value provided from CoAP Server
	 * @param pitch
	 * changed pitch axis's value provided from CoAP Server
	 * @param roll 
	 * changed roll axis's value provided from CoAP Server
	 */
	public static void gyroAddData(double yaw, double pitch, double roll) {
		if (listYawAngles.size() < listLength) {
			listYawAngles.add(yaw);
			listRollAngles.add(roll);
			listPitchAngles.add(pitch);
		} else if (listYawAngles.size() >= listLength) {
			listYawAngles.remove(0);
			listPitchAngles.remove(0);
			listRollAngles.remove(0);
			listYawAngles.add(yaw);
			listRollAngles.add(roll);
			listPitchAngles.add(pitch);
		}
		if (listYawAngles.size() >= 2) {
			double nextValue = listYawAngles.get(listYawAngles.size() - 1);
			double preValue = listYawAngles.get(listYawAngles.size() - 2);
			listYawDifferences.add(nextValue - preValue);
			nextValue = listRollAngles.get(listRollAngles.size() - 1);
			preValue = listRollAngles.get(listRollAngles.size() - 2);
			listRollDifferences.add(nextValue - preValue);
			nextValue = listPitchAngles.get(listPitchAngles.size() - 1);
			preValue = listPitchAngles.get(listPitchAngles.size() - 2);
			listPitchDifferences.add(nextValue - preValue);
			if (listYawDifferences.size() >= listLength + 1) {
				listYawDifferences.remove(0);
				listPitchDifferences.remove(0);
				listRollDifferences.remove(0);
			}
		}

		if (MotionListCollecting == true) {

			if (adjustingYawAxis == false) {
				YawAxisValueForAdjusting = 180 - yaw;
				adjustingYawAxis = true;
			} else {

				listRollAngle.add(roll);
				listPitchAngle.add(pitch);

				if (YawAxisValueForAdjusting < 0) {
				//	System.out.println("1번 1번 1번");  ///////////////////////삭제각
					if (yaw <= Math.abs(YawAxisValueForAdjusting)) {
						listYawAngle.add(360 + YawAxisValueForAdjusting + yaw);

					} else {
						listYawAngle.add(yaw + YawAxisValueForAdjusting);
					}

				} else if (YawAxisValueForAdjusting > 0) {
				//	System.out.println("2번 2번 2번");  ///////////////////////삭제각
					if (yaw> 360-YawAxisValueForAdjusting) {
						listYawAngle.add(YawAxisValueForAdjusting + yaw-360);

					} else {
						listYawAngle.add(yaw + YawAxisValueForAdjusting);
					}

				}
			//	System.out.println(listYawAngle.get(listYawAngle.size()-1));  ///////////////////////삭제각

				if (listYawAngle.size() >= 2) {
					double nextValue = listYawAngle.get(listYawAngle.size() - 1);
					double preValue = listYawAngle.get(listYawAngle.size() - 2);
					listYawDifference.add(nextValue - preValue);
					nextValue = listRollAngle.get(listRollAngle.size() - 1);
					preValue = listRollAngle.get(listRollAngle.size() - 2);
					listRollDifference.add(nextValue - preValue);
					nextValue = listPitchAngle.get(listPitchAngle.size() - 1);
					preValue = listPitchAngle.get(listPitchAngle.size() - 2);
					listPitchDifference.add(nextValue - preValue);

				} else {
					listYawDifference.add(initialValue);
					listRollDifference.add(initialValue);
					listPitchDifference.add(initialValue);
				}
			}
		} else {
			adjustingYawAxis = false;
		}
	}
	
	/**
	 * Empty lists where gyro values are gathered even when the motion trigger is not turned on.
	 */

	private void emptingContinuedList() {
		if (!listYawAngles.isEmpty()) {
			listYawAngles.clear();
		}
		if (!listRollAngles.isEmpty()) {
			listRollAngles.clear();
		}
		if (!listPitchAngles.isEmpty()) {
			listPitchAngles.clear();
		}
		if (!listYawDifferences.isEmpty()) {
			listYawDifferences.clear();
		}
		if (!listRollDifferences.isEmpty()) {
			listRollDifferences.clear();
		}
		if (!listPitchDifferences.isEmpty()) {
			listPitchDifferences.clear();
		}
		listYawDifferences.add(initialValue);
		listRollDifferences.add(initialValue);
		listPitchDifferences.add(initialValue);
	}
	/**
	 * Empties the lists where gyro values are collected when the motion trigger is turned on.
	 */
	private void emptingCollectedList() {
		if (!listYawAngle.isEmpty()) {
			listYawAngle.clear();
		}
		if (!listRollAngle.isEmpty()) {
			listRollAngle.clear();
		}
		if (!listPitchAngle.isEmpty()) {
			listPitchAngle.clear();
		}
		if (!listYawDifference.isEmpty()) {
			listYawDifference.clear();
		}
		if (!listRollDifference.isEmpty()) {
			listRollDifference.clear();
		}
		if (!listPitchDifference.isEmpty()) {
			listPitchDifference.clear();
		}
	}
	
	/**
	 * This method examines the elements added to the yawRollPitchRangeList and stores the values in the array corresponding to 
	 * the conditions indicated by the element into the differenceInRangeResultList at the same index as the element.
	 * @param yawRollPitchRangeList
	 * List having The maximum and minimum angular ranges of the yaw, roll, and pitch axes you want to obtain for motion recognition 
	 * and the minimum and maximum ranges of the angular difference values to be obtained within the range.
	 * @return 
	 * differenceInRangeResultList -
	 * List containing arrays(difference[]) having angular difference values at  index numbers that are equal to the index number of yawRollPitchRangeList.
	 * difference[0] is Information about the order in which values were sent. 
	 * difference[1] is Information on the yaw axis difference value.
	 * difference[2] is Information on the roll axis difference value.
	 * difference[3] is Information on the pitch axis difference value.
	 */

	public static List Range(List<double[]> yawRollPitchRangeList) {


		//메소드에서  리턴해줄  리스트 생성
		List<List> differenceInRangeResultList = new ArrayList<>();

		//구간을 설정해놓은 갯수만큼 리스트 생성해놓기
		for (int k = 0; k < yawRollPitchRangeList.size(); k++) {
			List<double[]> differenceInRangeList = new ArrayList<>();
			differenceInRangeResultList.add(differenceInRangeList);
		}
		if (listYawAngle.size() >= listLength) {
			for (int i = 0; i < listYawAngle.size(); i++) {
				double yawAngle = listYawAngle.get(i);
				double rollAngle = listRollAngle.get(i);
				double pitchAngle = listPitchAngle.get(i);
				double yawDifference = listYawDifference.get(i);
				double rollDifference = listRollDifference.get(i);
				double pitchDifference = listPitchDifference.get(i);
				for (int j = 0; j < yawRollPitchRangeList.size(); j++) {
					double[] difference = {0, 0, 0, 0}; //해당 범위지정번호: 인덱스값 ,{값이 들어온 순서,yaw,roll,pitch}
					boolean yawEnable = true;
					boolean rollEnable = true;
					boolean pitchEnable = true;
					boolean yawSatisfaction = false;
					boolean rollSatisfaction = false;
					boolean pitchSatisfaction = false;
					double[] range = yawRollPitchRangeList.get(j);
					double yawMinRange = range[0];
					double yawMaxRange = range[1];
					double rollMinRange = range[2];
					double rollMaxRange = range[3];
					double pitchMinRange = range[4];
					double pitchMaxRange = range[5];
					double yawGapMin = range[6];
					double yawGapMax = range[7];
					double rollGapMin = range[8];
					double rollGapMax = range[9];
					double pitchGapMin = range[10];
					double pitchGapMax = range[11];
					if (yawMinRange == yawMaxRange) {
						yawEnable = false;
					}
					if (rollMinRange == rollMaxRange) {
						rollEnable = false;
					}
					if (pitchMinRange == pitchMaxRange) {
						pitchEnable = false;
					}

					if (yawEnable == true) {
						if (yawMinRange < yawAngle && yawAngle < yawMaxRange) {
							yawSatisfaction = true;
						}
					} else {
						yawSatisfaction = true;
					}

					if (rollEnable == true) {
						if (rollMinRange < rollAngle && rollAngle < rollMaxRange) {
							rollSatisfaction = true;
						}
					} else {
						rollSatisfaction = true;
					}

					if (pitchEnable == true) {
						if (pitchMinRange < pitchAngle && pitchAngle < pitchMaxRange) {
							pitchSatisfaction = true;
						}
					} else {
						pitchSatisfaction = true;
					}

					if (yawSatisfaction && rollSatisfaction && pitchSatisfaction) {
						difference[0] = i; //추후 step에 사용, 해당 값의 순서
						if (yawGapMin <= Math.abs(yawDifference) && Math.abs(yawDifference) <= yawGapMax) {
							difference[1] = yawDifference;
						}
						if (rollGapMin <= Math.abs(rollDifference) && Math.abs(rollDifference) <= rollGapMax) {
							difference[2] = rollDifference;
						}
						if (pitchGapMin <= Math.abs(pitchDifference) && Math.abs(pitchDifference) <= pitchGapMax) {
							difference[3] = pitchDifference;
						}
						List<double[]> temp = differenceInRangeResultList.get(j);
						temp.add(difference);
					}
				}
			}
		}
		return differenceInRangeResultList;
	}

	/**
	 * It extracts the key and value from the parameter motionMap, compares the count, finds the motion with the highest count, and returns.
	 * @param motionMap
	 * Map with motion names of String type as Keys , counts of Integer type as Values.
	 * @return
	 * finalMotion - The name of the last determined motion. It Indicates the name of the motion with the highest count value.
	 */
	
	public static String motionDecision(Map<String, Integer> motionMap) {

		Set<String> keySet = motionMap.keySet();

		int prevalue = 0;
		boolean sameCount = false;
		String finalMotion = "";
		Iterator<String> keys = keySet.iterator();

		while (keys.hasNext()) {
			String key = keys.next(); // Set의 key 값을 하나씩 key에 대입
			int count = motionMap.get(key); // 해당 key에 해당하는 value 대입 / 오토 언박싱
			System.out.println(key + " : " + count);

			if (prevalue < count) {
				prevalue = count;
				finalMotion = key;
				sameCount = false;
			} else if (prevalue == count) {
				sameCount = true;
			}
			//motionMap.remove(key);
		}

		if (sameCount) {
			return "두개이상의 모션이 인식됩니다.( 인식 실패 )";
		} else {
			return finalMotion;
		}
	}

	/**
	 * This method is used when you want to use a motion trigger using a gyro sensor.
	 * This method is a redefined method because the GyroMotions class implements TriggerMotionInterface.
	 * When the yaw axis becomes 130 degrees or less and then returns to 170 degrees, the trigger is turned on or off according to the motion motion recognition step.
	 * If the motion recognition step is 0, it is turned on. If it is 1, it is turned off.
	 * If you do not want to use gyro sensor values with motion triggers, inherit the GyroMotions class and redefine those methods and leave them empty.
	 * If you want to create a motion trigger with different conditions using the gyro sensor value by modifying the contents I wrote, inherit the GyroMotions class, redefine the method and write the code that you want.
	 * 
	 * @param status 
	 * It indicates the current motion recognition step.
	 */
	@Override
	public void triggerMotion(int status) {
		boolean step1 = false;
		boolean step2 = false;
		int count = 0;
		if (listPitchAngles.size() >= 5) {
			for (int i = 0; i < listPitchAngles.size(); i++) {
				double prevalue = listPitchAngles.get(i);
				if (status == 0) {

					if (prevalue < 130 && step1 == false) {
						step1 = true;
						System.out.println("Step1 On");
					}
					if (step1 == true) {
						if (prevalue > 170) {
							step2 = true;
							System.out.println("Step2 On");
						}
					}

					if (step1 == true && step2 == true) {
						emptingContinuedList();
						System.out.println("Motion On");
						MotionCheck.MotionRecognitionStatus(1);
						emptingCollectedList();
						MotionListCollecting = true;
						i = listPitchAngles.size();

					}

				} else if (status == 1) {
					if (prevalue < 130 && step1 == false) {
						step1 = true;
						System.out.println("Step1 On");
					}
					if (step1 == true) {
						if (prevalue > 170) {
							step2 = true;
							System.out.println("Step2 On");
						}
					}

					if (step1 == true && step2 == true) {
						emptingContinuedList();
						System.out.println("Motion Recognition");
						MotionCheck.MotionRecognitionStatus(2);
						MotionListCollecting = false;
						i = listPitchAngles.size();
						buttonAddData("ready");  //다른 인터페이스도 이건꼭 해줘야함
					}
				}

			}
		}
	}

	/**
	 * This method is used when you want to use a motion trigger using a button sensor.
	 * This method is a redefined method because the GyroMotions class implements TriggerMotionInterface.
	 * If motion recognition step is 0, motion trigger is turned on if buttonStatus is off, motion trigger is off when buttonStatus is on when motion recognition step is 1.
	 * If you do not want to use button sensor values with motion triggers, inherit the GyroMotions class and redefine those methods and leave them empty.
	 * If you want to create a motion trigger with different conditions using the button sensor by modifying the contents I wrote, inherit the GyroMotions class, redefine the method and write the code that you want.
	 * 
	 * @param step
	 * It indicates the current motion recognition step.
	 * @param buttonStatus
	 * Current button's Status
	 */
	@Override
	public void triggerButton(int step, String buttonStatus) {
		/*
if(!listYawAngles.isEmpty()){
			System.out.println("YawAngle:  "+listYawAngles.get(listYawAngles.size()-1));
	
			System.out.println("PitchAngle:  "+listPitchAngles.get(listPitchAngles.size()-1));
		
			System.out.println("RollAngle:  "+listRollAngles.get(listRollAngles.size()-1));
}
		*/		
				
		//모션이 취해지지 않았을경우
		if (step == 0) {
			if (buttonStatus.equals("on")) {
				emptingContinuedList();
				System.out.println("Motion On");
				MotionCheck.MotionRecognitionStatus(1);
				emptingCollectedList();
				MotionListCollecting = true;
			} 
		} else if (step == 1) {
			if(!listYawDifference.isEmpty()){
			System.out.println("YawAngle:  "+listYawDifference.get(listYawDifference.size()-1));
	
			System.out.println("PitchAngle:  "+listPitchDifference.get(listPitchDifference.size()-1));
		
			System.out.println("RollAngle:  "+listRollDifference.get(listRollDifference.size()-1));
}
			
			if (buttonStatus.equals("off")) {
				emptingContinuedList();
				System.out.println("Motion Recognition");
				MotionCheck.MotionRecognitionStatus(2);
				MotionListCollecting = false;
				buttonAddData("ready");  // 버튼 상태는 ready , on , off 세가지

			}
		}

	}
	/**
	* This method is used when you want to use a motion trigger using a InfraredRay sensor.
	 * This method is a redefined method because the GyroMotions class implements TriggerMotionInterface.
	 * If the motion recognition step is 0, the motion trigger is turned on when the distance value is less than 10. If the distance value is less than 10 when the motion recognition step is 1, the motion trigger is turned off.
	 * If you do not want to use values measured from an infrared sensor with motion triggers, inherit the GyroMotions class and redefine those methods and leave them empty.
	 * If you want to create a motion trigger with different conditions using the InfraredRay sensor by modifying the contents I wrote, inherit the GyroMotions class, redefine the method and write the code that you want.
	 * 
	 * @param step
	 *  It indicates the current motion recognition step.
	 * @param distance 
	 * Current distance value measured from an infrared sensor
	 */

	@Override
	public void triggerIR(int step, double distance) {
		if (step == 0) {
			if (distance < 10) {
				emptingCollectedList();
				emptingContinuedList();
				System.out.println("Motion On");
				MotionCheck.MotionRecognitionStatus(1);
				MotionListCollecting = true;

			}
		} else if (step == 1) {
			if (distance < 10) {
				emptingContinuedList();
				System.out.println("Motion Recognition");
				MotionCheck.MotionRecognitionStatus(2);
				MotionListCollecting = false;
			}
		}

	}

}
