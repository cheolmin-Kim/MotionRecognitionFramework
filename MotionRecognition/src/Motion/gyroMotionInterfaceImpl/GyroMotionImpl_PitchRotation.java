

package Motion.gyroMotionInterfaceImpl;

import Motion.Interfaces.GyroMotionInterface;
import java.util.List;
import java.util.Map;
/**
 * 
 * @author CheolMin Kim
 */
public class GyroMotionImpl_PitchRotation implements GyroMotionInterface{

	/**
	 * It is ovverided method that writes an algorithm to recognize the PitchRotation motion using a list containing arrays(difference[]).
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
		int pitchRightRotationCount=0;
		int pitchLeftRotationCount=0;
		List<double[]> factorsInRangeRight = differenceResultList.get(5);
		for (int j = 0; j < factorsInRangeRight.size(); j++) {

			double[] count = factorsInRangeRight.get(j);
			if (count[3] > 0&&count[2]!=0&&count[1]!=0) {
						//System.out.println(count[2]);
						//System.out.println("down");
						pitchLeftRotationCount++;
					} 
				if (count[3] < 0&&count[2]!=0&&count[1]!=0) {
						//System.out.println(count[2]);
						//System.out.println("down");
						pitchRightRotationCount++;
					} 
		}
		motionMap.put("pitchLeftRotation", pitchLeftRotationCount);
		motionMap.put("pitchRightRotation", pitchRightRotationCount);
	}

}
