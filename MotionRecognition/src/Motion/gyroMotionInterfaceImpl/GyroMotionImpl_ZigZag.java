package Motion.gyroMotionInterfaceImpl;

import Motion.Interfaces.GyroMotionInterface;
import java.util.List;
import java.util.Map;
/**
 * 
 * @author CheolMin Kim
 */
public class GyroMotionImpl_ZigZag implements GyroMotionInterface {

	/**
	 * It is ovverided method that writes an algorithm to recognize the Zigzag motion using a list containing arrays(difference[]).
	 * @param differenceResultList
	 *  List containing arrays(difference[]) having angular difference values at  index numbers that are equal to the index number of yawRollPitchRangeList.
	 * difference[0] is Information about the order in which values were sent. 
	 * difference[1] is Information on the yaw axis difference value.
	 * difference[2] is Information on the roll axis difference value.
	 * difference[3] is Information on the pitch axis difference value.
	 * @param motionMap
	 * Map with motion names of String type as Keys , counts of Integer type as Values.
	 */
	@Override
	public void gyroMotion(List<List> differenceResultList, Map<String, Integer> motionMap) {
		int zigzagCount = 0;
		boolean step1 = true;
		boolean step2 = false;
		boolean step3 = false;

		boolean rightCheck = false;
		List<double[]> factorsInRange = differenceResultList.get(4);
		for (int j = 0; j < factorsInRange.size(); j++) {

			double[] count = factorsInRange.get(j);
			if (step1 == true) {
				//오른쪽직선일때
				if (count[1] < 0 && count[2] == 0) {
					
					rightCheck = false;

					zigzagCount++;
				} else if (count[1] > 0 && count[2] > 0) {
					if (rightCheck) {
						step1 = false;
						step2 = true;
						System.out.println("zigzag - Step1");
						zigzagCount++;
						zigzagCount++;
						
					}else{
					rightCheck = true;
					}
				} else {
					rightCheck = false;
				}
			}else if (step2 == true) {
				if (count[1] > 0 && count[2] > 0) {
					step2 = false;
					step3 = true;
					System.out.println("zigzag - Step2");
					zigzagCount++;
				}

			} else if (step3 == true) {
				if (count[1] < 0 && count[2] == 0) {
					zigzagCount++;
					System.out.println("zigzag - Step3");
				}
			}

		}
		if(step3==true){
		motionMap.put("zigzag", zigzagCount);
		}else{
			motionMap.put("zigzag", 0);
		}
	}

}
