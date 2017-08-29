package com.raspoid.sensors;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

public class UltrasonicSensor {

    //Field
    private static final int SOUND_VALOCITY = 34000; // (cm/s)
    private static final int NANO_SECOND = 1000000000;

    private GpioPinDigitalOutput trigPin;
    private GpioPinDigitalInput echoPin;

    private int previousDistance;
    private boolean again;

    //Constructor
    public UltrasonicSensor(Pin trigPinNo, Pin echoPinNo) {
        GpioController gpioController = GpioFactory.getInstance();

        trigPin = gpioController.provisionDigitalOutputPin(trigPinNo, PinState.LOW);
        trigPin.setShutdownOptions(true, PinState.LOW);

        echoPin = gpioController.provisionDigitalInputPin(echoPinNo, PinPullResistance.PULL_DOWN);
        echoPin.setShutdownOptions(true, PinState.LOW);
    }

    public int getDistance() {
        //초음파 발신 시간 변수와 수신 시간 변수 선언
        double startTime = 0;
        double endTime = 0;

        //초음파를 10 마이크로초 동안 발생
        trigPin.high();
        try {
            Thread.sleep((long) 0.01);
        } catch (Exception e) {
        }
        trigPin.low();

        //echoPin이 High가 될때까지 기다림
        while (echoPin.isLow()) {

        }
        //발신 시간을 저장
        startTime = System.nanoTime();

        //echoPin이 Low가 될때까지 기다림
        while (echoPin.isHigh()) {

        }
        endTime = System.nanoTime();

        //편도 시간(sec)
        double seconds = (endTime - startTime) / NANO_SECOND / 2;
        //거리(cm)
        int distance = (int) (seconds * SOUND_VALOCITY);

        //100이상 튀는 값이 있을 경우 다시 측정
        if (again == false && Math.abs(previousDistance - distance) > 7) {
            again = true;
            getDistance(); //dummy read
            distance = getDistance();
        } else {
            again = false;
        }

        //초과값 검사(2cm ~ 400cm)
        if (distance < 2) {
            distance = 2;
        } else if (distance > 400) {
            distance = 400;
        }
        previousDistance = distance;
        return distance;
    }

    //Method
    public static void main(String[] args) throws Exception {
        UltrasonicSensor test = new UltrasonicSensor(RaspiPin.GPIO_04, RaspiPin.GPIO_05);
        while (true) {
            int distance = test.getDistance();
            System.out.print("거리(cm): " + distance);
            Thread.sleep(100);
        }
    }
}
