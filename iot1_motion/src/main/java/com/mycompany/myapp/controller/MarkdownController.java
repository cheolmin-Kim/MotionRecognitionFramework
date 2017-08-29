package com.mycompany.myapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class MarkdownController {
	
	@RequestMapping("/markdown")
	public String markdown(){
		return "markdownSample";
	}

}
