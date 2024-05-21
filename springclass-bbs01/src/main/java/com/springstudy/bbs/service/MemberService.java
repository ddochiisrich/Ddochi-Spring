package com.springstudy.bbs.service;

import com.springstudy.bbs.domain.Member;

public interface MemberService {

	public int login(String id, String pass);
	
	public Member getMember(String id);
	
	public boolean overlapIdCheck(String id);
	
	public void addMember(Member member);
	
	public boolean memberPassCheck(String id, String pass);
	
	public void updateMember(Member member);
	
}
