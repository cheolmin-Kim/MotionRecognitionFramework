package com.mycompany.myapp.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.facebook.api.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.mycompany.myapp.dto.Member;
import com.mycompany.myapp.service.MemberService;

@Controller
@SessionAttributes({ "member", "log" })
public class JoinController {
	private static final Logger logger = LoggerFactory.getLogger(JoinController.class);

	@Resource(name = "memberServiceImpl")
	private MemberService service;

	@RequestMapping("/join")
	public String join() {
		return "join";
	}

	@RequestMapping("join/insert")

	public String insert(Member member, User profile, String name, String email, String mid) {
		member.setMname(name);
		member.setMemail(email);
		member.setMlevel("1");
		member.setMid(mid);

		service.memberJoin(member);

		return "redirect:/login";
	}

	@RequestMapping("leave")
	public String leave(String mid, SessionStatus sessionStatus, Model model) {
		logger.info("컨트롤러에서 leave ");
		sessionStatus.setComplete();
		String log = "logout";
		model.addAttribute("log", log);
		service.memberDelete(mid);
		return "redirect:/";
	}
}
