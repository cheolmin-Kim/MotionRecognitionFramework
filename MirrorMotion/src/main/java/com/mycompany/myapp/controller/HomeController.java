package com.mycompany.myapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import Motion.Recognizer;
import Motion.run.MotionCheck;





/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	public static Motion.Recognizer main;
	
    
	@RequestMapping("/")
	public String home() throws Exception  {
		main= new Recognizer(new Action());
		
		main.start();
//		MotionCheck.triggerOnMotionList.remove(0);
//		//MotionCheck.triggerOnMotionList.remove(1);
//		MotionCheck.triggerOffMotionList.remove(0);
		MotionCheck.triggerOnMotionList.add(new Controllsss());
		MotionCheck.triggerOffMotionList.add(new Controllsss());
	
		return "home";
	}
	
	
	
}
