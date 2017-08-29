package com.mycompany.myapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class VideoCotroller {
	@RequestMapping("/video")
	public String video() {
		return "video";
	}
}
