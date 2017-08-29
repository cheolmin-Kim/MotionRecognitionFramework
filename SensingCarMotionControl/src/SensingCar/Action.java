package SensingCar;

import Motion.Interfaces.ActionInterface;

public class Action implements ActionInterface {
	private Buzzer buzzer;
	private boolean buzzerStatus=false;
	private Camera camera;
	public Action() throws Exception{
		 buzzer=new Buzzer();
		 camera=new Camera();
		 
	}

	@Override
	public void action(String finalMotion) {

		switch (finalMotion) {
			case "left":
				System.out.println("----------------------Left 모션 인식");
				camera.turnLeftRight(120);
				break;
			case "right":
				System.out.println("----------------------Right 모션 인식");
				camera.turnLeftRight(60);
				break;
			case "up":
				System.out.println("----------------------Up 모션 인식");
				camera.turnUpDown(80);
				break;
			case "down":
				System.out.println("----------------------Down 모션 인식");
				camera.turnUpDown(20);
				break;
			case "zigzag":
				System.out.println("----------------------ZigZag 모션 인식");
				if(!buzzerStatus){
				buzzer.on();
				buzzerStatus=true;
				}else{
					buzzer.off();
					buzzerStatus=false;
				}
				break;
			default:
				System.out.println("----------------------모션 매칭 실패");
				break;
		}
	}

}
