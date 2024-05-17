package com.springstudy.bbs.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.springstudy.bbs.domain.Member;

@Repository
public class MemberDaoImpl implements MemberDao {

	@Autowired
	private SqlSessionTemplate sqlSession;
	
	private final String NAME_SPACE = "com.springstudy.bbs.mapper.MemberMapper";
	
	@Override
	public Member getMember(String id) {
		
		return sqlSession.selectOne(NAME_SPACE + ".getMember", id);
	}

}
