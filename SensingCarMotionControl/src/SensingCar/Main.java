package SensingCar;

import Motion.run.MotionCheck;
import static Motion.run.MotionCheck.yawRollPitchRangeList;


public class Main {

	public static void main(String[] args) throws Exception {
		
		Motion.Recognizer main=new Motion.Recognizer(new Action());
		main.start();
		
		
		MotionCheck.triggerOnMotionList.add(new Controller());
		
		MotionCheck.triggerOnMotionList.remove(0);
		
		MotionCheck.triggerOffMotionList.remove(0);
		MotionCheck.triggerOffMotionList.add(new Controller());
		
		
		double[] left={180,270,165,195,0,0,4,80,0,0.8,0,0};
		yawRollPitchRangeList.add(left);
		
		System.out.print("시작스");
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
