package com.mycompany.myapp.service;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.mycompany.myapp.dao.AdminDao;
import com.mycompany.myapp.dto.Member;

@Component
public class AdminServiceImpl implements AdminService {
	private static final Logger LOGGER = LoggerFactory.getLogger(AdminServiceImpl.class);
	@Resource(name = "adminDaoImpl")
	private AdminDao dao;

	@Override
	public List<Member> memberListPage(int pageNo, int rowsPerPage) {
		List<Member> list = dao.memberSelectPage(pageNo, rowsPerPage);
		return list;
	}

	@Override
	public int memberTotalRows() {
		int count = dao.memberCountAll();
		return count;
	}

	@Override
	public void memberGrade(String mid, String mlevel) {
		dao.memberUpdate(mid, mlevel);	
	}	
}
