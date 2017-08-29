package com.mycompany.myapp.service;

import java.util.List;

import com.mycompany.myapp.dto.Member;

public interface AdminService {
	public List<Member> memberListPage(int pageNo, int rowsPerPage);
	public int memberTotalRows();
	
	public void memberGrade(String mid, String mlevel);
}
