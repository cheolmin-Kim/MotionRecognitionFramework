package Motion;

import Motion.Interfaces.ActionInterface;
/**
 * 
 * @author CheolMin Kim
 */

public class Action implements ActionInterface {
	
	/**
	 * It is overrided method that matches the final motion and the event you want to execute
	 * @param finalMotion 
	 * The name of the last determined motion. It Indicates the name of the motion with the highest count value
	 */
	@Override
	public void action(String finalMotion) {
		switch (finalMotion) {
			case "left":
				System.out.println("----------------------Left 모션 인식");
				break;
			case "right":
				System.out.println("----------------------Right 모션 인식");
				break;
			case "up":
				System.out.println("----------------------Up 모션 인식");
				break;
			case "down":
				System.out.println("----------------------Down 모션 인식");
				break;
			case "zigzag":
				System.out.println("----------------------ZigZag 모션 인식");
				break;
				case "n":
				System.out.println("----------------------N 모션 인식");
				break;
				case "pitchRightRotation":
				System.out.println("----------------------pitchRightRotation 모션 인식");
				break;
				case "pitchLeftRotation":
				System.out.println("----------------------pitchLeftRotation 모션 인식");
				break;
				case "v":
				System.out.println("----------------------V 모션 인식");
				break;
			default:
				System.out.println("----------------------모션 매칭 실패");
				break;
		}
	}

}
