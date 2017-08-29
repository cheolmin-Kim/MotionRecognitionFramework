
package Motion.Interfaces;
/**
 * ActionInterface
 * @author CheolMin Kim
 */
public interface ActionInterface {
	/**
	 * It is abstract method that matches the final motion and the event you want to execute
	 * @param finalMotion
	 * The name of the last determined motion. It Indicates the name of the motion with the highest count value
	 */
		public void action(String finalMotion);
}
