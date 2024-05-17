package com.springstudy.bbs.dao;

import com.springstudy.bbs.domain.Member;

public interface MemberDao {
	// id에 해당하는 회원 정보를 DB테이블에서 읽어와 반환하는 메서드
	public abstract Member getMember(String id);
	
	// 회원가입
	public abstract void addMember(Member member);
}
