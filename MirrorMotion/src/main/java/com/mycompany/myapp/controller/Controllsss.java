package com.mycompany.myapp.controller;

import org.springframework.stereotype.Component;

import Motion.run.GyroMotions;

@Component
public class Controllsss extends GyroMotions {
	@Override
	public void triggerMotion(int arg0) {
		
	}
	
	@Override
	public void triggerButton(int step, String buttonStatus) {

	}
	
	@Override
	public void triggerIR(int step, double distance) {
		
	}
}
