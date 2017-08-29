package com.mycompany.myapp.dao;

import java.sql.Connection;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mycompany.myapp.dto.Member;

@Component
public class MemberDaoImpl implements MemberDao {
	private static final Logger logger=LoggerFactory.getLogger(MemberDaoImpl.class);
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	Connection conn=null;
	
	@Override
	public Member memberSelectByMid(String mid){

		Member member=sqlSessionTemplate.selectOne("member.selectByMid", mid);

		return member;
	}

	@Override
	public String memberInsert(Member member) {

		sqlSessionTemplate.insert("member.insert",member);
		
		return member.getMid();
	}
	
	@Override
	public void memberDelete(String mid) {
		logger.info("daoimpl"+mid);
		
		sqlSessionTemplate.delete("member.delete",mid);
	}
}
