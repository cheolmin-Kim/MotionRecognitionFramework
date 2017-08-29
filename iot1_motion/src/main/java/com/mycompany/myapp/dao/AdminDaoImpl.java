package com.mycompany.myapp.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mycompany.myapp.dto.Member;

@Component
public class AdminDaoImpl implements AdminDao {
	private static final Logger LOGGER = LoggerFactory.getLogger(AdminDaoImpl.class);

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	@Override
	public List<Member> memberSelectPage(int pageNo, int rowsPerPage) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("endNum", pageNo * rowsPerPage);
		map.put("startNum", (pageNo - 1) * rowsPerPage);
		List<Member> list = sqlSessionTemplate.selectList("member.selectPage", map);
		return list;
	}

	@Override
	public int memberCountAll() {
		int count = sqlSessionTemplate.selectOne("member.countAll");
		return count;
	}

	@Override
	public void memberUpdate(String mid, String mlevel) {
		Map<String, String> map = new HashMap<>();
		map.put("mid", mid);
		map.put("mlevel", mlevel);
		int result = sqlSessionTemplate.update("member.updateGrade", map);

		System.out.println("result: " + result);
		System.out.println("mid: " + mid);
		System.out.println("mlevel: " + mlevel);
	}
}
