
package SensingCar;

import Motion.run.GyroMotions;
import java.util.List;

public class Controller extends GyroMotions{
	private double rollAnglesPreValue=0.0;
	private double yawDifferencesPreValue=0.0;
	private BackTire backTire;
	private int speed;
	private int frontTireAngle;
	FrontTire frontTire;
	public Controller() throws Exception{
	
		backTire=new BackTire();
		backTire.forward();
		frontTire=new FrontTire();
		speed=500;
		frontTireAngle=90;
}

	@Override
	public void triggerMotion(int i) {
	    System.out.println(frontTireAngle);
		List<Double> listRollAngles=GyroMotions.getListRollAngles();
		List<Double> listYawDifferences=GyroMotions.getListYawDifferences();
		double currRollAnglesValue;
		double currYawDifferencesValue;
		if(!listRollAngles.isEmpty()){
			currRollAnglesValue=listRollAngles.get(listRollAngles.size()-1);
			if(rollAnglesPreValue!=currRollAnglesValue){
				rollAnglesPreValue=currRollAnglesValue;
				if(currRollAnglesValue<150){
					speed+=200;
					System.out.println("speed++++");
					
				}else if(currRollAnglesValue>210){
					speed-=200;
					System.out.println("speed---");
				}
				backTire.setSpeed(speed);
			}else{
				
				
			}
			
		}
		
		if(!listYawDifferences.isEmpty()){
			currYawDifferencesValue=listYawDifferences.get(listYawDifferences.size()-1);
			if(yawDifferencesPreValue!=currYawDifferencesValue){
				yawDifferencesPreValue=currYawDifferencesValue;
				if(Math.abs(currYawDifferencesValue)>8){
					if(currYawDifferencesValue<0){
						//frontTireAngle+=30;
						System.out.println("FrontAngle++++");
					}else{
						//frontTireAngle-=30;
						System.out.println("FrontAngle----");
					}
					frontTire.setAngle(frontTireAngle);
				}
			}
		}
	}

	@Override
	public void triggerButton(int i, String string) {
		
	}

}
