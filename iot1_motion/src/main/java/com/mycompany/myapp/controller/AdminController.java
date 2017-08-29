package com.mycompany.myapp.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mycompany.myapp.dto.Member;
import com.mycompany.myapp.service.AdminService;
import com.mycompany.myapp.service.MemberService;

@Controller
public class AdminController {

	@Resource(name = "adminServiceImpl")
	private AdminService service;
	@Resource(name = "memberServiceImpl")
	private MemberService mservice;

	@RequestMapping("/admin/memberList")
	public String memberList(@RequestParam(defaultValue = "1") int pageNo, Model model) {
		// 한 페이지를 구성하는 행 수
		int rowsPerPage = 10;
		// 한 그룹을 구성하는 페이지 수
		int pagesPerGroup = 5;
		// 총 행 수
		int totalRows = service.memberTotalRows(); // DB 쿼리해야함
		// 총 페이지 수
		int totalPageNo = (totalRows / rowsPerPage) + ((totalRows % rowsPerPage != 0) ? 1 : 0);
		// 총 그룹 수
		int totalGroupNo = (totalPageNo / pagesPerGroup) + ((totalPageNo % pagesPerGroup != 0) ? 1 : 0);
		// 현재 그룹 번호
		int groupNo = (pageNo - 1) / pagesPerGroup + 1;
		// 현재 그룹의 시작 페이지 번호
		int startPageNo = (groupNo - 1) * pagesPerGroup + 1;
		// 현재 그룹의 마지막 페이지 번호
		int endPageNo = startPageNo + pagesPerGroup - 1;
		if (groupNo == totalGroupNo) {
			endPageNo = totalPageNo;
		}
		// 현재 페이지의 데이터 가져오기
		List<Member> list = service.memberListPage(pageNo, rowsPerPage);
		// view 로 넘겨줄 데이터
		model.addAttribute("list", list);
		model.addAttribute("pagesPerGroup", pagesPerGroup);
		model.addAttribute("totalPageNo", totalPageNo);
		model.addAttribute("totalGroupNo", totalGroupNo);
		model.addAttribute("groupNo", groupNo);
		model.addAttribute("startPageNo", startPageNo);
		model.addAttribute("endPageNo", endPageNo);
		model.addAttribute("pageNo", pageNo);

		// view 이름 리턴
		return "admin/memberList";
	}

	@RequestMapping("/admin/memberGradeUpdate")
	public String memberGradeUpdate(String mid, String mlevel) {
		service.memberGrade(mid, mlevel);
		System.out.println("mlevel: " + mlevel);
		return "redirect:/admin/memberList";
	}

	@RequestMapping("/admin/memberDelete")
	public String member(String mid) {
		mservice.memberDelete(mid);
		return "redirect:/admin/memberList";
	}
}
