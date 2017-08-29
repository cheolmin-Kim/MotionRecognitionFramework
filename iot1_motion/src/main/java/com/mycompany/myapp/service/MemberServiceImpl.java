package com.mycompany.myapp.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.mycompany.myapp.dao.MemberDao;
import com.mycompany.myapp.dto.Member;

@Component
public class MemberServiceImpl implements MemberService {
	@Resource(name = "memberDaoImpl")
	private MemberDao dao;

	@Override
	public Member getMember(String mid) {
		Member member = dao.memberSelectByMid(mid);
		return member;
	}

	@Override
	public void memberJoin(Member member) {
		dao.memberInsert(member);
	}

	@Override
	public void memberDelete(String mid) {
		dao.memberDelete(mid);
	}

}
