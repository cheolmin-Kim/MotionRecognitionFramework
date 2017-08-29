package com.mycompany.myapp.service;

import com.mycompany.myapp.dto.Member;

public interface MemberService {
	public Member getMember(String mid);
	public void memberJoin(Member member);
	public void memberDelete(String mid);
}
