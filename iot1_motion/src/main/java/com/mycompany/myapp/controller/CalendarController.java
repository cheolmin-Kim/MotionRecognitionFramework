package com.mycompany.myapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller

public class CalendarController {
	@RequestMapping("/calendar")
	public String Calendar(){
		return "calendar";
	}
}
