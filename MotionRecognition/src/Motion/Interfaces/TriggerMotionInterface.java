
package Motion.Interfaces;
/**
 * TriggerMotionInterface
 * @author CheolMin Kim
 */
public interface TriggerMotionInterface {
	
	/**
	 * This abstract method can be overriden when you want to use a motion trigger using a gyro sensor.
	 * If you do not want to use gyro sensor values with motion triggers, inherit the GyroMotions class and redefine those methods and leave them empty. 
	 * If you want to create a motion trigger with different conditions using the gyro sensor value by modifying the contents I wrote, inherit the GyroMotions class, redefine the method and write the code that you want 
	 * or Empty MotionCheck.triggerOnMotionList, Create a new Class, implement the TriggerMotionInterface, redefine the method and add it to the MotionCheck.triggerOnMotionList.
	 * 
	 * @param status
	 * It indicates the current motion recognition step.
	 */
	public void triggerMotion(int status);
	
	/**
	 *This abstract method can be overriden when you want to use a motion trigger using a button sensor.
	 * If you do not want to use button sensor values with motion triggers, inherit the GyroMotions class and redefine those methods and leave them empty.
	 * If you want to create a motion trigger with different conditions using the button sensor by modifying the contents I wrote, inherit the GyroMotions class, redefine the method and write the code that you want
	 * or Empty MotionCheck.triggerOnMotionList, Create a new Class, implement the TriggerMotionInterface, redefine the method and add it to the MotionCheck.triggerOnMotionList.
	 * @param value
	 * It indicates the current motion recognition step.
	 * @param status
	 * Current button's Status
	 */
	public void triggerButton(int value,String status);
	
	
	/**
	 * This method can be overriden when you want to use a motion trigger using a InfraredRay sensor.
	 * If you do not want to use values measured from an infrared sensor with motion triggers, inherit the GyroMotions class and redefine those methods and leave them empty.
	 * If you want to create a motion trigger with different conditions using the InfraredRay sensor by modifying the contents I wrote, inherit the GyroMotions class, redefine the method and write the code that you want
	 * or Empty MotionCheck.triggerOnMotionList, Create a new Class, implement the TriggerMotionInterface, redefine the method and add it to the MotionCheck.triggerOnMotionList.
	 * @param value
	 * It indicates the current motion recognition step.
	 * @param distance
	 * Current distance value measured from an infrared sensor
	 */
	public void triggerIR(int value,double distance);
}
