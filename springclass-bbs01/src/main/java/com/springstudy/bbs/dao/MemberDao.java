package com.springstudy.bbs.dao;

import com.springstudy.bbs.domain.Member;

public interface MemberDao {

	public Member getMember(String id);
	
	public void addMember(Member member);
	
	// 회원 정보 수정 시에 기존 비밀번호가 맞는지 체크하는 메서드
	public String memberPassCheck(String id);
	
	// 회원 정보를 회원 테이블에서 수정하는 메서드
	public void updateMember(Member member);
	
}
