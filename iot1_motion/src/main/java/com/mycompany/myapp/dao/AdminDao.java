package com.mycompany.myapp.dao;

import java.util.List;

import com.mycompany.myapp.dto.Member;

public interface AdminDao {	
	public List<Member> memberSelectPage(int pageNo, int rowsPerPage);
	public int memberCountAll();
	
	public void memberUpdate(String mid, String mlevel);
}
