
package Motion.Interfaces;

import java.util.List;
import java.util.Map;
/**
 * GyroMotionInterface
 * @author CheolMin Kim
 */
public interface GyroMotionInterface {
	
	/**
	 * abstract method that writes an algorithm to recognize the desired motion using a list containing arrays(difference[]).
	 * In this method, the name of the desired motion is set as Key, and the count is set as Value and put in the parameter map.
	 * If you want to insert a new motion, create a new Class, implement a GyroMotionInterface, override the method and add it to the MotionCheck.gyroMotionList.
	 * @param differenceResultList
	 *  List containing arrays(difference[]) having angular difference values at  index numbers that are equal to the index number of yawRollPitchRangeList.
	 * difference[0] is Information about the order in which values were sent. 
	 * difference[1] is Information on the yaw axis difference value.
	 * difference[2] is Information on the roll axis difference value.
	 * difference[3] is Information on the pitch axis difference value.
	 * @param motionMap
	 * Map with motion names of String type as Keys , counts of Integer type as Values.
	 */
	public void gyroMotion(List<List> differenceResultList,Map<String,Integer> motionMap);
}
