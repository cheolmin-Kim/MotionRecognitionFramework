package Motion;




/**
 * 
 * @author HwaSung Seo
 */

/**
 *  This motionList is for distance sensors such as Ultrasonic sensor, Infrared rays sensor and so on.
 */
public class DistanceMotionList {
	
	/**
	 *  eventState indicates eventHandle current state.
	 */
	private boolean eventState;
	/**
	 *  holdState indicates holdON current state.
	 */
	private boolean holdState;
	/**
	 *  Constructor
	 */
	public DistanceMotionList(){
		
	}
	/**
	 * In this method, if distance is smaller than fixPoint, eventState will be true.
	 * @param distance distance from the sensor
	 * @param fixPoint a standard for this method
	 */
	public boolean eventHandle(double distance, double fixPoint) {
		
		if(distance<=fixPoint){
			eventState = true;
		}else eventState = false;
		
		return eventState;
	}
	/**
	 * In this method, if there is distance between min and max, holdstate will be ture.
	 * @param distance distance from the sensor
	 * @param min min value for a standard for distance.
	 * @param max max value for a standard for distance.
	 */
	public boolean holdOn(double distance, double min, double max) {
		
		if(distance<min && distance>max){
			holdState = true;
		}else holdState = false;
		
		return holdState;		
	}	
	
	
}
